package com.medialab.hangman.Dialogs;

import com.medialab.hangman.Dictionary;
import com.medialab.hangman.FileIO;
import com.medialab.hangman.Game;
import com.medialab.hangman.Messages.LoadDictionaryOp;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Optional;

public class LoadDictionaryDialog {

    private  TextInputDialog td;
    private Image img;

    public LoadDictionaryDialog() {
        td = new TextInputDialog();
        td.setHeaderText("Load Dictionary");
        td.setContentText("Enter DictionaryID: ");
    }

    public Dictionary show(){
        Optional<String> result = td.showAndWait();
        if (!result.isPresent()) return null;

        String dictionary_id = td.getEditor().getText();
        LoadDictionaryOp ldop = FileIO.loadNewDictionaryFile(dictionary_id);

        int error_code = ldop.getStatus();
        String msg = ldop.getMsg();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setWidth(200);
        alert.setHeight(300);
        alert.setContentText(msg);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        if (error_code == 0) {
            alert.setTitle("Success");
            alert.setHeaderText("Loaded Successfully!");
            try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/success.png")); }
            catch( Exception e){}
            stage.getIcons().add(img);
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong!");
            try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/fail.png")); }
            catch( Exception e){}
            stage.getIcons().add(img);
        }
        alert.showAndWait();
        Dictionary d = ldop.getDict();
        return d;
    }

}
