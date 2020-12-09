package Data
import javafx.collections.{FXCollections, ObservableList}

import scala.collection.mutable.ListBuffer

case class Playlist(id:Int,name:String, songs:List[Int], theme:String) extends MusicObject[Playlist] {
  def info(): Option[(Int,String,List[Int],String)] ={ Playlist.info(this) }
  def addSong(song: Int): Playlist={ Playlist.addSong(this,song) }
  def removeSong(song: Int): Playlist={ Playlist.removeSong(this,song) }

  override def toString(): String ={ Playlist.toString(this) }
  override val db: String = Playlist.db
  override var loaded: ObservableList[Playlist] = Playlist.loaded
  override def load(line: String): Unit = Playlist.load(line)
  override val constructN: Int = 4

  override def apply(info:List[String]):Playlist=Playlist.apply(info)
  override def getLoaded[Playlist](): List[String] = Playlist.getLoaded[Playlist]()
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
    val songsList=info(2).split(" ").toList
    if(songsList(0)==""){
      Playlist(info(0).toInt,info(1),List(),info(3))
    } else{
      Playlist(info(0).toInt,info(1),songsList.map(_.toInt),info(3))
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


}
