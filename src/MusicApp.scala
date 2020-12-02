import javafx.application.Application
import javafx.scene.{Group, Scene}
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.stage.Stage
import javafx.stage.FileChooser

import java.io.File
import java.net.URL

class MusicApp extends Application {

  override def start(primaryStage: Stage): Unit = {

    primaryStage.setTitle("MP3")
    //val file:File = new File("C:/Joao/Music/Arctic Monkeys/AM (Album)/test.mp3")

    val fileChooser = new FileChooser
    val selectedFile:File = fileChooser.showOpenDialog(primaryStage)

    //val resource = getClass.getClassLoader.getResource("C:/Joao/Music/doiwannaknow.mp3")
    println(selectedFile.getAbsoluteFile)
    val resource = getClass.getClassLoader.getResource(selectedFile.getAbsolutePath)
    //val pick: Media = new Media(resource.toString)
    val pick: Media = new Media(selectedFile.toURI.toString)
    val player: MediaPlayer = new MediaPlayer(pick)
    val m: MediaView = new MediaView(player)

    val g: Group = new Group(m)
    val scene = new Scene(g)
    primaryStage.setScene(scene)
    primaryStage.show()

    player.play


  }
}