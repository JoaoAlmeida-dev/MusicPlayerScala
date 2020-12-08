package Data
import scala.collection.mutable.ListBuffer
import javafx.collections.{FXCollections, ModifiableObservableListBase, ObservableList, ObservableListBase}


case class Album(id: Int,name:String, tracks:List[Int], artist:Int) extends MusicObject[Album]{
  def info():Option[(Int,String,List[Int],Int)]= { Album.info(this) }
  def setArtist(artist: Int): Album= { Album.setArtist(this,artist) }
  def addSong(song: Int): Album= { Album.addSong(this,song) }

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