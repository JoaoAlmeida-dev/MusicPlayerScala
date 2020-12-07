
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
import scala.util.{Failure, Success, Try}



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

  var mediaPlayer: MediaPlayer = _

  def initialize(): Unit ={
    DatabaseFunc.loadfiles()
    Song.loaded.map(songList.getItems.add)
    val firstSongPath:String=Song.loaded(0).filepath
    mediaPlayer = new MediaPlayer(new Media(new File(firstSongPath).toURI.toString))
  }

  def importMusic(): Unit = {
    val stage: Stage = (chooseFileButton.getScene.getWindow).asInstanceOf[Stage]
    val fileChooser = new FileChooser
    val selectedFile:File = fileChooser.showOpenDialog(stage)
    uploadSong(selectedFile)


  }

  def setSeekSlider(): Unit = {
    //minDurationLabel.setText(math.round(seektime).toString)
    val time:(Int,Int,Int)= msToMinSec(mediaPlayer.getTotalDuration())
    maxDurationLabel.setText(time._2+":"+time._3)
    minDurationLabel.setText("0:0")
  }

  def currentTimeLabelSet(time: String): Unit = {
    minDurationLabel.setText(time)
  }

  def setDurationSlider(value: Double): Unit = {
    durationSlider.setValue(value)
  }



  def dragDuration(): Unit = {
    this.synchronized{
      val seektime: Double = ((durationSlider.getValue * mediaPlayer.getTotalDuration().toMillis) / 100)
      mediaPlayer.seek(new Duration(seektime))
    }
  }

  def setVolume(): Unit = {
    val volume: Double = volumeSlider.getValue
    mediaPlayer.setVolume(volume / 100)
    volumeLabel.setText(volume.toInt.toString + "%")
  }



  private def msToMinSec(duration: Duration):(Int,Int,Int)={
    val hours:Int   = math.floor(duration.toHours).toInt
    val minutes:Int = math.floor(duration.toMinutes).toInt-(hours*60)
    val sec:Int     = math.floor(duration.toSeconds).toInt-(hours*60*60)-(minutes*60)

    (hours,minutes,sec)
  }

  //MediaPlayer Functions
  def playpause(): Unit = {
    if (!mediaPlayer.getStatus.equals(MediaPlayer.Status.PLAYING)) {
      togglePlayPause.setText("Pause")
      mediaPlayer.play()
    } else {
      togglePlayPause.setText("Play")
      mediaPlayer.pause()
    }

  }

  def setListeners(): Unit ={
    //progress Slider updater
    mediaPlayer.currentTimeProperty().addListener(new ChangeListener[Duration] {
      override def changed(observable: ObservableValue[_ <: Duration], oldValue: Duration, newValue: Duration): Unit = {
        val time:(Int,Int,Int)= msToMinSec(newValue)
        currentTimeLabelSet(time._2+":"+time._3)
        setDurationSlider((newValue.toSeconds * 100) / mediaPlayer.getTotalDuration.toSeconds)
      }
    })
  }

  def selectFromList(): Unit ={
    val song:Song = songList.getSelectionModel.getSelectedItems.get(0)
    mediaChange(song.filepath)
    musicNameLabel.setText(song.name)
  }

  def resetPlayButton(): Unit ={
    togglePlayPause.setSelected(false)
    togglePlayPause.setText("Play")
  }

  def mediaChange(filepath:String): Unit ={

    val media :Try[Media]= Try(new Media(new File(filepath).toURI.toString))
    media match{
      case Success(v) => {
        val volume = mediaPlayer.getVolume
        mediaPlayer.dispose()
        mediaPlayer = new MediaPlayer(v)
        mediaPlayer.setVolume(volume)
        setListeners()
        resetPlayButton()
      }
      case Failure(e) => {
        print("Erro a criar media")
        throw e
      }

    }

  }









  private def uploadSong(selectedFile: File): Unit={
    val media = new Media(selectedFile.toURI.toString)
    val metadataMediaPlayer = new MediaPlayer(media)
    val info:ListBuffer[(String,AnyRef)]=ListBuffer[(String,AnyRef)]()
    media.getMetadata().addListener(new MapChangeListener[String , AnyRef]{
      override def onChanged(change: MapChangeListener.Change[_ <: String, _ <: AnyRef]): Unit ={
        if(change.wasAdded()){
          info.addOne((change.getKey() , change.getValueAdded))
        }
      }
    })
    metadataMediaPlayer.setOnReady(new Runnable {
      override def run(): Unit = {

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

        val trackNaux:ListBuffer[(String,AnyRef)]=info.filter(x => x._1.equals("track number"))
        val trackN = if(trackNaux.isEmpty){
          0
        }else{
          trackNaux(0)._2.toString.trim
        }
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
}
