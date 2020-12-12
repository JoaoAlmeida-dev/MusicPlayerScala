package Data

import javafx.collections.{FXCollections, ObservableList}

import scala.::

case class Artist(id:Int,name:String, albums: List[Int], songs:List[Int] ) extends MusicObject[Artist]{
  def info():Option[(Int,String,List[Int],List[Int])]={ Artist.info(this) }
  def addSong(song: Int): Artist={ Artist.addSong(this, song) }
  def addAlbum(album: Int): Artist={ Artist.addAlbum(this, album) }
  def getSongs():List[Song] = {Artist.getSongs(this)}
  def getAlbums():List[Album] = {Artist.getAlbums(this)}
  def remAlbum(album:Int):Unit={Artist.remAlbum(this,album)}
  def remSong(song:Int):Unit={Artist.remSong(this,song)}
  override def delete(): Unit = {Artist.delete(this)}


  override def toString(): String ={ Artist.toString(this) }
  override val db: String =Artist.db
  override var loaded: ObservableList[Artist] = Artist.loaded
  override def load(line: String): Unit = Artist.load(line)
  override val constructN: Int = 4

  override def apply(info:List[String]):Artist=Artist.apply(info)
  override def getLoaded[Artist](): List[String] = Artist.getLoaded[Artist]()

}

object Artist{
  type id       = Int
  type Name     = String
  type Albums   = List[Int]
  type Songs    = List[Int]

  val db: String = "DataBases/db_artists"
  val loaded: ObservableList[Artist] =  FXCollections.observableArrayList[Artist]()
  def getLoaded[Artist](): List[String] = {
    this.loaded.toArray().toList.map(_.toString.split(";").toList.drop(1).dropRight(1).mkString(";"))
  }

  def load(line: String): Unit={
    val info=line.split(";").toList
    //val artist:Artist = Artist(info(0),info(1),info(2),info(3))
    val artist:Artist = Artist(info)
    loaded.add( artist)
    //println("Loaded " + line)

  }

  def info(a:Artist): Option[(Int,String,List[Int],List[Int])] ={
    Option(
      a.id,
      a.name,
      a.albums,
      a.songs
    )
  }

//------------------------ Load APPLY
  //def apply(id: String,name: String, albums: String, songs: String): Artist = {
  def apply(info:List[String]): Artist = {
    val albumsList=info(2).split(" ").toList
    val songsList=info(3).split(" ").toList

    if(albumsList(0)=="" && songsList(0)==""){
      Artist(info(0).toInt,info(1),List(),List())
    }
    else if(albumsList(0)==""){
      Artist(info(0).toInt,info(1),List(),songsList.map(_.toInt))
    }
    else if(songsList(0)==""){
      Artist(info(0).toInt,info(1),albumsList.map(_.toInt),List())
    }
    else{
      Artist(info(0).toInt,info(1),albumsList.map(_.toInt),songsList.map(_.toInt))
    }

  }


//----------------

  def addSong(a: Data.Artist, song: Int): Artist ={
    if(!a.songs.contains(song)){
      DatabaseFunc.update[Artist](a,3,(a.songs.appended(song)).mkString(" "))
    }else{
      a
    }
  }

  def addAlbum(a: Data.Artist, album:Int): Artist = {
    if(!a.albums.contains(album)){
      DatabaseFunc.update[Artist](a, 2, (a.albums.appended(album)).mkString(" "))
    }else{
      a
    }
      //Artist(a.id,a.name,album::a.albums, a.songs)
  }
   def remAlbum(a: Data.Artist, album:Int): Unit ={
    if(a.albums.filter(x=>x==album).isEmpty){
      DatabaseFunc.update[Artist](a,2,(a.albums.filter(_!=album)).mkString(" "))
      //Artist(a.id,a.name,album::a.albums, a.songs)
    }
   }
   def remSong(a: Data.Artist, song:Int): Unit ={
    if(a.songs.filter(x=>x==song).isEmpty){
      DatabaseFunc.update[Artist](a,3,(a.albums.filter(_!=song)).mkString(" "))
      //Artist(a.id,a.name,album::a.albums, a.songs)
     }
   }


  def delete(a:Artist){
    loaded.remove(a)
    val albumsFiltered:List[Album] = DatabaseFunc.observableListToList(Album.loaded).filter(x=>x.artist == a.id)
    DatabaseFunc.observableListToList(Playlist.loaded).map(x=>x.removeSongsFromArtist(a.id))
    albumsFiltered.map(x=>x.delete)
  }

  def getSongs(a:Artist): List[Data.Song] ={
    DatabaseFunc.observableListToList(Song.loaded).filter(x=>a.songs.contains(x.id))
  }
  def getAlbums(a:Artist): List[Data.Album] ={
    DatabaseFunc.observableListToList(Album.loaded).filter(x=>a.albums.contains(x.id))
  }

  def toString(a: Data.Artist): String={
    a.id+";"+a.name+";"+a.albums.mkString(" ")+";"+a.songs.mkString(" ")+";end;"
  }


}
