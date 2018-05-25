package com.tkartas.speedcubingtimer;

import com.tkartas.generatescramble.GenerateScramble;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.text.*;


public class Controller {
    @FXML
    private Label timerLabel;
    @FXML
    private Button startStopButton;
    @FXML
    private Label scrambleLabel;
    @FXML
    private Label timesLabel;
    @FXML
    private TextArea timesArea;
    @FXML
    private Label analyzerLabel1, analyzerLabel2, analyzerLabel3, analyzerLabel4, analyzerLabel5, analyzerLabel6, analyzerLabel7, analyzerLabel8, analyzerLabel9;
    @FXML
    private Button plusTwoButton, deleteButton;

    private boolean running = false;
    private Analyzer timesList = new Analyzer();

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
//        if (event.getCode().equals(KeyCode.SPACE) && running==true) {
//            timer.stop();
//            startStopButton.setText("START");
//        }
//    }
//    @FXML
//    private void handleKeyReleased(KeyEvent event) {
//        System.out.println(event.getCode());
//        if (event.getCode().equals(KeyCode.SPACE) && running==false) {
//            timer.start();
//            startStopButton.setText("STOP");
//        }
//    }

    @FXML
    private void initialize() {
        scrambleLabel.setText(generateScramble());
    }

    private String generateScramble() {
        GenerateScramble scramble = new GenerateScramble(25);
        return scramble.getScramble();
    }

    private void updateLabel(Label labelName, String text) {
        String[] textFromLabel = labelName.getText().split(": ");
        StringBuilder sb = new StringBuilder();
        sb.append(textFromLabel[0]);
        sb.append(": ");
        sb.append(text);
        labelName.setText(sb.toString());
    }

    private void updateAnalyzer() {

        updateLabel(analyzerLabel1, String.valueOf(timesList.getSize()));
        if (timesList.getSize() > 0) {
            updateLabel(analyzerLabel2, timesList.minTime());
            System.out.println(timesList.minTime());
            updateLabel(analyzerLabel3, timesList.maxTime());
            System.out.println(timesList.maxTime());
        } else {
            updateLabel(analyzerLabel2, "");
            updateLabel(analyzerLabel3, "");
        }
        if (timesList.getSize() >= 5) {
            updateLabel(analyzerLabel4, timesList.currentAvg5());
            updateLabel(analyzerLabel5, timesList.bestAvg5());
        } else {
            updateLabel(analyzerLabel4, "");
            updateLabel(analyzerLabel5, "");
        }
        if (timesList.getSize() >= 12) {
            updateLabel(analyzerLabel6, timesList.currentAvg12());
            updateLabel(analyzerLabel7, timesList.bestAvg12());
        } else {
            updateLabel(analyzerLabel6, "");
            updateLabel(analyzerLabel7, "");
        }
        if (timesList.getSize() >= 100) {
            updateLabel(analyzerLabel8, timesList.currentAvg100());
            updateLabel(analyzerLabel9, timesList.bestAvg100());
        } else {
            updateLabel(analyzerLabel8, "");
            updateLabel(analyzerLabel9, "");
        }
    }

    @FXML
    private void deleteButtonAction() {

        if (timesList.getSize() <= 0) {
        } else {
            timesList.deleteLastTime();
            timesArea.setText(timesList.printTimes());
            updateAnalyzer();
        }
    }

    @FXML
    private void plusTwoButtonAction() {
//        timesList.plusTwo();
        timesArea.setText(timesList.printTimes());
        updateAnalyzer();
    }

    @FXML
    private void startStopTimer() {
        String time;
        if (running) {
            timer.stop();
            startStopButton.setText("START");
            scrambleLabel.setText(generateScramble());
            time = timerLabel.getText();
            timesList.addTime(time);
            timesArea.setText(timesList.printTimes());
            updateAnalyzer();

        } else {
            timer.start();
            startStopButton.setText("STOP");
        }
    }
}
