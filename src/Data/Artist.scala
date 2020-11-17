package Data

import Data.Artist.addAlbum

case class Artist(id:Int,name:String, albums: List[Int], songs:List[Int] ){
  def info():Option[(Int,String,List[Int],List[Int])]={ Artist.info(this) }
  def addSong(song: Int): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: Int): Artist={ Artist.addAlbum(this, album) }
  override def toString(): String ={ Artist.toString(this) }
}

object Artist{
  type id       = Int
  type Name     = String
  type Albums   = List[Int]
  type Songs    = List[Int]

  def info(a:Artist): Option[(Int,String,List[Int],List[Int])] ={
    Option(
      a.id,
      a.name,
      a.albums,
      a.songs
    )
  }

//------------------------ Load APPLY
  def apply(id: Int,name: String, albums: String, songs: String): Artist = {
    val albumsList=albums.split(" ").toList
    val songsList=songs.split(" ").toList
    if(albumsList(0)=="" && songsList(0)==""){
      Artist(id,name,List(),List())
    }
    else if(albumsList(0)==""){
      Artist(id,name,List(),songsList.map(_.toInt))
    }
    else if(songsList(0)==""){
      Artist(id,name,albumsList.map(_.toInt),List())
    }
    else{
      Artist(id,name,albumsList.map(_.toInt),songsList.map(_.toInt))
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
