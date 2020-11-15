package Data

case class Song ( filepath:String, name:String, duration:Double, artist:Artist, genre:String, album:Album, feats: List[Artist], listened:Int){
  def info(): Option[(String,String,Double,Data.Artist,String,Data.Album,List[Data.Artist],Int)] ={
    Song.info(this)
  }

}
object Song{

  type Filepath   = String
  type Name       = String
  type Duration   = Double
  type Artist     = Data.Artist
  type Genre      = String
  type Album      = Data.Album
  type Feats      = List[Data.Artist]
  type Listened   = Int

  def info(s:Song): Option[(String,String,Double,Data.Artist,String,Data.Album,List[Data.Artist],Int)] ={
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


}
