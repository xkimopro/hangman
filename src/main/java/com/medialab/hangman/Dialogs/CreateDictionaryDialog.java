package com.medialab.hangman.Dialogs;

import com.medialab.hangman.Dictionary;
import com.medialab.hangman.FileIO;
import com.medialab.hangman.Game;
import com.medialab.hangman.Messages.LoadDictionaryOp;
import com.medialab.hangman.Messages.NewDictionaryOp;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.util.Optional;

public class CreateDictionaryDialog {

    private  TextInputDialog td;
    private Image img;

    public CreateDictionaryDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Dictionary");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField open_library_id = new TextField();
        open_library_id.setPromptText("OpenLibraryID...");
        TextField dictionary_id = new TextField();
        dictionary_id.setPromptText("DictionaryID...");


        gridPane.add(open_library_id, 0, 0);
        gridPane.add(dictionary_id, 2, 0);

        dialog.getDialogPane().setContent(gridPane);
        try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/dict.jpg")); }
        catch( Exception e){}
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(img);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(open_library_id.getText(), dictionary_id.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();



        result.ifPresent(pair -> {
            String open_library_id_str = pair.getKey();
            String dictionary_id_str = pair.getValue();

            NewDictionaryOp ndop = FileIO.createDictionaryFile(dictionary_id_str,open_library_id_str);


            int error_code = ndop.getStatus();
            String msg = ndop.getMsg();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setWidth(200);
            alert.setHeight(300);
            alert.setContentText(msg);
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();

            if (error_code == 0) {
                alert.setTitle("Success");
                alert.setHeaderText("Loaded Successfully!");
                try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/success.png")); }
                catch( Exception e){}
                stage2.getIcons().add(img);
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Something went wrong!");
                try { img = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/fail.png")); }
                catch( Exception e){}
                stage2.getIcons().add(img);
            }
            alert.showAndWait();
        });

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
