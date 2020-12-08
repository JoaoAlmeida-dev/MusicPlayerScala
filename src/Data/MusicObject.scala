package Data

import javafx.collections.ObservableList

import scala.collection.mutable.ListBuffer

trait MusicObject[A]{

  val id:Int
  val db:String
  val constructN:Int
  var loaded:ObservableList[A]

  def toString:String
  def load(line: String):Unit

  def apply(args:List[String]):A

  def getLoaded[A]():List[String]
}
