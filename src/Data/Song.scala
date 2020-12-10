package Data

import javafx.collections.{FXCollections, ObservableList, ObservableListBase}
import javafx.scene.image.Image

import scala.collection.mutable.ListBuffer

case class Song (id: Int, name:String,filepath:String, artist:Int, genre:String, album:Int, feats: List[Int], listened:Int, trackN:Int) extends MusicObject[Song] {

  def info(): Option[(Int,String,String,Int,String,Int,List[Int],Int,Int)] ={ Song.info(this) }

  override def toString(): String ={ Song.toString(this) }
  override val db: String = Song.db
  override var loaded: ObservableList[Song] = Song.loaded
  override def load(line: String): Unit = Song.load(line)
  override val constructN: Int = 10

  override def apply(info:List[String]):Song=Song.apply(info)

  override def getLoaded[Song](): List[String] = Song.getLoaded()

}
/*
tiramos o duration
 */


object Song{
  type id         = Int
  type Name       = String
  type Filepath   = String
  type Artist     = Int
  type Genre      = String
  type Album      = Int
  type Feats      = List[Int]
  type Listened   = Int
  type TrackN     = Int


  val db: String = "DataBases/db_songs"
  var loaded: ObservableList[Song] = FXCollections.observableArrayList[Song]()


  def getLoaded[Song](): List[String] ={
    this.loaded.toArray().toList.map(_.toString.split(";").toList.drop(1).dropRight(1).mkString(";"))
  }

  def load(line: String): Unit={
    val info=line.split(";").toList
    //val song:Song = Song(info(0),info(1),info(2),info(3),info(4),info(5),info(6),info(7),info(8),info(9))
    val song:Song = Song(info)
    loaded.add( song)
    println("Loaded " + line)
  }

  def info(s:Song): Option[(Int,String,String,Int,String,Int,List[Int],Int,Int)] ={
    Option(
      s.id,
      s.name,
      s.filepath,
      s.artist,
      s.genre,
      s.album,
      s.feats,
      s.listened,
      s.trackN
    )
  }
//---------  LOAD APPLY


  def apply(info:List[String]): Song = {
    val featsList = info(6).split(" ").toList
    if (featsList(0) == "") {
      Song(info(0).toInt, info(1), info(2), info(3).toInt, info(4), info(5).toInt, List(), info(7).toInt, info(8).toInt)
    } else {
      Song(
        info(0).toInt,
        info(1), //id
        info(2), //name
        info(3).toInt, //filepath
        //info(4).toInt, //duration
        info(4), //artist
        info(5).toInt, //genre
        featsList.map(_.toInt), //feats
        info(7).toInt, //listened
        info(8).toInt) //trackN
    }
  }

//----------
  def toString(s:Song ): String ={
     s.id + ";" + s.name + ";"+ s.filepath + ";" + s.artist + ";" + s.genre + ";" + s.album + ";" + s.feats.mkString(" ") + ";" + s.listened +";"+s.trackN+";end;"
  }


}
