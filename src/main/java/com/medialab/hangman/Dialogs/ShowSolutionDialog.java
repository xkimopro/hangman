package com.medialab.hangman.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ShowSolutionDialog {
    private Image img;
    private Alert alert;

    public ShowSolutionDialog(String answer){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Game is over");
        alert.setContentText("The answer is: "  + answer + "\nYou have lost the game because you 've seen the selected word");
        alert.setWidth(200);
        alert.setHeight(250);
    }

    public void show(){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/hangman.png")); }
        catch( Exception e){}
        stage.getIcons().add(img);

        alert.showAndWait();
    }
}
