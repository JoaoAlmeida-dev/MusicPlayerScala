import Data.{DatabaseFunc, _}
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.annotation.tailrec
import scala.io.StdIn.readLine


object Main {
  def main(args: Array[String])
  {
    Application.launch(classOf[Main], args: _*)

    DatabaseFunc.writeDB(Artist.loaded, Artist.db)
    DatabaseFunc.writeDB(Album.loaded, Album.db)
    DatabaseFunc.writeDB(Song.loaded, Song.db)
    DatabaseFunc.writeDB(Playlist.loaded, Playlist.db)

    //DatabaseFunc.printLoaded()
    //mainLoop()
  }

}

class Main extends Application{

  override def start(primaryStage: Stage): Unit = {

    primaryStage.setTitle("MusicPlayerScala - DEMO")

    val fxmlLoader = new FXMLLoader(getClass.getResource("Controller.fxml"))

    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    scene.getStylesheets.add(getClass.getResource("css/style.css").toExternalForm)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

  /*
   TODO
    Playlists
      ChangeOrder
  */

  def showPrompt(): Unit ={

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
    showPrompt()
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
