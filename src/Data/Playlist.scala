package Data
import javafx.collections.{FXCollections, ObservableList}

import scala.util.Random

case class Playlist(id:Int,name:String, songs:List[Int], theme:String) extends MusicObject[Playlist] {
  def info(): Option[(Int,String,List[Int],String)] ={ Playlist.info(this) }

  def addSong(songid: Int): Playlist={ Playlist.addSong(this,songid) }
  def addSong(songsid: List[Int]): Playlist={ Playlist.addSong(this,songsid) }

  def removeSong(songid: Int): Playlist={ Playlist.removeSong(this,songid) }
  def removeSong(songsid: List[Int]): Playlist={ Playlist.removeSong(this,songsid) }

  def getSongs():List[Song] = Playlist.getSongs(this)

  override def toString(): String ={ Playlist.toString(this) }
  override val db: String = Playlist.db
  override var loaded: ObservableList[Playlist] = Playlist.loaded
  override def load(line: String): Unit = Playlist.load(line)
  override val constructN: Int = 4

  override def apply(info:List[String]):Playlist=Playlist.apply(info)
  override def getLoaded[Playlist](): List[String] = Playlist.getLoaded[Playlist]()

  override def delete(): Unit = {Playlist.delete(this)}
}

object Playlist{

  type id     = Int
  type Name   = String
  type Songs  = List[Int]
  type Theme  = String

  val db: String = "DataBases/db_playlists"
  var loaded: ObservableList[Playlist] =FXCollections.observableArrayList[Playlist]()
  def getLoaded[Playlist](): List[String] = {
    this.loaded.toArray().toList.map(_.toString.split(";").toList.drop(1).dropRight(1).mkString(";"))
  }

  def load(line: String): Unit={
    val info=line.split(";").toList
    //val playlist:Playlist =Playlist(info(0),info(1),info(2),info(3))
    val playlist:Playlist =Playlist(info)
    loaded.add(playlist)
    println("Loaded " + line)
  }

  def info(p:Playlist): Option[(Int,String,List[Int],String)] ={
    Option(
      p.id,
      p.name,
      p.songs,
      p.theme
    )
  }

//--------------------------- LOAD APPLY
  //def apply(id: String,name:String,songs:String, theme: String):Playlist={
  def apply(info:List[String]):Playlist={
    val songsList=info(2).trim.split(" ").toList
    if(songsList(0)==""){
      Playlist(info(0).toInt,info(1),List(),info(3))
    } else{
      Playlist(info(0).toInt,info(1),songsList.map(_.toInt),info(3))
    }
  }
//------------------------------------------
//TODO usar Database.func update
  def addSong(p: Playlist, songid: Int): Playlist={
    DatabaseFunc.update[Playlist](p,2,p.songs.mkString(" ")+" "+songid)
    //Playlist(p.id,p.name,song::p.songs,p.theme)
  }

  def addSong(p: Playlist, songsid: List[Int]): Playlist={

    def recursiveAdd(playlist: Playlist,id:List[Int],songsold:List[Int]): Playlist =id match{
      case h::t =>
        val pl:Playlist = if(songsold.contains(h)){
          playlist
        }else{
          playlist.addSong(h)
        }
        recursiveAdd (pl,t,songsold)
      case Nil => playlist
    }
    recursiveAdd(p,songsid,p.songs)
    //Playlist(p.id,p.name,song::p.songs,p.theme)
  }

  def removeSong(p: Playlist, songid: Int): Playlist={
    val songs=p.songs.filter( _ != songid)
    DatabaseFunc.update[Playlist](p,2,songs.mkString(" "))
  }
  def removeSong(p: Playlist, songsid: List[Int]): Playlist={
    val songs=p.songs.filter( !songsid.contains(_))
    DatabaseFunc.update[Playlist](p,2,songs.mkString(" "))
  }

  def delete(playlist: Playlist): Unit = {loaded.remove(playlist)}

  def getSongs(playlist: Playlist):List[Song]={
    DatabaseFunc.observableListToList(Song.loaded).filter(x=>playlist.songs.contains(x.id))
  }

  def toString(p: Playlist): String={
    p.id+";"+p.name+";"+p.songs.mkString(" ")+";"+p.theme+";end;"
  }

  def shuffle(playlist: Playlist) = {Random.shuffle(playlist.songs)}

  def addFromFilter(f: Function[Song, Boolean], playlist: Playlist): Unit = {
    var lst:List[Song] = DatabaseFunc.observableListToList((Song.loaded)).filter(x => f(x))
    lst.foreach(x => addSong(playlist, x.id))
  }



}
