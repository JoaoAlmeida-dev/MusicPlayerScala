package Data

case class Song ( name:String,filepath:String,  duration:Int, artist:String, genre:String, album:String, feats: List[String], listened:Int, trackN:Int){

  def info(): Option[(String,String,Int,String,String,String,List[String],Int)] ={ Song.info(this) }

  override def toString(): String ={ Song.toString(this) }

}
object Song{

  type Name       = String
  type Filepath   = String
  type Duration   = Int
  type Artist     = String
  type Genre      = String
  type Album      = String
  type Feats      = List[String]
  type Listened   = Int
  type TrackN   = Int


  def info(s:Song): Option[(String,String,Int,String,String,String,List[String],Int,Int)] ={
    Option(
      s.filepath,
      s.name,
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
  def apply(filepath:String, name:String, duration:Int, artist:String, genre:String, album:String, feats: String, listened:Int,trackN: Int): Song = {
    Song(name, filepath, duration, artist, genre, album, feats.split(" ").toList, listened, trackN)
  }
//----------
  def toString(s:Song ): String ={
     s.name + ";"+ s.filepath + ";" + s.duration + ";" + s.artist + ";" + s.genre + ";" + s.album + ";" + s.feats.mkString(" ") + ";" + s.listened +";"+s.trackN+";end;"
  }


}
