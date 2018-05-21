package sample;

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

    private boolean running=false;

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

    @FXML
  private void startStopTimer(){
        if(running){
            timer.stop();
            startStopButton.setText("START");
            scrambleLabel.setText(generateScramble());
        } else{
            timer.start();
            startStopButton.setText("STOP");
        }
    }
}
