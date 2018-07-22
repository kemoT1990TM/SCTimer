package com.tkartas.speedcubingtimer;

import com.tkartas.speedcubingtimer.datamodel.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;



public class Controller {
    @FXML
    private Label timerLabel;
    @FXML
    public Button startStopButton;
    @FXML
    private Label scrambleLabel;
    @FXML
    private TextArea timesArea;
    @FXML
    private Label analyzerLabel1, analyzerLabel2, analyzerLabel3, analyzerLabel4, analyzerLabel5, analyzerLabel6, analyzerLabel7, analyzerLabel8, analyzerLabel9;
    @FXML
    private Button plusTwoButton, deleteButton;
    @FXML
    private ChoiceBox puzzleChoice;
    @FXML
    private BorderPane mainSCTimerPane;

    private ScramblesAndTimes scramblesAndTimes=new ScramblesAndTimes("SCTimer");

    private boolean running = false;

    private TimeFormatter formatter=new TimeFormatter();



    private AnimationTimer timer = new AnimationTimer() {
        private long startTime;
        private long time = 0;

        @Override
        public void start() {
            super.start();
            running=true;
            startTime = System.currentTimeMillis();
        }

        @Override
        public void stop() {
            super.stop();
            running = false;
        }



        @Override
        public void handle(long now) {
            String elapsedTime;
            long newTime = System.currentTimeMillis();
            time = newTime - startTime;
            elapsedTime=formatter.cutLastMilli(formatter.convertToDateFormat(time));
            timerLabel.setText(elapsedTime);
        }
    };





//    @FXML
//    private void handleKeyPressed(KeyEvent event) {
//        System.out.println(event.getCode());
//        if (event.getCode().equals(KeyCode.SPACE)) {
//            timer.stop();
//            startStopButton.setText("START");
//        }
//    }
//
//    @FXML
//    private void handleKeyReleased(KeyEvent event) {
//        System.out.println(event.getCode());
//        if (event.getCode().equals(KeyCode.SPACE) {
//            timer.start();
//            startStopButton.setText("STOP");
//        }
//    }

    @FXML
    private void initialize() throws IOException {
        puzzleChoice.getSelectionModel().select("3x3x3");
        updateScrambleLabel(choosePuzzle());
        plusTwoButton.setDisable(true);
        deleteButton.setDisable(true);

//        Platform.runLater(()->startStopButton.requestFocus());
        }

        public void focusButton(){
            startStopButton.requestFocus();
        }

    @FXML
    public void showScramblesDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainSCTimerPane.getScene().getWindow());
        dialog.setTitle("Show scrambles");
        dialog.setHeaderText("Use this dialog to generate scramble list");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ShowScrambles.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
    }

//    private String generateScramble(int choice) {
//        ScrambleGenerator scramblePuzzle = new ScrambleGenerator(choice);
//        return scramblePuzzle.generateWcaScramble();
//    }

    public void updateScrambleLabel(int choice) {
       final ScrambleGenerator scramblePuzzle = new ScrambleGenerator(choice);
        Task<String> generateScrambleTask=new Task<String>() {
            @Override
            protected String call() throws Exception {
                return scramblePuzzle.generateWcaScramble();
            }
        };
        new Thread(generateScrambleTask).start();
        scrambleLabel.textProperty().bind(generateScrambleTask.valueProperty());
    }

    public void updateScrambleLabelAfterPuzzleChoice(){
        updateScrambleLabel(choosePuzzle());
    }

    @FXML
    private int choosePuzzle() {
        String choice = puzzleChoice.getValue().toString();
        return Integer.parseInt(choice.substring(0, 1));
    }

    @FXML
    private void updateLabel(Label labelName, String text) {
        String[] textFromLabel = labelName.getText().split(": ");
        StringBuilder sb = new StringBuilder();
        Task<String> task= new Task<String>() {
            @Override
            protected String call() throws Exception {
                sb.append(textFromLabel[0]);
                sb.append(": ");
                sb.append(text);
                return sb.toString();
            }
        };
        new Thread(task).start();
        labelName.textProperty().bind(task.valueProperty());
    }

    private void printTimes(){

        Task<String> task=new Task<String>() {
            @Override
            protected String call() throws Exception {
                return scramblesAndTimes.printTimes();
            }
        };
        new Thread(task).start();
        timesArea.textProperty().bind(task.valueProperty());
}


    @FXML
    private void updateAnalyzer() {
        updateLabel(analyzerLabel1, String.valueOf(scramblesAndTimes.getSizeOfTimes()));
        if (scramblesAndTimes.getSizeOfTimes() <= 0) {
            deleteButton.setDisable(true);
            plusTwoButton.setDisable(true);
        }
        scramblesAndTimes.updateMinScramble();
        scramblesAndTimes.updateAvg5Scrambles();
        scramblesAndTimes.updateAvg12Scrambles();
        if (scramblesAndTimes.getSizeOfTimes() > 0) {
            updateLabel(analyzerLabel2, scramblesAndTimes.minTime());
            updateLabel(analyzerLabel3, scramblesAndTimes.maxTime());
        } else {
            updateLabel(analyzerLabel2, "");
            updateLabel(analyzerLabel3, "");
        }
        if (scramblesAndTimes.getSizeOfTimes() >= 5) {
            updateLabel(analyzerLabel4, scramblesAndTimes.currentAvg5());
            updateLabel(analyzerLabel5, scramblesAndTimes.bestAvg5());
        } else {
            updateLabel(analyzerLabel4, "");
            updateLabel(analyzerLabel5, "");
        }
        if (scramblesAndTimes.getSizeOfTimes() >= 12) {
            updateLabel(analyzerLabel6, scramblesAndTimes.currentAvg12());
            updateLabel(analyzerLabel7, scramblesAndTimes.bestAvg12());
        } else {
            updateLabel(analyzerLabel6, "");
            updateLabel(analyzerLabel7, "");
        }
        if (scramblesAndTimes.getSizeOfTimes() >= 100) {
            updateLabel(analyzerLabel8, scramblesAndTimes.currentAvg100());
            updateLabel(analyzerLabel9, scramblesAndTimes.bestAvg100());
        } else {
            updateLabel(analyzerLabel8, "");
            updateLabel(analyzerLabel9, "");
        }
    }

    @FXML
    private void deleteButtonAction() {
        if (scramblesAndTimes.getSizeOfTimes() <= 0) {
            deleteButton.setDisable(true);
            plusTwoButton.setDisable(true);
        } else {
            scramblesAndTimes.deleteLastTime();
            printTimes();
            updateAnalyzer();
        }
    }

    @FXML
    private void plusTwoButtonAction() {
        scramblesAndTimes.plusTwo();
        printTimes();
        updateAnalyzer();
        plusTwoButton.setDisable(true);
    }

    @FXML
    private void generateScrButtonAction() {
        updateScrambleLabel(choosePuzzle());
    }

    @FXML
    public void startStopTimer() {
        if (running) {
            timer.stop();
            plusTwoButton.setDisable(false);
            deleteButton.setDisable(false);
            startStopButton.setText("START");
            scramblesAndTimes.addRecord(scrambleLabel.getText(),timerLabel.getText());
            printTimes();
            updateAnalyzer();
            updateScrambleLabel(choosePuzzle());
        } else {
            timer.start();
            plusTwoButton.setDisable(true);
            deleteButton.setDisable(true);
            startStopButton.setText("STOP");
        }
    }
    @FXML
    public void storeData() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Storing data");
        alert.setHeaderText("Text file with times and scrambles will be created.");
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get()) == ButtonType.OK) {
            scramblesAndTimes.storeScramblesAndTImes();
        }
    }

    @FXML
    public void resetSession(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resetting session");
        alert.setHeaderText("Your session is going to be reset! All times and scrambles will be gone!");
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get()) == ButtonType.OK) {
            scramblesAndTimes=new ScramblesAndTimes("SCTimer");
            updateAnalyzer();
            printTimes();
            plusTwoButton.setDisable(true);
            deleteButton.setDisable(true);
            timerLabel.setText("0.00");
        }
    }

}

