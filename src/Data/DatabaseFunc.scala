package Data

import javafx.collections.ObservableList

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import scala.annotation.tailrec
import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

object DatabaseFunc {

  def loadfiles(): Unit = {

    readFile(Song.load, Song.db)
    readFile(Album.load, Album.db)
    readFile(Artist.load, Artist.db)
    readFile(Playlist.load, Playlist.db)
  }

  def unload[A](a: A): Unit = a match {
    case a: Data.MusicObject[A] =>
      //println("Unloaded" + a.toString)
      a.loaded.remove(a.asInstanceOf[A])
  }

  def update[A](a: MusicObject[A], field: Int, newv: String): A = {
      val objectold: MusicObject[A] = a.asInstanceOf[MusicObject[A]]
      val loaded : List[MusicObject[A]] = observableListToList(objectold.loaded).asInstanceOf[List[MusicObject[A]]]
      val loadedObject: MusicObject[A] = loaded.filter(_.id == objectold.id).head
      val info: List[String] = loadedObject.toString.split(";").toList.updated(field, newv)
      //val objectNew:A = a.getClass.getConstructor(classOf[MusicObject[A]]).newInstance(info).asInstanceOf[A]

      a.loaded.remove(a)
      a.load(info.mkString(";"))

      a.apply(info)
  }

  //Higher order right here
  def readFile(load: String => Any, filename: String): Unit = {
    val bufferedFile: Try[BufferedSource] = Try(Source.fromFile(filename))
    bufferedFile match {
      case Success(v) =>
        val lines = v.getLines.toList
        if (lines.isEmpty) {
          v.close()
        } else {
          readline(load, lines)
          v.close()
        }
      case Failure(e) =>
        new File("DataBases").mkdir()

        val f: File = new File(filename)
        val writer = new PrintWriter(f)
        writer.write("")
        writer.close()

        //println("Ficheiro de BD não existe" + e.getMessage)
        //throw e
    }
  }

  @tailrec
  def readline(load: String => Any, lines: List[String]): Unit = lines match {
    case a :: Nil => load(a)
    case a :: t => load(a); readline(load, t)
  }

  def getlastidPlaylists(loaded: ObservableList[Playlist]): Int = {
    var max = 0
    loaded.forEach(x => {
      if (x.id > max) max = x.id
    })
    max
  }

  def getlastidSongs(loaded: ObservableList[Song]): Int = {
    var max = 0
    loaded.forEach(x => {
      if (x.id > max) max = x.id
    })
    max
  }

  def getlastidAlbums(loaded: ObservableList[Album]): Int = {
    var max = 0
    loaded.forEach(x => {
      if (x.id > max) max = x.id
    })
    max
  }

  def getlastidArtists(loaded: ObservableList[Artist]): Int = {
    var max = 0
    loaded.forEach(x => {
      if (x.id > max) max = x.id
    })
    max
  }

  def GetIDArtistOrCreateFeats(artist: String, songid: String): String = {
    val artistcheck = Artist.loaded.filtered(x => x.name.equals(artist))

    val artistid: Int = {
      if (artistcheck.isEmpty) {
        val newid: Int = DatabaseFunc.getlastidArtists(Artist.loaded) + 1
        Artist.loaded.add(Artist(List(newid.toString, artist, "", songid)))
        newid
      } else {
        val artist_temp = artistcheck.get(0).addSong(songid.toInt)
        Artist.loaded.remove(artistcheck.get(0))
        Artist.loaded.add(artist_temp)
        artistcheck.get(0).id
      }
    }
    artistid.toString
  }

  def writeDB[A](loaded: ObservableList[A], dbPath: String): Unit = {
    val file = new File(dbPath)
    val bw = new BufferedWriter(new FileWriter(file))
    loaded.forEach(x => {
      bw.write(x.toString + "\n")
    }
    )
    bw.close()
  }

  def observableListToList[A](oblst:ObservableList[A]): List[A] ={
    @tailrec
    def aux(oblst:ObservableList[A],list:List[A], index:Int): List[A]={
      if (oblst.size() == index) {
        list
      } else {
        val obj: A = oblst.get(index)
        aux(oblst, list ::: List(obj), index + 1)
      }
    }
    aux (oblst,List[A](),0)
  }

}
