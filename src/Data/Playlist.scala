package Data

case class Playlist(name:String, songs:List[String], theme:String) {
  def info(): Option[(String,List[String],String)] ={ Playlist.info(this) }
  def addSong(song: String): Playlist={ Playlist.addSong(this,song) }
}

object Playlist{

  type Name   = String
  type Songs  = List[String]
  type Theme  = String

  def info(p:Playlist): Option[(String,List[String],String)] ={
    Option(
      p.name,
      p.songs,
      p.theme
    )
  }

  def apply(name:String,songs:List[String], theme: String):Playlist={
    Playlist(name,songs,theme)
  }
  def apply(name:String):Playlist={
    Playlist(name,List(),"")
  }

  def addSong(p: Playlist, song: String): Playlist={
    Playlist(p.name,song::p.songs,p.theme)
  }
  //Falta Remover
}
