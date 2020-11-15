package Data

case class Artist(name:String, albums: List[Album], songs:List[Song] ){
  def info():Option[(String,List[Album],List[Song])]={ Artist.info(this) }

}

object Artist{

  type Nome     = String
  type Albums   = List[Album]
  type Songs    = List[Song]

  def info(a:Artist): Option[(String,List[Album],List[Song])] ={
    Option(
      a.name,
      a.albums,
      a.songs
    )
  }

  def apply(name:String): Artist ={
    Artist(name, Nil, Nil)
  }

  def apply(name:String,albums: List[Album]): Artist ={
    Artist(name, albums, Nil)
  }

  def apply(name:String,album: Album): Artist ={
    Artist(name, List(album), Nil)
  }

  def apply(name:String,album: Album, songs:List[Song]): Artist ={
    Artist(name, List(album), songs)
  }



}
