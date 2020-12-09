import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import Data.{DatabaseFunc, _}
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

    primaryStage.setTitle("MusicPlayerScala")

    val fxmlLoader = new FXMLLoader(getClass.getResource("Controller.fxml"))

    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

  /*
   TODO
    Playlists
      create
      add
      remove
      addSong
      removeSong
      ChangeOrder
    Show Musics from artist or from album
    ficheiro cache pa guardar o estado da app ao fechar (queue, time da musica atual e assim)
    menus para navegar pela db (cmd style)
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
