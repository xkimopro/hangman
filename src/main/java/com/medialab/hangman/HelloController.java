package com.medialab.hangman;

import com.medialab.hangman.Dialogs.GameOverDialog;
import com.medialab.hangman.Dialogs.GameWonDialog;
import com.medialab.hangman.Dialogs.RoundsDialog;
import com.medialab.hangman.Dialogs.ShowSolutionDialog;
import com.medialab.hangman.Messages.LoadDictionaryOp;
import com.medialab.hangman.Messages.LoadStatsOp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

public class HelloController {
    @FXML
    public Label scoreLabel;
    @FXML
    public Label remainingWordsLabel;
    @FXML
    public Label ratioLabel;
    @FXML
    public Label choicesLabel;
    @FXML
    private Button pickLetterButton;
    @FXML
    private Label choiceDescriber;
    @FXML
    private AnchorPane positionMapsPane;
    @FXML
    private AnchorPane wordPresentationPane;
    @FXML
    private SplitPane mainPane;


    private int rounds = 1;
    private int selectedCell = 0;
    private  int word_size;
    private  int cellWidth = 30;
    private  int cellHeight = 30;
    private  int cellSpacing = 10;
    private  double paneHeight;
    private  double paneWidth;
    private  double hboxWidth;
    private Game g = null;
    private  Boolean gameRunning = false;
    private int selectedCellIndex = 0;


    private ArrayList<TextField> cells = new ArrayList<TextField>();
    private ArrayList<Button> bl;
    private ListView<Button> lv;
    private Image image;
    private ImageView imageView;

    @FXML
    protected void initialize(){

        mainPane.setDisable(true);

        pickLetterButton.setVisible(false);
        choiceDescriber.setVisible(false);
        scoreLabel.setVisible(false);
        remainingWordsLabel.setVisible(false);
        ratioLabel.setVisible(false);
        choicesLabel.setVisible(false);


//        positionMapsPane.setFocusTraversable(false);

//        ActionEvent ev = new ActionEvent();
//        this.startGame(ev);
    }

    @FXML
    protected  void startGame(ActionEvent event){

        event.consume();
        if (gameRunning) return;
        mainPane.setDisable(false);

        initializeGameState();

        ArrayList<Character> current_word = g.getCurrent_word();
        word_size = current_word.size();
        pickLetterButton.setVisible(true);
        choiceDescriber.setVisible(true);
        scoreLabel.setVisible(true);
        remainingWordsLabel.setVisible(true);
        ratioLabel.setVisible(true);
        choicesLabel.setVisible(true);

        if (cells != null) {
            for (TextField tf: cells) tf.setVisible(false);
            cells.clear();
        }
        if (imageView != null) {
            imageView.setVisible(false);
        }

        gameRunning = true;

        HBox box = new HBox();
        for (int i=0; i< word_size; i++){
            TextField letterCell = new TextField();
            letterCell.setPrefWidth(cellWidth);
            letterCell.setPrefHeight(cellHeight);

            letterCell.setEditable(false);
            letterCell.setAlignment(Pos.CENTER);
            letterCell.setFont(new Font(15));
            letterCell.setCursor(Cursor.HAND);
            box.getChildren().add(letterCell);
            cells.add(letterCell);

        }

        box.setSpacing(10);


        wordPresentationPane.getChildren().add(box);
        paneHeight = wordPresentationPane.getHeight();
        paneWidth = wordPresentationPane.getWidth();
        hboxWidth = ((cellWidth+cellSpacing) * word_size - cellSpacing) ;


        double leftOffset = (paneWidth - hboxWidth)/2;
        wordPresentationPane.setTopAnchor(box, 2*paneHeight/3 );
        wordPresentationPane.setLeftAnchor(box, leftOffset);



        cells.get(selectedCellIndex).requestFocus();

        renderPossibleCharacters();
        renderHangedMan();
        renderStats();

        // OnFocusChange Listener
        Scene scene = positionMapsPane.getScene();

        scene.focusOwnerProperty().addListener(
                (prop, oldNode, newNode) -> {


                    for (int i=0; i<cells.size(); i++){
                        if (cells.get(i).isFocused() && selectedCellIndex != i) {
                            choiceDescriber.setText("Pick Letter _ For Position "+String.valueOf(i+1));
                            selectedCellIndex=i;
                            renderPossibleCharacters();
                            return;
                        }
                    }



                });



    }

    public void renderPossibleCharacters(){

//        System.out.println(selectedCellIndex);
        LinkedHashMap<Character, ArrayList<String>> lhm = g.getPositionMaps(selectedCellIndex);

        if (lv!=null) lv.getItems().clear();
        bl = new ArrayList<Button>();
        for (Character c: lhm.keySet()){

            Button b = new Button(c.toString());
            b.setPrefWidth(30);
            b.setOnAction(event -> pickLetter(c));

            Font f = new Font("Monospaced"  , 15);
            b.setAlignment(Pos.CENTER);
            b.setFont(f);
            bl.add(b);

        }
        ObservableList<Button> ol = FXCollections.observableList(bl);
        lv = new ListView<Button>(ol);
        lv.setPrefHeight(457);
        positionMapsPane.getChildren().add(lv);
    }


    protected void initializeGameState(){
        LoadDictionaryOp ldop = FileIO.loadNewDictionaryFile("A2");

        int error_code = ldop.getStatus();
        // String msg = ldop.getMsg();
        if (error_code == 0) {
            Dictionary d = ldop.getDict();
            this.g = new Game(d);

            try {
//                g.startGame();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("ERROR");
        }
    }

    @FXML
    public void makeChoice(Event e){

        Character c = choiceDescriber.getText().charAt(12);
        int index = selectedCellIndex;

        try {
           if (g.pickLetter(index,c)){

           }
           else {

           }
            ArrayList<Character> curr = g.getCurrent_word();
            for (int i=selectedCellIndex; i<curr.size(); i++){
                if (curr.get(i) == '_') {
                    selectedCellIndex = i;
                    cells.get(i).requestFocus();
                    break;
                }
            }

            String row;
           switch (g.gameStatus()) {
               case -1:
                   gameLost();
                   break;
               case 0:

                   renderPossibleCharacters();
                   renderHangedMan();
                   renderCellsBasedOnCurrentWord();
                   renderStats();
                   break;
               case 1:
                   GameWonDialog d = new GameWonDialog(g.getScore());
                   d.show();
                   renderStats();
                   stopGame();

                   row = g.getChosenWord() + "," + g.getChoices() + ",PLAYER";
                   FileIO.appendToStatsFile(row);
                   break;
           }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        e.consume();

    }

    public void renderStats(){
        int score = g.getScore();
        int words_remaining = g.getWordsRemaining();
        int choices = g.getChoices();
        int wrong_choices = g.getWrongChoices();
        double ratio;
        if (choices != 0)  ratio = ((double)(choices-wrong_choices)/(double) choices)*100;
        else ratio = 0.00;
        String ratio_str = String.format("%.2f",ratio);

        scoreLabel.setText("Score: " + score);
        remainingWordsLabel.setText("Words Remaining: " + words_remaining);
        ratioLabel.setText("Success Ratio: "+ String.valueOf(ratio_str) + "%");
        choicesLabel.setText("Attempts: " + choices);


    }

    public void renderHangedMan(){
        if (imageView!=null) imageView.setVisible(false);

        try { image = new Image(new FileInputStream("src/main/resources/com/medialab/hangman/img/hang_"+(g.getWrongChoices()+1)+".png")); }
        catch( Exception e){}
        imageView = new ImageView(image);
        imageView.setX(paneWidth/2.3);

        wordPresentationPane.getChildren().add(imageView);
    }


    public void renderCellsBasedOnCurrentWord(){
        ArrayList<Character> curr = g.getCurrent_word();
        for (int i=0; i< curr.size(); i++){
            if (curr.get(i) == '_'){
                continue;
            }
            else {
                TextField tf = cells.get(i);
                tf.setText(curr.get(i).toString());
                tf.setDisable(true);
                tf.setOpacity(0.8);

            }
        }
    }

    public void pickLetter(Character c){

        Character position = choiceDescriber.getText().charAt(choiceDescriber.getText().length()-1);
        String newText = "Pick Letter " + c.toString() + " For Position " + position.toString();
        choiceDescriber.setText(newText);
        cells.get(selectedCellIndex).requestFocus();
    }


    public void gameLost(){
        renderHangedMan();
        renderStats();
        stopGame();
        String row = g.getChosenWord() + "," + g.getChoices() + ",COMPUTER";
        try {
            FileIO.appendToStatsFile(row);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void gameWon(){

    }

    public void stopGame(){
        gameRunning = false;

        mainPane.setDisable(true);
        imageView.setOpacity(0.4);

    }


    public void exitGame(){
        exitGame();
    }

    public void showSolution(){
        if (g != null && gameRunning) {
                ShowSolutionDialog d = new ShowSolutionDialog(g.getChosenWord());
                d.show();
                gameLost();
                stopGame();

        }

    }

    public void showFiveLastRounds(){

            LoadStatsOp lsop = FileIO.readStatsFile();
            RoundsDialog rd = new RoundsDialog(lsop);
            rd.show();



    }







}