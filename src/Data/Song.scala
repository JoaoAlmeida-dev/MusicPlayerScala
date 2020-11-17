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
  def apply(id:Int, name:String, filepath:String, duration:Int, artist:Int, genre:String, album:Int, feats: String, listened:Int,trackN: Int): Song = {
    val featsList=feats.split(" ").toList
    if(featsList(0)==""){
      Song(id,name, filepath, duration, artist, genre, album, List(), listened, trackN)
    } else{
      Song(id,name, filepath, duration, artist, genre, album, featsList.map(_.toInt), listened, trackN)
    }
  }
//----------
  def toString(s:Song ): String ={
     s.id + ";" + s.name + ";"+ s.filepath + ";" + s.duration + ";" + s.artist + ";" + s.genre + ";" + s.album + ";" + s.feats.mkString(" ") + ";" + s.listened +";"+s.trackN+";end;"
  }


}
