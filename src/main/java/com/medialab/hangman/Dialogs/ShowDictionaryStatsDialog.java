package com.medialab.hangman.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.HashMap;

public class ShowDictionaryStatsDialog {
    private Image img;
    private Alert alert;

    public ShowDictionaryStatsDialog(HashMap<String, Integer> hm){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dictionary Stats");
        alert.setHeaderText("Dictionary statistics per word size. Small is strictly 6 letters \nMedium is from 7 to 9 letters and Large is from 10 letters and above");
        String contentText = "";
        int total_words = 0;
        for (String w_size: hm.keySet()){
            contentText+=w_size + ": \t" + hm.get(w_size) + "\n";
            total_words +=  hm.get(w_size);
        }

        contentText += "\nTotal words: \t"+ total_words + "\n";


        alert.setContentText(contentText);
        alert.setWidth(200);
        alert.setHeight(375);
    }

    public void show(){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/hangman.png")); }
        catch( Exception e){}
        stage.getIcons().add(img);

        alert.showAndWait();
    }
}
