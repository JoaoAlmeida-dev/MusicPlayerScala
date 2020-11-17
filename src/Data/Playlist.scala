package Data

case class Playlist(name:String, songs:List[String], theme:String) {
  def info(): Option[(String,List[String],String)] ={ Playlist.info(this) }
  def addSong(song: String): Playlist={ Playlist.addSong(this,song) }
  def removeSong(song: String): Playlist={ Playlist.addSong(this,song) }
  override def toString(): String ={ Playlist.toString(this) }
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
//--------------------------- LOAD APPLY
  def apply(name:String,songs:String, theme: String):Playlist={
    Playlist(name,songs.split(" ").toList,theme)
  }
//------------------------------------------

  def addSong(p: Playlist, song: String): Playlist={
    Playlist(p.name,song::p.songs,p.theme)
  }

  def removeSong(p: Playlist, song: String): Playlist={
    val songs=p.songs.filter( _ != song)
    Playlist(p.name,songs,p.theme)
  }

  def toString(p: Playlist): String={
    p.name+";"+p.songs.mkString(" ")+";"+p.theme+";end;"
  }
  //Falta Remover
}
