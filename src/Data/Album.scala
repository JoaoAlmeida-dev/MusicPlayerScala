package Data
import scala.collection.mutable.ListBuffer

case class Album(id: Int,name:String, tracks:List[Int], artist:Int) extends Object[Album]{
  def info():Option[(Int,String,List[Int],Int)]= { Album.info(this) }
  def setArtist(artist: Int): Album= { Album.setArtist(this,artist) }
  def addSong(song: Int): Album= { Album.addSong(this,song) }

  override def toString(): String ={ Album.toString(this) }
  override val db: String = Album.db
  override var loaded: ListBuffer[Album] = Album.loaded
  override def load(line: String): Unit = Album.load(line)

  override val constructN: Int = 4
}

object Album {
  type id = Int
  type Name = String
  type Tracks = List[Int]
  type Artist = Int

  val db: String = "DataBases/db_albums"
  var loaded: ListBuffer[Album] = new ListBuffer[Album]

  def load(line: String): Unit={
    val info=line.split(";").toList
    val album:Album = Album(info(0),info(1),info(2),info(3))
    loaded+=album
    println("Loaded " + line)
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