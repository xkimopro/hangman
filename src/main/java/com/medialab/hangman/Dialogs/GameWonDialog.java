package com.medialab.hangman.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class GameWonDialog {
    private Alert alert;
    private Image img;

    public GameWonDialog(int score){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME WON");
        alert.setHeaderText("You have won the game");
        alert.setContentText("You have manage to find the selected word. Congratulations your score was:  " + score);
        alert.setWidth(200);
        alert.setHeight(300);
    }

    public void show(){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/success.png")); }
        catch( Exception e){}
        stage.getIcons().add(img);

        alert.showAndWait();
    }


}
