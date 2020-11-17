package Data

case class Playlist(id:Int,name:String, songs:List[String], theme:String) {
  def info(): Option[(Int,String,List[String],String)] ={ Playlist.info(this) }
  def addSong(song: String): Playlist={ Playlist.addSong(this,song) }
  def removeSong(song: String): Playlist={ Playlist.addSong(this,song) }
  override def toString(): String ={ Playlist.toString(this) }
}

object Playlist{

  type id     = Int
  type Name   = String
  type Songs  = List[String]
  type Theme  = String

  def info(p:Playlist): Option[(Int,String,List[String],String)] ={
    Option(
      p.id,
      p.name,
      p.songs,
      p.theme
    )
  }
//--------------------------- LOAD APPLY
  def apply(id: Int,name:String,songs:String, theme: String):Playlist={
    Playlist(id,name,songs.split(" ").toList,theme)
  }
//------------------------------------------

  def addSong(p: Playlist, song: String): Playlist={
    Playlist(p.id,p.name,song::p.songs,p.theme)
  }

  def removeSong(p: Playlist, song: String): Playlist={
    val songs=p.songs.filter( _ != song)
    Playlist(p.id,p.name,songs,p.theme)
  }

  def toString(p: Playlist): String={
    p.id+";"+p.name+";"+p.songs.mkString(" ")+";"+p.theme+";end;"
  }
  //Falta Remover
}
