package Data

case class Song ( filepath:String, name:String, duration:Int, artist:String, genre:String, album:String, feats: List[String], listened:Int){

  def info(): Option[(String,String,Int,String,String,String,List[String],Int)] ={ Song.info(this) }

  override def toString(): String ={ Song.toString(this) }

}
object Song{

  type Filepath   = String
  type Name       = String
  type Duration   = Int
  type Artist     = String
  type Genre      = String
  type Album      = String
  type Feats      = List[String]
  type Listened   = Int


  def info(s:Song): Option[(String,String,Int,String,String,String,List[String],Int)] ={
    Option(
      s.filepath,
      s.name,
      s.duration,
      s.artist,
      s.genre,
      s.album,
      s.feats,
      s.listened
    )
  }
//---------  LOAD APPLY
  def apply(filepath:String, name:String, duration:Int, artist:String, genre:String, album:String, feats: String, listened:Int): Song = {
    Song(filepath,name,duration,artist,genre,album,feats.split(" ").toList,listened)
  }
//----------
  def toString(s:Song ): String ={
    s.filepath + ";" + s.name + ";" + s.duration + ";" + s.artist + ";" + s.genre + ";" + s.album + ";" + s.feats.mkString(" ") + ";" + s.listened +";end;"
  }


}
