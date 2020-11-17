package Data

case class Playlist(id:Int,name:String, songs:List[Int], theme:String) {
  def info(): Option[(Int,String,List[Int],String)] ={ Playlist.info(this) }
  def addSong(song: Int): Playlist={ Playlist.addSong(this,song) }
  def removeSong(song: Int): Playlist={ Playlist.addSong(this,song) }
  override def toString(): String ={ Playlist.toString(this) }
}

object Playlist{

  type id     = Int
  type Name   = String
  type Songs  = List[int]
  type Theme  = String

  def info(p:Playlist): Option[(Int,String,List[Int],String)] ={
    Option(
      p.id,
      p.name,
      p.songs,
      p.theme
    )
  }
//--------------------------- LOAD APPLY
  def apply(id: Int,name:String,songs:String, theme: String):Playlist={
    val songslist = songs.split(" ").toList
    if(songslist(0)==""){
      Playlist(id, name,List() , theme)
    }else{
      Playlist(id, name, songslist.toList.map(_.toInt), theme)
    }
  }
//------------------------------------------

  def addSong(p: Playlist, song: Int): Playlist={
    Playlist(p.id,p.name,song::p.songs,p.theme)
  }

  def removeSong(p: Playlist, song: Int): Playlist={
    val songs=p.songs.filter( _ != song)
    Playlist(p.id,p.name,songs,p.theme)
  }

  def toString(p: Playlist): String={
    p.id+";"+p.name+";"+p.songs.mkString(" ")+";"+p.theme+";end;"
  }
  //Falta Remover
}
