import java.io.{BufferedWriter, File, FileWriter}

import Data.{Album, Artist, Object, Playlist, Song}
import com.sun.prism.PixelFormat.{BYTE_ALPHA, DataType}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.Source

object DatabaseFunc {
/*
  var loadedSongs = new ListBuffer[Song]()
  var loadedArtists = new ListBuffer[Artist]()
  var loadedAlbums =  new ListBuffer[Album]()
  var loadedPlaylists = new ListBuffer[Playlist]()
  val db_songs="DataBases/db_songs"
  val db_artists="DataBases/db_artists"
  val db_albums="DataBases/db_albums"
  val db_playlists="DataBases/db_playlists"
*/
  def loadfiles(): Unit={
    /*
    loadedSongs     = new ListBuffer[Song]()
    loadedArtists   = new ListBuffer[Artist]()
    loadedAlbums    =  new ListBuffer[Album]()
    loadedPlaylists = new ListBuffer[Playlist]()
    */
    readFile(Song.load,Song.db)
    readFile(Album.load,Album.db)
    readFile(Artist.load,Artist.db)
    readFile(Playlist.load,Playlist.db)
  }
/*
  def loadSong(line: String): Unit={
    val info=line.split(";").toList
    val song:Song = Song(info(0),info(1),info(2),info(3),info(4),info(5),info(6),info(7),info(8),info(9))
    loaded+= song
    println("Loaded " + line)
  }
  def loadArtist(line: String): Unit={
    val info=line.split(";").toList
    val artist:Artist = Artist(info(0),info(1),info(2),info(3))
    loadedArtists+= artist
    println("Loaded " + line)
  }
  def loadAlbum(line: String): Unit={
    val info=line.split(";").toList
    val album:Album = Album(info(0),info(1),info(2),info(3))
    loadedAlbums+=album
    println("Loaded " + line)
  }
  def loadPlaylist(line: String): Unit={
    val info=line.split(";").toList
    val playlist:Playlist =Playlist(info(0),info(1),info(2),info(3))
    loadedPlaylists+=playlist
    println("Loaded " + line)
  }
*/
  def unload[A](a:A):Unit= a match{
    case a: Data.Object[A] => {
      println("Unloaded" + a.toString)
      a.loaded -= a.asInstanceOf[A]
    }
  }
  /*
    case a : Data.Song =>
      println("Unloaded "+ a.toString)
      loadedSongs -= a.asInstanceOf[Data.Song]
    case a : Data.Artist =>
      println("Unloaded " + a.toString)
      loadedArtists-= a.asInstanceOf[Data.Artist]
    case a : Data.Album =>
      println("Unloaded " + a.toString)
      loadedAlbums -= a.asInstanceOf[Data.Album]
    case a : Data.Playlist =>
      println("Unloaded " + a.toString)
      loadedPlaylists -= a.asInstanceOf[Data.Playlist]
      */

  def update [A](a:Object[A],field:Int, newv:String):Unit = {
    if(field ==0){
      println("UpdateError:Cant change id")
    }else{
      a match {
        case a:Data.Object[A]=>
          val objectold:Object[A] = a.asInstanceOf[Object[A]]
          val loadedObject:A =objectold.loaded.filter(_.asInstanceOf[Object[A]].id == objectold.id)(0)
          val info: List[String] = loadedObject.toString().split(";").toList.updated(field,newv)
          val objectNew:A = a.getClass.getConstructor(classOf[Object[A]]).newInstance(info).asInstanceOf[A]

          deletefromDB(loadedObject, a.db)
          addtoDB(objectNew, a.db)
          a.load(info.mkString(";"))
      }
        /*case a: Data.Song =>
          val objectOld: Song = a.asInstanceOf[Data.Song]
          val loadedObject: Song = loadedSongs.filter(_.id == objectOld.id)(0)
          val info: List[String] = loadedObject.toString().split(";").toList.updated(field,newv)
          val objectNew: Song = Song(info(0), info(1), info(2), info(3), info(4), info(5), info(6), info(7), info(8), info(9))

          deletefromDB(loadedObject, db_songs)
          addtoDB(objectNew, db_songs)
          loadSong(info.mkString(";"))

        case a: Data.Artist =>
          val objectOld: Artist = a.asInstanceOf[Data.Artist]
          val loadedObject: Artist = loadedArtists.filter(_.id == objectOld.id)(0)
          val info: List[String] = loadedObject.toString().split(";").toList.updated(field,newv)
          val objectNew: Artist = Artist(info(0),info(1),info(2),info(3))

          deletefromDB(loadedObject, db_artists)
          addtoDB(objectNew, db_artists)
          loadArtist(info.mkString(";"))

        case a: Data.Album =>
          val objectOld: Album = a.asInstanceOf[Data.Album]
          val loadedObject: Album = loadedAlbums.filter(_.id == objectOld.id)(0)
          val info: List[String] = loadedObject.toString().split(";").toList.updated(field,newv)
          val objectNew: Album = Album(info(0),info(1),info(2),info(3))

          deletefromDB(loadedObject, db_albums)
          addtoDB(objectNew, db_albums)
          loadAlbum(info.mkString(";"))

        case a: Data.Playlist =>
          val objectOld: Playlist = a.asInstanceOf[Data.Playlist]
          val loadedObject: Playlist = loadedPlaylists.filter(_.id == objectOld.id)(0)
          val info: List[String] = loadedObject.toString().split(";").toList.updated(field,newv)
          val objectNew: Playlist = Playlist(info(0),info(1),info(2),info(3))

          deletefromDB(loadedObject, db_playlists)
          addtoDB(objectNew, db_playlists)
          loadPlaylist(info.mkString(";"))

      }*/
    }
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
    bw.write(a.toString)
    bw.newLine()
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
    Song.loaded.foreach{println}
    Artist.loaded.foreach{println}
    Album.loaded.foreach{println}
    Playlist.loaded.foreach{println}
  }

  def getlastid(filename:String): Int ={
    val bufferedFile = Source.fromFile(filename)
    val lines = bufferedFile.getLines.toList
    bufferedFile.close()
    lines.last.split(";").toList(0).toInt
  }



}
