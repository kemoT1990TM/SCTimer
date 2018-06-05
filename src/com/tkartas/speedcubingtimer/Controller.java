package com.tkartas.speedcubingtimer;

import com.tkartas.speedcubingtimer.datamodel.ScrambleGenerator;
import com.tkartas.speedcubingtimer.datamodel.Scrambles;
import com.tkartas.speedcubingtimer.datamodel.ScramblesAndTimes;
import com.tkartas.speedcubingtimer.datamodel.Times;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Controller {
    @FXML
    private Label timerLabel;
    @FXML
    private Button startStopButton;
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

    private AnimationTimer timer = new AnimationTimer() {
        private long startTime;
        private double time = 0;

        @Override
        public void start() {
            super.start();
            startTime = System.currentTimeMillis();
            running = true;
        }

        @Override
        public void stop() {
            super.stop();
            running = false;
        }

        @Override
        public void handle(long now) {
            DateFormat df = new SimpleDateFormat("s.SS");
            long newTime = System.currentTimeMillis();
            time = newTime - startTime;
            double timeDev = time / 1000;
            if (timeDev >= 10 && timeDev < 60) {
                df = new SimpleDateFormat("ss.SS");
            } else if (timeDev >= 60 && timeDev < 600) {
                df = new SimpleDateFormat("m:ss.SS");
            } else if (timeDev >= 600 && timeDev < 3600) {
                df = new SimpleDateFormat("mm:ss.SS");
            } else if (timeDev > 3600) {
                df = new SimpleDateFormat("HH:mm:ss.SS");
            }
            timerLabel.setText(df.format(time));
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
    private void initialize() {
        puzzleChoice.getSelectionModel().select("3x3x3");
        scrambleLabel.setText(generateScramble(choosePuzzle()));
        plusTwoButton.setDisable(true);
        deleteButton.setDisable(true);
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


    public void updateScrambleLabel() {
        scrambleLabel.setText(generateScramble(choosePuzzle()));
    }

    private String generateScramble(int choice) {
        ScrambleGenerator scramblePuzzle = new ScrambleGenerator(choice);
        return scramblePuzzle.generateWcaScramble();
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
        sb.append(textFromLabel[0]);
        sb.append(": ");
        sb.append(text);
        labelName.setText(sb.toString());
    }

    @FXML
    private void updateAnalyzer() {
        updateLabel(analyzerLabel1, String.valueOf(scramblesAndTimes.getSizeOfTimes()));
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
        } else {
            scramblesAndTimes.deleteLastTime();
            timesArea.setText(scramblesAndTimes.printTimes());
            updateAnalyzer();
            deleteButton.setDisable(true);
        }
    }

    @FXML
    private void plusTwoButtonAction() {
        scramblesAndTimes.plusTwo();
        timesArea.setText(scramblesAndTimes.printTimes());
        updateAnalyzer();
        plusTwoButton.setDisable(true);
    }

    @FXML
    private void generateScrButtonAction() {
        scrambleLabel.setText(generateScramble(choosePuzzle()));
    }

    @FXML
    private void startStopTimer() {
        String time;
        String scramble;
        if (running) {
            scramble = scrambleLabel.getText();
            timer.stop();
            startStopButton.setText("START");
            scrambleLabel.setText(generateScramble(choosePuzzle()));
            time = timerLabel.getText();
            scramblesAndTimes.addRecord(scramble,time);
            timesArea.setText(scramblesAndTimes.printTimes());
            updateAnalyzer();
            plusTwoButton.setDisable(false);
            deleteButton.setDisable(false);
        } else {
            timer.start();
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
            timesArea.setText(scramblesAndTimes.printTimes());
            plusTwoButton.setDisable(false);
            deleteButton.setDisable(false);
            timerLabel.setText("0.00");
        }
    }

}

