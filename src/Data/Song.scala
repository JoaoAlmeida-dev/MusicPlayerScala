package Data

case class Song (id: Int, name:String,filepath:String,  duration:Int, artist:Int, genre:String, album:Int, feats: List[Int], listened:Int, trackN:Int){

  def info(): Option[(Int,String,String,Int,Int,String,Int,List[Int],Int,Int)] ={ Song.info(this) }

  override def toString(): String ={ Song.toString(this) }

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
  type TrackN   = Int


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
