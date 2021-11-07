package com.medialab.hangman;

// Java program to create a menu bar and add
// menu to it and also add menuitems to menu
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;

import com.medialab.hangman.Messages.*;

public class App {

    public void start(Stage s) {



        {
            // set title for the stage
            s.setTitle("creating MenuBar");

            // create a menu
            Menu m = new Menu("Menu");

            // create menuitems
            MenuItem m1 = new MenuItem("menu item 1");
            MenuItem m2 = new MenuItem("menu item 2");
            MenuItem m3 = new MenuItem("menu item 3");

            // add menu items to menu
            m.getItems().add(m1);
            m.getItems().add(m2);
            m.getItems().add(m3);

            // label to display events
            Label l = new Label("\t\t\t\t"
                                + "no menu item selected");

            // create events for menu items
            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    l.setText("\t\t\t\t" + ((MenuItem)e.getSource()).getText() +
                                                                   " selected");
                }
            };

            // add event
            m1.setOnAction(event);
            m2.setOnAction(event);
            m3.setOnAction(event);

            // create a menubar
            MenuBar mb = new MenuBar();

            // add menu to menubar
            mb.getMenus().add(m);

            // create a VBox
            VBox vb = new VBox(mb, l);

            // create a scene
            Scene sc = new Scene(vb, 500, 300);

            // set the scene
            s.setScene(sc);

            s.show();
        }
    }

    public static void main(String[] args) {

//        launch(args);

         // String open_library_id = "OL31390631M";
         LoadDictionaryOp ldop = FileIO.loadNewDictionaryFile("A2");

         int error_code = ldop.getStatus();
         // String msg = ldop.getMsg();
         if (error_code == 0) {
         Dictionary d = ldop.getDict();
         Game g = new Game(d);

         try {
         g.startGame();

         } catch (Exception e) {
         System.out.println(e.getMessage());
         }
         } else {

         }

    }
}
