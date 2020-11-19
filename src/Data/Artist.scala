package Data

import Data.Artist.addAlbum

import scala.collection.mutable.ListBuffer

case class Artist(id:Int,name:String, albums: List[Int], songs:List[Int] ) extends Object[Artist]{
  def info():Option[(Int,String,List[Int],List[Int])]={ Artist.info(this) }
  def addSong(song: Int): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: Int): Artist={ Artist.addAlbum(this, album) }

  override def toString(): String ={ Artist.toString(this) }
  override val db: String =Artist.db
  override var loaded: ListBuffer[Artist] = Artist.loaded
  override def load(line: String): Unit = Artist.load(line)

  override val constructN: Int = 4
}

object Artist{
  type id       = Int
  type Name     = String
  type Albums   = List[Int]
  type Songs    = List[Int]

  val db: String = "DataBases/db_artists"
  val loaded: ListBuffer[Artist] = new ListBuffer[Artist]

  def load(line: String): Unit={
    val info=line.split(";").toList
    val artist:Artist = Artist(info(0),info(1),info(2),info(3))
    loaded+= artist
    println("Loaded " + line)

  }

  def info(a:Artist): Option[(Int,String,List[Int],List[Int])] ={
    Option(
      a.id,
      a.name,
      a.albums,
      a.songs
    )
  }

//------------------------ Load APPLY
  def apply(id: String,name: String, albums: String, songs: String): Artist = {
    val albumsList=albums.split(" ").toList
    val songsList=songs.split(" ").toList
    if(albumsList(0)=="" && songsList(0)==""){
      Artist(id.toInt,name,List(),List())
    }
    else if(albumsList(0)==""){
      Artist(id.toInt,name,List(),songsList.map(_.toInt))
    }
    else if(songsList(0)==""){
      Artist(id.toInt,name,albumsList.map(_.toInt),List())
    }
    else{
      Artist(id.toInt,name,albumsList.map(_.toInt),songsList.map(_.toInt))
    }

  }


//----------------

  def addSong(a: Data.Artist, song: Int): Artist ={
    Artist(a.id,a.name,a.albums, song::a.songs)
  }

  def addAlbum(a: Data.Artist, album:Int): Artist ={
    Artist(a.id,a.name,album::a.albums, a.songs)
  }

  def toString(a: Data.Artist): String={
    a.id+";"+a.name+";"+a.albums.mkString(" ")+";"+a.songs.mkString(" ")+";end;"
  }


}
