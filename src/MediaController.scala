import javafx.scene.media.{Media, MediaPlayer}

import javafx.util.Duration
import scala.reflect.io.File
import scala.util.{Failure, Success, Try}

case class MediaController (path:String,player: MediaPlayer){





}


object MediaController{

type player =MediaPlayer


  def playpause(mc:MediaController): Unit = {
    if (!mc.player.getStatus.equals(MediaPlayer.Status.PLAYING)) {
      mc.player.play()
    } else {
      mc.player.pause()
    }
  }

  def seek(mc:MediaController,time:Duration): Unit ={
    mc.player.seek(time)
  }

  def setVolume(mc:MediaController,vol:Double): Unit ={
    mc.player.setVolume(math.max(0,math.min(vol,100)))
  }

  def apply(path:String):MediaController={
    val pick= Try( new Media(File(path).toURI.toString))
    pick match{
      case Success(v) => MediaController( path, new MediaPlayer(v))
      case Failure(e) => throw e.getCause

    }
  }

}