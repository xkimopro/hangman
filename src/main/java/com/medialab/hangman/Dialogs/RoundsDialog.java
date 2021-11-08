package com.medialab.hangman.Dialogs;

import com.medialab.hangman.Messages.LoadStatsOp;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

import static java.lang.Math.min;

public class RoundsDialog {
    private Image img;
    private Alert alert;

    public RoundsDialog(LoadStatsOp lsop){
        alert = new Alert(Alert.AlertType.INFORMATION);

        if (lsop.getStatus() == 0) {
            String contentText = parseList(lsop.getStats());

            alert.setTitle("Final 5 Rounds");
            alert.setHeaderText("Results for the last five rounds");
            alert.setContentText(contentText);

        }
        else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Something went wrong during file read");
            alert.setContentText(lsop.getMsg());
        }
        alert.setWidth(200);
        alert.setHeight(300);

    }

    private String parseList(ArrayList<String>stats){
        String contentText = "";
        int limit = min(stats.size() , 5);
        for (int i=stats.size()-1; i>=(stats.size()-limit); i--){
            contentText += stats.get(i) + "\n";
        }
        return  contentText;

    }

    public void show(){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/hangman.png")); }
        catch( Exception e){}
        stage.getIcons().add(img);

        alert.showAndWait();
    }
}
