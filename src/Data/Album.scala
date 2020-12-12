package Data
import javafx.collections.{FXCollections, ObservableList}

import scala.List

case class Album(id: Int,name:String, tracks:List[Int], artist:Int) extends MusicObject[Album]{
  def info():Option[(Int,String,List[Int],Int)]= { Album.info(this) }

  def addSong(song: Int): Album= { Album.addSong(this,song) }
  def addSong(songs: List[Int]): Album= { Album.addSong(this,songs) }
  def getSongs():List[Song] = {Album.getSongs(this)}

  override def delete(): Unit = { Album.delete(this) }

/*
  def removeSong(song: Int): Album= { Album.removeSong(this,song) }
  def removeSong(songs: List[Int]): Album= { Album.removeSong(this,songs) }
*/
  override def toString(): String ={ Album.toString(this) }
  override val db: String = Album.db
  override var loaded: ObservableList[Album] = Album.loaded
  override def load(line: String): Unit = Album.load(line)
  override val constructN: Int = 4

  override def apply(info:List[String]):Album=Album.apply(info)

  override def getLoaded[Album](): List[String] = Album.getLoaded[Album]()

}

object Album {
  type id = Int
  type Name = String
  type Tracks = List[Int]
  type Artist = Int

  val db: String = "DataBases/db_albums"
  var loaded: ObservableList[Album] = FXCollections.observableArrayList[Album]()

  def getLoaded[Album](): List[String] = {
    this.loaded.toArray.toList.map(_.toString.split(";").toList.drop(1).dropRight(1).mkString(";"))
  }

  def load(line: String): Unit={
    val info=line.split(";").toList
    //val album:Album = Album(info(0),info(1),info(2),info(3))
    val album:Album = Album(info)
    loaded.add(album)
    //println("Loaded " + line)
  }

  def info(a: Album): Option[(Int,String, List[Int], Int)] = {
    Option(
      a.id,
      a.name,
      a.tracks,
      a.artist
    )
  }
//------------------- LOAD APPLY
  //def apply(id: String,name: String, tracks: String, artist:String): Album = {
  def apply(info:List[String]): Album = {
    val tracksList= info(2).split(" ").toList
    if(tracksList(0)==""){
      //no traks
      Album(info(0).toInt,info(1),List(), info(3).toInt)
    } else{
      Album(info(0).toInt,info(1),tracksList.map(_.toInt), info(3).toInt)
    }
  }
//------------------

  def addSong(a: Album, songid: Int): Album = {
    DatabaseFunc.update[Album](a,2,(a.tracks:::List(songid)).mkString(" "))
  }
  def addSong(a: Album, songsid: List[Int]): Album = {
    DatabaseFunc.update[Album](a,2,(a.tracks:::songsid).mkString(" "))
  }

  def remTrack(a: Album, songid: Int): Album = {
    DatabaseFunc.update[Album](a,2,(a.tracks:::List(songid)).mkString(" "))
  }
  def remArtist(a: Album, songsid: List[Int]): Album = {
    DatabaseFunc.update[Album](a,2,(a.tracks:::songsid).mkString(" "))
  }

  def delete(a:Album): Unit ={
    loaded.remove(a)
    val songsfiltered:List[Song] = DatabaseFunc.observableListToList(Song.loaded).filter(x=>x.album == a.id)
    val artistsFiltered:List[Data.Artist] = DatabaseFunc.observableListToList(Artist.loaded).filter(x=>x.albums.contains(a))
    artistsFiltered.map(x=> x.remAlbum(a.id))
    artistsFiltered.map(x=>songsfiltered.map(y=> x.remSong(y.id)))
    DatabaseFunc.observableListToList(Playlist.loaded).map(x=>x.removeSongsFromAlbum(a.id))
    songsfiltered.map(x=>x.delete())
  }

  def getSongs(a:Album): List[Data.Song] ={
    DatabaseFunc.observableListToList(Song.loaded).filter(x=>a.tracks.contains(x.id))
  }

  def getArtist(a:Album): List[Data.Artist] ={
    DatabaseFunc.observableListToList(Artist.loaded).filter(x=>x.id == a.artist)
  }

  def toString(a: Album): String={
    a.id+";"+a.name+";"+a.tracks.mkString(" ")+";"+a.artist+";end;"
  }

}