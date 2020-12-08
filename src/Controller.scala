
import Data.{Album, Artist, MusicObject, Song}
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.{FXCollections, ListChangeListener, MapChangeListener, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, ListView, MultipleSelectionModel, Slider, ToggleButton}
import javafx.scene.layout.{AnchorPane, FlowPane, GridPane}
import javafx.scene.media.{Media, MediaPlayer}
import javafx.stage.{FileChooser, Stage}
import javafx.util.Duration
import java.io.File

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}


class Controller {

  @FXML private var centerGrid: GridPane = _
  @FXML private var bottomGrid: GridPane = _
  @FXML private var togglePlayPause: ToggleButton = _
  @FXML private var chooseFileButton: Button = _
  @FXML private var musicNameLabel: Label = _
  @FXML private var volumeSlider: Slider = _
  @FXML private var durationSlider: Slider = _
  @FXML private var minDurationLabel: Label = _
  @FXML private var maxDurationLabel: Label = _
  @FXML private var volumeLabel: Label = _
  @FXML private var leftPane: AnchorPane = _
  @FXML private var listSongs: ListView[Song] = new ListView()
  @FXML private var listAlbums: ListView[Album] = new ListView()
  @FXML private var listArtists: ListView[Artist] = new ListView()
  @FXML private var randomButton: ToggleButton = _
  @FXML private var repeatButton: ToggleButton = _


  var mediaPlayer: MediaPlayer = _

  def initialize(): Unit ={
    DatabaseFunc.loadfiles()
    setLoadedListners()
    updateListSongs()
    updateListAlbums()
    updateListArtists()
    //val firstSongPath:String=Song.loaded(0).filepath
    //mediaPlayer = new MediaPlayer(new Media(new File(firstSongPath).toURI.toString))
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
        val currtime:(Int,Int,Int)= msToMinSec(newValue)

        currentTimeLabelSet(currtime._2+":"+currtime._3)

        setDurationSlider((newValue.toSeconds * 100) / mediaPlayer.getTotalDuration.toSeconds)

      }
    })

    //onEndoFMedia
    mediaPlayer.setOnEndOfMedia(new Runnable {
      override def run(): Unit = {
        next()
      }
    })

    }



  def selectFromListSongs(): Unit ={
    val song:Song = listSongs.getSelectionModel.getSelectedItems.get(0)
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
        if(!mediaPlayer.isInstanceOf[MediaPlayer]){
          //mediaPlayer has not been instanciated
          mediaPlayer = new MediaPlayer(v)
        }else{
          val volume = mediaPlayer.getVolume
          mediaPlayer.dispose()
          mediaPlayer = new MediaPlayer(v)
          mediaPlayer.setVolume(volume)

        }
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

    val runner = new Runnable {
      override def run(): Unit= {
        println(info)
        val album:String = info.filter( x=>x._1.equals("album") ) (0)._2.toString.trim
        val artist:String = info.filter( x=>x._1.equals("artist") ).map(_._2).remove(0).toString.split(",").head.trim
        val songid = DatabaseFunc.getlastidSongs(Song.loaded)+1

        val albumcheck =Album.loaded.filtered(x=>x.name.equals(album))

        val artistcheck=Artist.loaded.filtered(x=>x.name.equals(artist))

        println()
        println()
        println(album + "   "+ albumcheck)
        println(artist + "   "+ artistcheck)
        println()
        println()

        val artistid:Int={
          if (artistcheck.isEmpty) {
            val newid:Int=DatabaseFunc.getlastidArtists(Artist.loaded)+1
            newid
          } else {
            artistcheck.get(0).id
          }
        }
        //album exists? if not creating it

        val albumid:Int = {
          if (albumcheck.isEmpty) {
            val newid:Int=DatabaseFunc.getlastidAlbums(Album.loaded)+1
            Album.loaded.add(Album( List(newid.toString,album,songid.toString,artistid.toString) ))
            newid
          } else {
            val album_temp=albumcheck.get(0).addSong(songid)
            Album.loaded.remove(albumcheck.get(0))
            Album.loaded.add(album_temp)
            albumcheck.get(0).id
          }
        }

        //artist exists? if not creating it

        if (artistcheck.isEmpty) {
          Artist.loaded.add(Artist( List(artistid.toString,artist,albumid.toString,songid.toString)  ))
        } else {
          val artist_temp=artistcheck.get(0).addSong(songid)
          Artist.loaded.remove(artistcheck.get(0))
          Artist.loaded.add(artist_temp.addAlbum(albumid))
        }


        val nomeFeats = info.filter( x=>x._1.equals("artist") ).map(_._2).remove(0).toString.split(", ").tail.toList

        val idFeats=nomeFeats.map(x=>DatabaseFunc.GetIDArtistOrCreateFeats(x.trim,songid.toString))

        val trackNaux:ListBuffer[(String,AnyRef)]=info.filter(x => x._1.equals("track number"))
        val trackN = if(trackNaux.isEmpty){0}else{trackNaux(0)._2.toString.trim}

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
        Song.loaded.add(song)

      }
      }
    metadataMediaPlayer.setOnReady(runner)

  }
  def setLoadedListners(): Unit ={
    Song.loaded.addListener( new ListChangeListener[Song]{
      override def onChanged(change: ListChangeListener.Change[_ <: Song]): Unit ={
        while(change.next()){
          if(change.wasAdded()) {
            println("Uma nova música foi adicionada")
            updateListSongs()
          }
        }
      }
    } )

    Artist.loaded.addListener( new ListChangeListener[Artist]{
      override def onChanged(change: ListChangeListener.Change[_ <: Artist]): Unit ={
        while(change.next()){
          if(change.wasAdded()) {
            println("Um novo artista foi adicionado")
            updateListArtists()
          }
        }
      }
    } )

    Album.loaded.addListener( new ListChangeListener[Album]{
      override def onChanged(change: ListChangeListener.Change[_ <: Album]): Unit ={
        while(change.next()){
          if(change.wasAdded()) {
            println("Uma novo album foi adicionado")
            updateListAlbums()
          }
        }
      }
    } )

  }
  def updateListSongs(): Unit ={
    listSongs.getItems.clear()
    Song.loaded.forEach(x=>listSongs.getItems.add(x))
    println("------------- número de items "+  listSongs.getItems.size)
    listSongs.getItems.forEach(println)
  }
  def updateListAlbums(): Unit ={
    listAlbums.getItems.clear()
    Album.loaded.forEach(x=>listAlbums.getItems.add(x))
    println("------------- número de items "+  listAlbums.getItems.size)
    listAlbums.getItems.forEach(println)
  }
  def updateListArtists(): Unit ={
    listArtists.getItems.clear()
    Artist.loaded.forEach(x=>listArtists.getItems.add(x))
    println("------------- número de items "+  listArtists.getItems.size)
    listArtists.getItems.forEach(println)
  }
  def before(): Unit ={
    val song:Song = listSongs.getSelectionModel.getSelectedItems.get(0)
    if(mediaPlayer.getCurrentTime.toSeconds>3){
      mediaPlayer.seek(new Duration(0))
    }else{
      if(repeatButton.isSelected){
        mediaPlayer.seek(new Duration(0))
      }
      else if(randomButton.isSelected){
        val r = scala.util.Random
        val pos = r.nextInt(listSongs.getItems.size())
        gotoSong(pos)
      }else{
        val pos=listSongs.getItems.lastIndexOf(song)-1
        if(pos<0){
          gotoSong(listSongs.getItems.size-1)
        }else{
          gotoSong(pos)
        }
      }
    }
  }
  def next(): Unit ={
    val song:Song = listSongs.getSelectionModel.getSelectedItems.get(0)
    if(repeatButton.isSelected){
      mediaPlayer.seek(new Duration(0))
    }
    else if(randomButton.isSelected){
      val r = scala.util.Random
      val pos = r.nextInt(listSongs.getItems.size())
      gotoSong(pos)
    }else{
      val pos=listSongs.getItems.lastIndexOf(song)+1
      if(pos>listSongs.getItems.size-1){
        gotoSong(0)
      }else{
        gotoSong(pos)
      }
    }
  }
  private def gotoSong(pos: Int): Unit ={
    val newSong=listSongs.getItems.get(pos)
    listSongs.getSelectionModel.select(pos)
    mediaChange(newSong.filepath)
    musicNameLabel.setText(newSong.name)
    mediaPlayer.play()
  }
  def random(): Unit ={
    if(repeatButton.isSelected){
      repeatButton.setSelected(false)
      mediaPlayer.setCycleCount(1)
    }
  }

  def repeat(): Unit ={
    if(randomButton.isSelected){
      randomButton.setSelected(false)
    }
    if(repeatButton.isSelected){
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE)
    }else{
      mediaPlayer.setCycleCount(1)
    }
  }
}
