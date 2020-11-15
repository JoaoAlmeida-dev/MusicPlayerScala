import Data._


object Main {
    def  main (args: Array[String]): Unit = {
        var album:Album = Album("album1")
        var artist:Artist = Artist("artist1",album)
        var s:Song = Song("path","songname",100,artist,"genre",album,Nil,10)
        println(s.info())
    }

    def showPropt(): Unit ={
        print()
    }

    def getUserInput(): Unit ={


    }
}
