package Data

import Data.Artist.addAlbum

case class Artist(name:String, albums: List[String], songs:List[String] ){
  def info():Option[(String,List[String],List[String])]={ Artist.info(this) }
  def addSong(song: String): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: String): Artist={ Artist.addAlbum(this, album) }
  override def toString(): String ={ Artist.toString(this) }
}

object Artist{

  type Name     = String
  type Albums   = List[String]
  type Songs    = List[String]

  def info(a:Artist): Option[(String,List[String],List[String])] ={
    Option(
      a.name,
      a.albums,
      a.songs
    )
  }

//------------------------ Load APPLY
  def apply(name: String, albums: String, songs: String): Artist = {
    Artist(name, albums.split(" ").toList, songs.split(" ").toList)
  }
//----------------

  def addSong(a: Data.Artist, song: String): Artist ={
    Artist(a.name,a.albums, song::a.songs)
  }

  def addAlbum(a: Data.Artist, album:String): Artist ={
    Artist(a.name,album::a.albums, a.songs)
  }

  def toString(a: Data.Artist): String={
    a.name+";"+a.albums.mkString(" ")+";"+a.songs.mkString(" ")+";end;"
  }


}
