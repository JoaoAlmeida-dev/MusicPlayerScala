package Data

import scala.collection.mutable.ListBuffer

case class Song (id: Int, name:String,filepath:String,  duration:Int, artist:Int, genre:String, album:Int, feats: List[Int], listened:Int, trackN:Int) extends Object[Song] {

  def info(): Option[(Int,String,String,Int,Int,String,Int,List[Int],Int,Int)] ={ Song.info(this) }

  override def toString(): String ={ Song.toString(this) }
  override val db: String = Song.db
  override var loaded: ListBuffer[Song] = Song.loaded
  override def load(line: String): Unit = Song.load(line)

  override val constructN: Int = 10
}
object Song{
  type id         = Int
  type Name       = String
  type Filepath   = String
  type Duration   = Int
  type Artist     = Int
  type Genre      = String
  type Album      = Int
  type Feats      = List[Int]
  type Listened   = Int
  type TrackN     = Int


  val db: String = "DataBases/db_songs"
  var loaded: ListBuffer[Song] = new ListBuffer[Song]()

  def load(line: String): Unit={
    val info=line.split(";").toList
    val song:Song = Song(info(0),info(1),info(2),info(3),info(4),info(5),info(6),info(7),info(8),info(9))
    loaded+= song
    println("Loaded " + line)
  }

  def info(s:Song): Option[(Int,String,String,Int,Int,String,Int,List[Int],Int,Int)] ={
    Option(
      s.id,
      s.name,
      s.filepath,
      s.duration,
      s.artist,
      s.genre,
      s.album,
      s.feats,
      s.listened,
      s.trackN
    )
  }
//---------  LOAD APPLY
  def apply(id:String, name:String, filepath:String, duration:String, artist:String, genre:String, album:String, feats: String, listened:String,trackN: String): Song = {
    val featsList=feats.split(" ").toList
    if(featsList(0)==""){
      Song(id.toInt,name, filepath, duration.toInt, artist.toInt, genre, album.toInt, List(), listened.toInt, trackN.toInt)
    } else{
      Song(id.toInt,name, filepath, duration.toInt, artist.toInt, genre, album.toInt, featsList.map(_.toInt), listened.toInt, trackN.toInt)
    }
  }
//----------
  def toString(s:Song ): String ={
     s.id + ";" + s.name + ";"+ s.filepath + ";" + s.duration + ";" + s.artist + ";" + s.genre + ";" + s.album + ";" + s.feats.mkString(" ") + ";" + s.listened +";"+s.trackN+";end;"
  }


}
