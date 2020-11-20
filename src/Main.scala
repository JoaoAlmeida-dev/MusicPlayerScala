import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import Data._
import com.sun.xml.internal.bind.v2.TODO

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ListBuffer

object Main {

  def  main (args: Array[String]): Unit = {

    DatabaseFunc.loadfiles()
    DatabaseFunc.printLoaded()



    mainLoop()
    /*
    printLoaded()
    println()
    println(DatabaseFunc.loadedAlbums(0))
    val artist1 = DatabaseFunc.loadedArtists(0)
    DatabaseFunc.update(artist1,2,"1")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)
    DatabaseFunc.update(artist1,2,"1 2 3 4")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)

    list MusicObjects sorted by field
    val listsorted:List[Song]= Song.loaded.toList.sortWith((x1,x2)=>x1.trackN<x2.trackN)
    listsorted.map(println)

    */

  }

  /*
   TODO
    play music files
    import music from file system
    list MusicObjects sorted by field
    menus para navegar pela db (cmd style)

  */

  def showPropt(): Unit ={

    println("-----------------------MusicPlayer in SCALA-----------------------")
    println("Options:")
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
  def mainLoop(): Unit ={
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
