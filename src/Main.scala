import Data._

import scala.io.StdIn.readLine


object Main {
    def  main (args: Array[String]): Unit = {
        var album:Album = Album("album1")
        var artist:Artist = Artist("artist1",album)
        var s:Song = Song("path","songname",100,artist,"genre",album,Nil,10)
        println(s.info())

      //  mainLoop()
    }

    def showPropt(): Unit ={
        print()
    }

    def getUserInput(): String ={
        readLine.trim.toUpperCase
    }

/*    @tailrec
    def mainLoop(): Unit ={
        showPropt()
        val userInput:String = getUserInput()
        userInput match{
            case

        }
    }*/
}
