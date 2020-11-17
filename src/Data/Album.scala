package Data

case class Album(id: Int,name:String, tracks:List[Int], artist:Int){
  def info():Option[(Int,String,List[Int],Int)]= { Album.info(this) }
  def setArtist(artist: Int): Album= { Album.setArtist(this,artist) }
  def addSong(song: Int): Album= { Album.addSong(this,song) }
  override def toString(): String ={ Album.toString(this) }
}

object Album {
  type id = Int
  type Name = String
  type Tracks = List[Int]
  type Artist = Int

  def info(a: Album): Option[(Int,String, List[Int], Int)] = {
    Option(
      a.id,
      a.name,
      a.tracks,
      a.artist
    )
  }
//------------------- LOAD APPLY
  def apply(id: String,name: String, tracks: String, artist:String): Album = {
    val tracksList= tracks.split(" ").toList
    if(tracksList(0)==""){
      Album(id.toInt,name,List(), artist.toInt)
    } else{
      Album(id.toInt,name,tracksList.map(_.toInt), artist.toInt)
    }
  }
//------------------

  def setArtist(a: Album, artist: Int): Album = {
    Album(a.id,a.name, a.tracks, artist)
  }

  def addSong(a: Album, song: Int): Album = {
    Album(a.id,a.name, song :: a.tracks, a.artist)
  }

  def toString(a: Album): String={
    a.id+";"+a.name+";"+a.tracks.mkString(" ")+";"+a.artist+";end;"
  }

}