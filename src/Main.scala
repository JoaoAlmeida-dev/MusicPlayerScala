import Data._

import scala.io.StdIn.readLine


object Main {
  def  main (args: Array[String]): Unit = {
    var album:Album = Album("album1")
    var artist:Artist = Artist("artist1","album1")
    var s:Song = Song("path","songname",100,"artist1","genre","album1",Nil,10)
    println(s.info())
    println(s.toString())

    //  mainLoop()
  }

  def showPropt(): Unit ={
      print()
  }

  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }

  /*
  Lista de funções
      WRITE TO FILESSSS
      delete from files
      read from files

      sacar info da musica a partir do filepath(1ª criação da musica / importar musica para o sistema)

  */

  /*
  @tailrec
  def mainLoop(): Unit ={
    showPropt()
    val userInput:String = getUserInput()
    userInput match{


      }
  }
  */
}
