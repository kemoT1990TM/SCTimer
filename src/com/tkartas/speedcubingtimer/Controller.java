package com.tkartas.speedcubingtimer;

import com.tkartas.generatescramble.GenerateScramble;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label analyzerLabel1;
    @FXML
    private Label analyzerLabel2;
    @FXML
    private Label analyzerLabel3;
    @FXML
    private Label analyzerLabel4;
    @FXML
    private Label analyzerLabel5;
    @FXML
    private Label analyzerLabel6,analyzerLabel7,analyzerLabel8,analyzerLabel9;


    private boolean running=false;
    private Analyzer timesList=new Analyzer();

    private AnimationTimer timer = new AnimationTimer() {
        private long startTime;
        private double time = 0;

        @Override
        public void start() {
            super.start();
            startTime = System.currentTimeMillis();
            running=true;
        }

        @Override
        public void stop() {
            super.stop();
            running=false;
        }

        @Override
        public void handle(long now) {
            DateFormat df = new SimpleDateFormat("s.SSS");
            long newTime = System.currentTimeMillis();
            time = newTime - startTime;
            double timeDev = time / 1000;
            if (timeDev >= 10 && timeDev < 60) {
                df = new SimpleDateFormat("ss.SSS");
            } else if (timeDev >= 60 && timeDev < 600) {
                df = new SimpleDateFormat("m:ss.SSS");
            } else if (timeDev >= 600 && timeDev < 3600) {
                df = new SimpleDateFormat("mm:ss.SSS");
            } else if (timeDev > 3600) {
                df = new SimpleDateFormat("HH:mm:ss.SSS");
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
    private void initialize(){
        scrambleLabel.setText(generateScramble());
    }

    private String generateScramble(){
        GenerateScramble scramble=new GenerateScramble(25);
        return scramble.getScramble();
    }

    private void updateLabel(Label labelName,String text){
        String[] textFromLabel=labelName.getText().split(": ");
        StringBuilder sb=new StringBuilder();
        sb.append(textFromLabel[0]);
        sb.append(": ");
        sb.append(text);
        labelName.setText(sb.toString());
    }

    private void updateAnalyzer(){
        updateLabel(analyzerLabel1,String.valueOf(timesList.getSize()));
        updateLabel(analyzerLabel2,timesList.minTime());
        updateLabel(analyzerLabel3,timesList.maxTime());
        if(timesList.getSize()>=5){
            updateLabel(analyzerLabel4,timesList.currentAvg5());
            updateLabel(analyzerLabel5,timesList.bestAvg5());
        }
        if(timesList.getSize()>=12){
            updateLabel(analyzerLabel6,timesList.currentAvg12());
            updateLabel(analyzerLabel7,timesList.bestAvg12());
        }
        if(timesList.getSize()>=100){
            updateLabel(analyzerLabel8,timesList.currentAvg100());
            updateLabel(analyzerLabel9,timesList.bestAvg100());
        }
    }

    @FXML
  private void startStopTimer(){
        Double time;
        if(running){
            timer.stop();
            startStopButton.setText("START");
            scrambleLabel.setText(generateScramble());
            time=Double.parseDouble(timerLabel.getText());
            timesList.addTime(time);
            timesLabel.setText(timesList.printTimes());
            updateAnalyzer();

        } else{
            timer.start();
            startStopButton.setText("STOP");
        }
    }
}
