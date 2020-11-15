package Data;

case class Playlist(name:String,artist:Artist,songs:List[Song], theme:String) {


}

object Playlist{
  type Name   = String
  type Artist = Artist
  type Songs  = List[Song]
  type Theme  = String

  def apply(name:String,artist:Artist,songs:List[Song]){
    Playlist(name,artist,songs,"")
  }
  def apply(name:String,artist:Artist,songs:List[Song]){
    Playlist(name,artist,Nil,"")
  }


}
