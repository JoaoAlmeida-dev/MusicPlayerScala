package Data

case class Artist(name:String, albums: List[Album], songs:List[Song] ){



}

object Artist{

  type Nome     = String
  type Albums   = List[Album]
  type Songs    = List[Song]

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

  def info(a:Artist): Option[(String,List[Albums],List[Song])] ={
    Option(
      a.name,
      a.albums,
      a.songs
    )
  }

}
