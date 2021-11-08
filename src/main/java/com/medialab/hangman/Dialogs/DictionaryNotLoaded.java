package com.medialab.hangman.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class DictionaryNotLoaded {
    private Image img;
    private Alert alert;

    public DictionaryNotLoaded(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Dictionary not loaded");
        alert.setContentText("Try to load a dictionary from a file \nusing its dictionary id");
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
