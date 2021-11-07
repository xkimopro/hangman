package com.medialab.hangman.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class GameOverDialog {
    private Image img;
    private Alert alert;

    public GameOverDialog(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Game is over");
        alert.setContentText("You have failed to guess the word six times. You have lost this game. Try again.");
        alert.setWidth(200);
        alert.setHeight(300);
    }

    public void show(){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/fail.png")); }
        catch( Exception e){}
        stage.getIcons().add(img);

        alert.showAndWait();
    }
}
