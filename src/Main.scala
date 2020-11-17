import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import Data._

import scala.annotation.tailrec
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
    println()
    println("song:"+getlastid(db_songs))
    println("album:"+getlastid(db_albums))
    println("artist:"+getlastid(db_artists))
    println("palylist:"+getlastid(db_playlists))
    //println(getSongfromBD(Datatype.SONG,"song1"))

    /*
    println("before:")
    println(loadedSongs)
    deletefromDB(loadedSongs(1),db_songs)
    println("after:")
    println(loadedSongs)
    */

  }

  def showPropt(): Unit ={
      print()
  }

  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }
  def loadfiles(): Unit={
    loadedSongs     = new ListBuffer[Song]()
    loadedArtists   = new ListBuffer[Artist]()
    loadedAlbums    =  new ListBuffer[Album]()
    loadedPlaylists = new ListBuffer[Playlist]()
    readFile(loadSong,db_songs)
    readFile(loadArtist,db_artists)
    readFile(loadAlbum,db_albums)
    readFile(loadPlaylist,db_playlists)
  }

  def unload[A](a:A):Unit= a match{
    case a : Data.Song =>
      println("unloaded "+ a.toString)
      loadedSongs -= a.asInstanceOf[Data.Song]

    case a : Data.Artist =>
      println("unloaded " + a.toString)
      loadedArtists -= a.asInstanceOf[Data.Artist]

    case a : Data.Album =>
      println("unloaded " + a.toString)
      loadedAlbums -= a.asInstanceOf[Data.Album]

    case a : Data.Playlist =>
      println("unloaded " + a.toString)
      loadedPlaylists -= a.asInstanceOf[Data.Playlist]

  }


  def update [A](a:A,field:Int, newv:String):Unit = a match{
    case a : Data.Song =>
      val song:Song = a.asInstanceOf[Data.Song]
      val loadedSong = loadedSongs.filter(_.id==song.id)(0)

      //val loadedSongUpdated:Song = Song(loadedSong.filepath,loadedSong.name,loadedSong.duration)

    case a : Data.Artist =>

      loadedArtists

    case a : Data.Album =>

      loadedAlbums

    case a : Data.Playlist =>

      loadedPlaylists

  }

  def loadSong(line: String): Unit={
    val info=line.split(";").toList
    val song:Song = Song(info(0),info(1),info(2),info(3),info(4),info(5),info(6),info(7),info(8),info(9))
    loadedSongs+= song
    println("Song loaded from DB")
  }
  def loadArtist(line: String): Unit={
    val info=line.split(";").toList
    val artist:Artist = Artist(info(0),info(1),info(2),info(3))
    loadedArtists+= artist
    println("Artist loaded from DB")
  }
  def loadAlbum(line: String): Unit={
    val info=line.split(";").toList
    val album:Album = Album(info(0),info(1),info(2),info(3))
    loadedAlbums+=album
    println("Album loaded from DB")
  }
  def loadPlaylist(line: String): Unit={
    val info=line.split(";").toList
    val playlist:Playlist =Playlist(info(0),info(1),info(2),info(3))
    loadedPlaylists+=playlist
    println("Playlist loaded from DB")
    playlist
  }

  def readFile(load: String=>Any, filename: String): Unit={
    val bufferedFile = Source.fromFile(filename)
    val lines = bufferedFile.getLines.toList
    readline(load,lines)
    bufferedFile.close()

  }
  @tailrec
  def readline(load: String=>Any, lines: List[String]): Unit= lines match{
    case a::Nil => load(a)
    case a::t => load(a);readline(load,t)
  }
  def addtoDB[A](a: A, dbPath: String):Unit={
    val file=new File(dbPath)
    val bw = new BufferedWriter(new FileWriter(file, true))
    bw.newLine()
    bw.write(a.toString)
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

    unload(a)
  }
  @tailrec
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
  def getlastid(filename:String): Int ={
    val bufferedFile = Source.fromFile(filename)
    val lines = bufferedFile.getLines.toList
    bufferedFile.close()
    lines.last.split(";").toList(0).toInt
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
