
import Data.{Album, Artist, Song}
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.{FXCollections, MapChangeListener, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, ListView, Slider, ToggleButton}
import javafx.scene.layout.{AnchorPane, FlowPane, GridPane}
import javafx.scene.media.{Media, MediaPlayer}
import javafx.stage.{FileChooser, Stage}
import javafx.util.Duration

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.util.Success



class Controller {
  /*
    var selectedFile: File = _
    var pick: Media = _
    var player: MediaPlayer = _
  */

  @FXML private var centerGrid: GridPane = _
  @FXML private var bottomGrid: GridPane = _
  @FXML private var togglePlayPause: ToggleButton = _
  @FXML private var chooseFileButton: Button = _
  @FXML private var musicNameLabel: Label = _
  @FXML private var seekButton: Button = _
  @FXML private var volumeSlider: Slider = _
  @FXML private var durationSlider: Slider = _
  @FXML private var minDurationLabel: Label = _
  @FXML private var maxDurationLabel: Label = _
  @FXML private var volumeLabel: Label = _
  @FXML private var leftPane: AnchorPane = _
  @FXML private var songList: ListView[Song] = new ListView[Song]()

  var mediaControl: MediaController = MediaController()

  def initialize(): Unit ={
    DatabaseFunc.loadfiles()
    Song.loaded.map(songList.getItems.add)

  }

  def chooseFile(): Unit = {
    val stage: Stage = (chooseFileButton.getScene.getWindow).asInstanceOf[Stage]
    val fileChooser = new FileChooser
    val selectedFile = fileChooser.showOpenDialog(stage)
    //val pick = new Media(selectedFile.toURI.toString)
    //val mc=MediaController(selectedFile.getAbsolutePath)
    mediaControl = MediaController(selectedFile.getAbsolutePath)
    val info:ListBuffer[(String,AnyRef)]=ListBuffer[(String,AnyRef)]()

    musicNameLabel.setText(selectedFile.getName)

    mediaControl.mediaPlayer.get.currentTimeProperty().addListener(new ChangeListener[Duration] {
      override def changed(observable: ObservableValue[_ <: Duration], oldValue: Duration, newValue: Duration): Unit = {
        val time:(Int,Int,Int)= msToMinSec(newValue)
        currentTimeLabelSet(time._2+":"+time._3)
        setDurationSlider((newValue.toSeconds * 100) / mediaControl.getMaxDuration().toSeconds)
      }
    })

    mediaControl.media.get.getMetadata().addListener(new MapChangeListener[String , AnyRef]{
      override def onChanged(change: MapChangeListener.Change[_ <: String, _ <: AnyRef]): Unit ={
        if(change.wasAdded()){
          info.addOne((change.getKey() , change.getValueAdded))
        }
       }
    })

    mediaControl.mediaPlayer.get.setOnReady(new Runnable {
      override def run(): Unit = {
        setSeekSlider()
        println(info)

        val album:String = info.filter( x=>x._1.equals("album") ) (0)._2.toString
        val artist:String = info.filter( x=>x._1.equals("album artist") )(0)._2.toString
        val songid = DatabaseFunc.getlastid(Song.loaded.toList)+1
//artist exists? if not creating it
        val artistcheck:ListBuffer[Artist]=Artist.loaded.filter(x=>x.name.equals(artist))

        val artistid:Int={
          if (artistcheck.isEmpty) {
            val newid:Int=DatabaseFunc.getlastid(Artist.loaded.toList)+1
            Artist.loaded+=Artist( List(newid.toString,artist,"","")  )
            newid
          } else {
            artistcheck(0).id
          }
        }

//album exists? if not creating it
        val albumcheck:ListBuffer[Album]=Album.loaded.filter(x=>x.name.equals(album))

        val albumid:Int = {
          if (albumcheck.isEmpty) {
            val newid:Int=DatabaseFunc.getlastid(Album.loaded.toList)+1
            Album.loaded+=Album( List(newid.toString,album,songid.toString,artistid.toString) )
            newid
          } else {
            albumcheck(0).id
          }
        }
        val nomeFeats = info.filter( x=>x._1.equals("artist") ).map(_._2).remove(0).toString.split(", ").tail.toList
        nomeFeats.map(println)
        val idFeats=nomeFeats.map(x=>DatabaseFunc.GetIDArtistOrCreate(x))

        val song:Song = Song(List[String](
          songid.toString,//0
          info.filter( x=>x._1.equals("title") )(0)._2.toString.trim,//1
          selectedFile.getAbsolutePath,//2 filepath
          artistid.toString,//4 artist
          info.filter( x=>x._1.equals("genre") ) (0)._2.toString,//5 genre
          albumid.toString,//6 album
          idFeats.mkString(" "),//7 feats
          0.toString,//8
          trackN.toString,//9 TrackNumber resolver
          )
        )
        println(song)
        Song.loaded+=song
        //songList.getItems.add(song)
      }
    })

  }

  def setSeekSlider(): Unit = {
    //minDurationLabel.setText(math.round(seektime).toString)
    val time:(Int,Int,Int)= msToMinSec(mediaControl.getMaxDuration())
    maxDurationLabel.setText(time._2+":"+time._3)
    minDurationLabel.setText("0:0")
  }

  def currentTimeLabelSet(time: String): Unit = {
    minDurationLabel.setText(time)
  }

  def setDurationSlider(value: Double): Unit = {
    durationSlider.setValue(value)
  }

  def playpause(): Unit = {
    mediaControl.playpause()
    if (mediaControl.mediaPlayer.get.getStatus.equals(MediaPlayer.Status.PLAYING)) {
      togglePlayPause.setText("Play")
    } else {
      togglePlayPause.setText("Pause")
    }

  }

  def dragDuration(): Unit = {
    this.synchronized{
      val seektime: Double = ((durationSlider.getValue * mediaControl.getMaxDuration().toMillis) / 100)
      mediaControl.seek(new Duration(seektime))
    }
  }

  def setVolume(): Unit = {
    val volume: Double = volumeSlider.getValue
    mediaControl.setVolume(volume / 100)
    volumeLabel.setText(volume.toInt.toString + "%")
  }

  def selectFromList(): Unit ={
    val song:Song = songList.getSelectionModel.getSelectedItems.get(0)
    if(mediaControl.path == ""){
    mediaControl.mediaPlayer.get.dispose()
    }

    mediaControl = MediaController(song.filepath)
    musicNameLabel.setText(song.name)
    val info:ListBuffer[(String,AnyRef)]=ListBuffer[(String,AnyRef)]()
    mediaControl.mediaPlayer.get.currentTimeProperty().addListener(new ChangeListener[Duration] {
      override def changed(observable: ObservableValue[_ <: Duration], oldValue: Duration, newValue: Duration): Unit = {
        val time:(Int,Int,Int)= msToMinSec(newValue)
        currentTimeLabelSet(time._2+":"+time._3)
        setDurationSlider((newValue.toSeconds * 100) / mediaControl.getMaxDuration().toSeconds)
      }
    })

    mediaControl.media.get.getMetadata().addListener(new MapChangeListener[String , AnyRef]{
      override def onChanged(change: MapChangeListener.Change[_ <: String, _ <: AnyRef]): Unit ={
        if(change.wasAdded()){
          info.addOne((change.getKey() , change.getValueAdded))
        }
      }
    })
  }

  private def msToMinSec(duration: Duration):(Int,Int,Int)={
    val hours:Int   = math.floor(duration.toHours).toInt
    val minutes:Int = math.floor(duration.toMinutes).toInt-(hours*60)
    val sec:Int     = math.floor(duration.toSeconds).toInt-(hours*60*60)-(minutes*60)

    (hours,minutes,sec)
  }


}
