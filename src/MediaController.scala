import Data.Song
import javafx.scene.media.{Media, MediaPlayer}
import javafx.util.Duration

import scala.reflect.io.File


case class MediaController (path:String,media:Option[Media], mediaPlayer: Option[MediaPlayer]){

  def playpause():Unit={MediaController.playpause(this)}
  def seek(time:Duration):Unit={MediaController.seek(this,time)}
  def setVolume(vol:Double):Unit={MediaController.setVolume(this,vol)}

  def getMaxDuration():Duration={MediaController.getMaxDuration(this)}
  def getCurrentTime():Duration={MediaController.getCurrentTime(this)}



}
//Classe para encapsular a troca de media e trocar a musica que dá play

object MediaController{

type path =String
type media =Option[Media]
type mediaPlayer =Option[MediaPlayer]


  def playpause(mc:MediaController): Unit = {
    if (!mc.mediaPlayer.get.getStatus.equals(MediaPlayer.Status.PLAYING)) {
      mc.mediaPlayer.get.play()
    } else {
      mc.mediaPlayer.get.pause()
    }
  }

  private def seek(mc:MediaController,time:Duration): Unit ={
    mc.mediaPlayer.get.seek(time)
  }

  private def setVolume(mc:MediaController,vol:Double): Unit ={
    mc.mediaPlayer.get.setVolume(math.max(0,math.min(vol,100)))
  }
  private def getMaxDuration(mc:MediaController): Duration ={
    mc.mediaPlayer.get.getTotalDuration
  }
  private def getCurrentTime(mc:MediaController): Duration ={
    mc.mediaPlayer.get.getCurrentTime()
  }

  private def changePlayer(filepath:String,mc:MediaController): Unit ={
    mc.mediaPlayer.get.dispose()

  }

  def apply(filepath:String):MediaController={
    /*
    val pick= Try( new Media(File(filepath).toURI.toString))
    pick match{
      case Success(v) => MediaController( filepath,Option(new Media(File(filepath).toURI.toString)) ,Option(new MediaPlayer(v)))
      case Failure(e) => throw e.getCause

    }
    */
    val media=Option(new Media(File(filepath).toURI.toString))
    MediaController( filepath, media,Option(new MediaPlayer(media.get)))
  }


  def apply():MediaController={
    MediaController("")
  }



}