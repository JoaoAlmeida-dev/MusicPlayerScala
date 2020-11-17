package Data

case class Album(id: Int,name:String, tracks:List[String], artist:String){
  def info():Option[(Int,String,List[String],String)]= { Album.info(this) }
  def setArtist(artist: String): Album= { Album.setArtist(this,artist) }
  def addSong(song: String): Album= { Album.addSong(this,song) }
  override def toString(): String ={ Album.toString(this) }
}

object Album {
  type id = Int
  type Name = String
  type Tracks = List[String]
  type Artist = String

  def info(a: Album): Option[(Int,String, List[String], String)] = {
    Option(
      a.id,
      a.name,
      a.tracks,
      a.artist
    )
  }
//------------------- LOAD APPLY
  def apply(id: Int,name: String, tracks: String, artist: String): Album = {
    Album(id,name, tracks.split(" ").toList, artist)
  }
//------------------

  def setArtist(a: Album, artist: String): Album = {
    Album(a.id,a.name, a.tracks, artist)
  }

  def addSong(a: Album, song: String): Album = {
    Album(a.id,a.name, song :: a.tracks, a.artist)
  }

  def toString(a: Album): String={
    a.id+";"+a.name+";"+a.tracks.mkString(" ")+";"+a.artist+";end;"
  }

}