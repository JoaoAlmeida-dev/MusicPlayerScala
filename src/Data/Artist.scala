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

}
