package Data

import scala.collection.mutable.ListBuffer

trait MusicObject[A]{

  val id:Int
  val db:String
  val constructN:Int
  var loaded:ListBuffer[A]

  def toString:String
  def load(line: String):Unit

  def apply(args:List[String]):A

  def getLoaded[A]():List[String] = {
    this.loaded.toList.map(_.toString.split(";").drop(1).dropRight(1).toString)
  }
}
/*
case object ALBUM     extends Objects[Album]   ("Album","DataBases/db_albums",new ListBuffer[Album])
case object ARTIST    extends Objects[Artist]  ("Artist","DataBases/db_artists",new ListBuffer[Artist])
case object PLAYLIST  extends Objects[Playlist]("Playlist","DataBases/db_playlists",new ListBuffer[Playlist])
case object SONG      extends Objects[Song]    ("Song","DataBases/db_songs",new ListBuffer[Song])
*/