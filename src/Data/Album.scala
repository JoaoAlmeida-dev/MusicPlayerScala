package Data

case class Album(name:String, tracks:List[String], artist:String){
  def info():Option[(String,List[String],String)]= { Album.info(this) }
  def setArtist(artist: String): Album= { Album.setArtist(this,artist) }
  def addSong(song: String): Album= { Album.addSong(this,song) }
}

object Album{

  type Name     = String
  type Tracks   = List[String]
  type Artist   = String

  def info(a:Album): Option[(String,List[String],String)] ={
    Option(
      a.name,
      a.tracks,
      a.artist
    )
  }

  def apply(name:String, song:String): Album ={
    Album(name, List(song), "")
  }
  def apply(name:String, song:String, artist:String): Album ={
    Album(name, List(song), artist)
  }
  def apply(name:String): Album ={
    Album(name, Nil, "")
  }

  def setArtist(a:Album, artist: String): Album ={
    Album(a.name,a.tracks,artist)
  }

  def addSong(a: Album, song: String): Album ={
    Album(a.name,song::a.tracks, a.artist)
  }
}