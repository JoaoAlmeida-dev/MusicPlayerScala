import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import Data._
import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.{Group, Parent, Scene}
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.stage.{FileChooser, Stage}
import javafx.util.Duration

import scala.annotation.tailrec
import scala.io.StdIn.readLine


object Main {
  def main(args: Array[String])
  {
    Application.launch(classOf[Main], args: _*)
    //DatabaseFunc.loadfiles()
    //DatabaseFunc.printLoaded()
    //mainLoop()
  }


}

class Main extends Application{

  override def start(primaryStage: Stage): Unit = {

    primaryStage.setTitle("MP3")
    /*
    val file:File = new File("C:/Joao/Music/Arctic Monkeys/AM (Album)/test.mp3")
    val fileChooser = new FileChooser
    val selectedFile:File = fileChooser.showOpenDialog(primaryStage)

    //val resource = getClass.getClassLoader.getResource("C:/Joao/Music/doiwannaknow.mp3")
    println(selectedFile.getAbsoluteFile)
    val resource = getClass.getClassLoader.getResource(selectedFile.getAbsolutePath)
    //val pick: Media = new Media(resource.toString)
    val pick: Media = new Media(selectedFile.toURI.toString)
    val player: MediaPlayer = new MediaPlayer(pick)
    val m: MediaView = new MediaView(player)
    */

    val fxmlLoader = new FXMLLoader(getClass.getResource("Controller.fxml"))

    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()

  }
  /*
   TODO
    play music files
    import music from file system
      create song from file
        create/check artist/album on db and if fail create and add them
    menus para navegar pela db (cmd style)
    ficheiro cache pa guardar o estado da app ao fechar (queue, time da musica atual e assim)
    funcoes das paylists (criar uma nova playlist, adicionar musica, remover musica )
  */

  def showPropt(): Unit ={

    println("-----------------------MusicPlayer in SCALA-----------------------")
    println("Menu, type the specified line below to choose:")
    println("    Display")
    println("    Option 2")
    println("    Option 3")
  }

  def displayPrompt():Unit= {
    println("Write Songs to display the available songs")
    println("Write Artists to display the available artists")
    println("Write Albums to display the available albums")
    println("Write Playlists to display the available playlists")

  }
  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }

  @tailrec
  final def mainLoop(): Unit ={
    showPropt()
    val userInput:String = getUserInput()
    userInput match{
      case "DISPLAY" => display() ;mainLoop()
      case _ =>println("\n Nenhuma das opcoes foi selecionada, tente novamente")    ;mainLoop()

      }
  }

  def display():Unit={
    displayPrompt()
    val userInput:String = getUserInput()

    userInput match{
      case "ARTISTS" => println("Artists:");Artist.getLoaded().map(println)
      case "ALBUMS"  =>println("Albums");Album.getLoaded().map(println)
      case "SONGS"   =>println("Songs");Song.getLoaded().map(println)
      case "PLAYLISTS"   =>println("PLAYLISTS");Playlist.getLoaded().map(println)
      case _ =>println("\n Nenhuma das opcoes foi selecionada, tente novamente")  ;mainLoop()

    }
  }

}
