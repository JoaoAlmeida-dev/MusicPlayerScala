package Data

case class Album(name:String, tracks:List[Song], artist:Data.Artist){
  def info():Option[(String,List[Song],Artist)]= { Album.info(this) }

}

object Album{

  type Name     = String
  type Tracks   = List[Song]
  type Artist   = Data.Artist

  def info(a:Album): Option[(String,List[Song],Artist)] ={
    Option(
      a.name,
      a.tracks,
      a.artist
    )
  }

}