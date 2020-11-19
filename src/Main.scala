
object Main {

  def  main (args: Array[String]): Unit = {

    DatabaseFunc.loadfiles()
    //printLoaded()
    println()
    println(DatabaseFunc.loadedAlbums(0))
    val artist1 = DatabaseFunc.loadedArtists(0)
    DatabaseFunc.update(artist1,2,"1")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)
    DatabaseFunc.update(artist1,2,"1 2 3 4")
    DatabaseFunc.addtoDB(DatabaseFunc.loadedArtists(0),DatabaseFunc.db_artists)
    //println(getSongfromBD(Datatype.SONG,"song1"))
  }



  /*
  Lista de funções
      sacar info da musica a partir do filepath(1ª criação da musica / importar musica para o sistema)

  */

  /*
  def showPropt(): Unit ={
      print()
  }
  def getUserInput(): String ={
      readLine.trim.toUpperCase
  }
  @tailrec
  def mainLoop(): Unit ={
    showPropt()
    val userInput:String = getUserInput()
    userInput match{


      }
  }


  -----------------------------TASKS----------------------------------------------
  ler ficheiros mp3
  dar play a ficheiros mp3

  fazer o menu da cmd

  */
}
