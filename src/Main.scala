import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import Data._

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ListBuffer

object Main {

  def  main (args: Array[String]): Unit = {

    DatabaseFunc.loadfiles()
    //printLoaded()
    println()
    /*
    println(DatabaseFunc.loadedAlbums(0))
    val artist1 = DatabaseFunc.loadedArtists(0)
    DatabaseFunc.update(artist1,2,"1")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)
    DatabaseFunc.update(artist1,2,"1 2 3 4")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)
    */
    println("------------SONG------------")
      println(Song.loaded)
    /*
    println("------------ARTIST------------")
      println(DatabaseFunc.get(Object[Artist]))
    println("------------ALBUM------------")
      println(DatabaseFunc.get(Object[Album]))
    println("------------PLAYLIST------------")
      println(DatabaseFunc.get(Object[Playlist]))*/
    //println(getSongfromBD(Datatype.SONG,"song1"))
  }




  /*
  Lista de funções
      sacar info da musica a partir do filepath(1ª criação da musica / importar musica para o sistema)

  */

  /*
  def showPropt(): Unit ={
      print()
  }
  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }
  @tailrec
  def mainLoop(): Unit ={
    showPropt()
    val userInput:String = getUserInput()
    userInput match{


      }
  }


  -----------------------------TASKS----------------------------------------------
  ler ficheiros mp3
  dar play a ficheiros mp3

  fazer o menu da cmd

  */
}
