package Data

import Data.Artist.addAlbum

case class Artist(id:Int,name:String, albums: List[String], songs:List[String] ){
  def info():Option[(Int,String,List[String],List[String])]={ Artist.info(this) }
  def addSong(song: String): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: String): Artist={ Artist.addAlbum(this, album) }
  override def toString(): String ={ Artist.toString(this) }
}

object Artist{
  type id       = Int
  type Name     = String
  type Albums   = List[String]
  type Songs    = List[String]

  def info(a:Artist): Option[(Int,String,List[String],List[String])] ={
    Option(
      a.id,
      a.name,
      a.albums,
      a.songs
    )
  }

//------------------------ Load APPLY
  def apply(id: Int,name: String, albums: String, songs: String): Artist = {
    Artist(id,name, albums.split(" ").toList, songs.split(" ").toList)
  }
//----------------

  def addSong(a: Data.Artist, song: String): Artist ={
    Artist(a.id,a.name,a.albums, song::a.songs)
  }

  def addAlbum(a: Data.Artist, album:String): Artist ={
    Artist(a.id,a.name,album::a.albums, a.songs)
  }

  def toString(a: Data.Artist): String={
    a.id+";"+a.name+";"+a.albums.mkString(" ")+";"+a.songs.mkString(" ")+";end;"
  }


}
