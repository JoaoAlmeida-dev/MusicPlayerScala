import Data.Song
import javafx.scene.media.{Media, MediaPlayer}
import javafx.util.Duration

import scala.reflect.io.File
import scala.util.{Failure, Success, Try}

case class MediaController (path:String,player: MediaPlayer,pick:Media){

  def playpause():Unit={MediaController.playpause(this)}
  def seek(time:Duration):Unit={MediaController.seek(this,time)}
  def setVolume(vol:Double):Unit={MediaController.setVolume(this,vol)}

  def getMaxDuration():Duration={MediaController.getMaxDuration(this)}
  def getCurrentTime():Duration={MediaController.getCurrentTime(this)}

}
//Classe para encapsular a troca de media e trocar a musica que dá play

object MediaController{

type path =String
type player =MediaPlayer
type pick =Media


  def playpause(mc:MediaController): Unit = {
    if (!mc.player.getStatus.equals(MediaPlayer.Status.PLAYING)) {
      mc.player.play()
    } else {
      mc.player.pause()
    }
  }

  private def seek(mc:MediaController,time:Duration): Unit ={
    mc.player.seek(time)
  }

  private def setVolume(mc:MediaController,vol:Double): Unit ={
    mc.player.setVolume(math.max(0,math.min(vol,100)))
  }
  private def getMaxDuration(mc:MediaController): Duration ={
    mc.player.getTotalDuration
  }
  private def getCurrentTime(mc:MediaController): Duration ={
    mc.player.getCurrentTime()
  }



  def apply(path:String):MediaController={
    val pick= Try( new Media(File(path).toURI.toString))
    pick match{
      case Success(v) => MediaController( path, new MediaPlayer(v),v)
      case Failure(e) => throw e.getCause

    }
  }



}