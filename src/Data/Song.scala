package Data

case class Song ( filepath:String, name:String, duration:Double, artist:Artist, genre:String, album:Album, feats: List[Artist], listened:Int){



}

object Song{

  type Filepath   = String
  type Name       = String
  type Duration   = Double
  type Artist     = Artist
  type Genre      = String
  type Album      = Album
  type Feats      = List[Artist]
  type listened   =Int

}
