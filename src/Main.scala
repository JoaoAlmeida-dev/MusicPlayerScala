import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import Data._

import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ListBuffer

object Main {
  var loadedSongs = new ListBuffer[Song]()
  var loadedArtists = new ListBuffer[Artist]()
  var loadedAlbums =  new ListBuffer[Album]()
  var loadedPlaylists = new ListBuffer[Playlist]()
  val db_songs="DataBases/db_songs"
  val db_artists="DataBases/db_artists"
  val db_albums="DataBases/db_albums"
  val db_playlists="DataBases/db_playlists"

  def  main (args: Array[String]): Unit = {
    loadfiles()
    printLoaded()
  }

  def showPropt(): Unit ={
      print()
  }

  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }
  def loadfiles(): Unit={
    loadedSongs = new ListBuffer[Song]()
    loadedArtists = new ListBuffer[Artist]()
    loadedAlbums =  new ListBuffer[Album]()
    loadedPlaylists = new ListBuffer[Playlist]()
    readFile(loadSong,db_songs)
    readFile(loadArtist,db_artists)
    readFile(loadAlbum,db_albums)
    readFile(loadPlaylist,db_playlists)
  }
  def loadSong(line: String): Unit={
    val info=line.split(";").toList
    loadedSongs+=Song(info(0),info(1),info(2).toInt,info(3),info(4),info(5),info(6),info(7).toInt)
    println("Song loaded from DB")
  }
  def loadArtist(line: String): Unit={
    val info=line.split(";").toList
    loadedArtists+=Artist(info(0),info(1),info(2))
    println("Artist loaded from DB")
  }
  def loadAlbum(line: String): Unit={
    val info=line.split(";").toList
    loadedAlbums+=Album(info(0),info(1),info(2))
    println("Album loaded from DB")
  }
  def loadPlaylist(line: String): Unit={
    val info=line.split(";").toList
    loadedPlaylists+=Playlist(info(0),info(1),info(2))
    println("Playlist loaded from DB")
  }
  def readFile(load: String=>Unit, filename: String): Unit={
    val bufferedFile = Source.fromFile(filename)
    val lines = bufferedFile.getLines.toList
    readline(load,lines)
    bufferedFile.close()

  }
  def readline(load: String=>Unit, lines: List[String]): Unit= lines match{
    case a::Nil => load(a)
    case a::t => load(a);readline(load,t)
  }
  def addtoDB[A](a: A, dbPath: String):Unit={
    var file=new File(dbPath)
    val bw = new BufferedWriter(new FileWriter(file, true))
    bw.newLine()
    bw.write(a.toString())
    bw.close()
  }
  def deletefromDB[A](a: A, dbPath: String):Unit={
    val bufferedFile = Source.fromFile(dbPath)
    val lines = bufferedFile.getLines.toList.filter(_ != a.toString)
    bufferedFile.close()
    var file=new File(dbPath)
    val bw = new BufferedWriter(new FileWriter(file))
    writeFileAfterDelete(bw,lines)
    bw.close()
  }
  def writeFileAfterDelete(bw: BufferedWriter, lines: List[String]): Unit = lines match{
    case a::Nil => bw.write(a);bw.newLine();bw.close()
    case a::t => bw.write(a);bw.newLine();writeFileAfterDelete(bw,t)
  }
  def printLoaded(): Unit={
    loadedSongs.foreach{println}
    loadedArtists.foreach{println}
    loadedAlbums.foreach{println}
    loadedPlaylists.foreach{println}
  }

  /*
  Lista de funções
      sacar info da musica a partir do filepath(1ª criação da musica / importar musica para o sistema)

  */

  /*
  @tailrec
  def mainLoop(): Unit ={
    showPropt()
    val userInput:String = getUserInput()
    userInput match{


      }
  }
  */
}
