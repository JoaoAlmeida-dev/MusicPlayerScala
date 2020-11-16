package Data

import Data.Artist.addAlbum

case class Artist(name:String, albums: List[String], songs:List[String] ){
  def info():Option[(String,List[String],List[String])]={ Artist.info(this) }
  def addSong(song: String): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: String): Artist={ Artist.addAlbum(this, album) }
}

object Artist{

  type Nome     = String
  type Albums   = List[String]
  type Songs    = List[String]

  def info(a:Artist): Option[(String,List[String],List[String])] ={
    Option(
      a.name,
      a.albums,
      a.songs
    )
  }
  def apply(): Artist ={
    Artist("", Nil, Nil)
  }

  def apply(name:String): Artist ={
    Artist(name, Nil, Nil)
  }

  def apply(name:String,albums: List[String]): Artist ={
    Artist(name, albums, Nil)
  }

  def apply(name:String,album: String): Artist ={
    Artist(name, List(album), Nil)
  }

  def apply(name:String,album: String, songs:List[String]): Artist ={
    Artist(name, List(album), songs)
  }

  def addSong(a: Data.Artist, song: String): Artist ={
    Artist(a.name,a.albums, song::a.songs)
  }

  def addAlbum(a: Data.Artist, album:String): Artist ={
    Artist(a.name,album::a.albums, a.songs)
  }

}
