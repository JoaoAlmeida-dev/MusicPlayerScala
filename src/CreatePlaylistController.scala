
import Data.{DatabaseFunc, Playlist}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, Label, TextField}
import javafx.stage.Stage

class CreatePlaylistController {
  @FXML private var nameTextField:TextField =_
  @FXML private var themeTextField:TextField =_
  @FXML private var alertLabel:Label =_
  @FXML private var okButton:Button =_

  def create(): Unit ={
    val name:String = nameTextField.getText
    val playlistcheck=Playlist.loaded.filtered(x=>x.name.equals(name))
    if(playlistcheck.isEmpty) {
      val newid: Int = DatabaseFunc.getlastidPlaylists(Data.Playlist.loaded) + 1
      val playlist:Playlist = Playlist(List[String](
        newid.toString,
        name,
        "",
        themeTextField.getText
      ) )
      println(playlist)
      Data.Playlist.loaded.add(playlist)
      println(Playlist.loaded)
      okButton.getScene().getWindow().asInstanceOf[Stage].close()
    }else{
      //alertLabel.setText("Playlist with that name already exists, try again!")
      val alert = new Alert(AlertType.WARNING)
      alert.setTitle("Try again!")
      alert.setHeaderText("Playlist already exists")
      alert.setContentText("Try again!")

      alert.showAndWait()
    }
  }

def changeFocus(): Unit ={
  themeTextField.requestFocus()
}

}
