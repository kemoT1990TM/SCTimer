package com.tkartas.speedcubingtimer;

import com.tkartas.speedcubingtimer.datamodel.ScrambleGenerator;
import com.tkartas.speedcubingtimer.datamodel.Scrambles;
import com.tkartas.speedcubingtimer.datamodel.TimesAnalyzer;
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
import java.time.format.DateTimeFormatter;
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


    private boolean running = false;
    private TimesAnalyzer timesList = new TimesAnalyzer();
    private Scrambles scrambles = new Scrambles();

    public static String minScramble="Not enough times";
    public static String avg5Scrambles="Not enough times";
    public static String avg12Scrambles="Not enough times";

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
        scrambleLabel.setText(generateScramble(choicePuzzle()));
        plusTwoButton.setDisable(true);
        deleteButton.setDisable(true);
//        startStopButton.setDisable(true);
//        timesArea.setDisable(true);
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
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            DialogController controller = fxmlLoader.getController();
//            ToDoItem newItem = controller.processResults();
////            todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems()); after changing List to ObservableList
//            todoListView.getSelectionModel().select(newItem);
//        }
    }


    public void updateScrambleLabel() {
        scrambleLabel.setText(generateScramble(choicePuzzle()));
    }

    private String generateScramble(int choice) {
        ScrambleGenerator scramblePuzzle = new ScrambleGenerator(choice);
        return scramblePuzzle.generateWcaScramble();
    }

    @FXML
    private int choicePuzzle() {
        String choice = puzzleChoice.getValue().toString();
        return Integer.parseInt(choice.substring(0, 1));
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
            updateLabel(analyzerLabel3, timesList.maxTime());
            updateMinScramble();
        } else {
            updateLabel(analyzerLabel2, "");
            updateLabel(analyzerLabel3, "");
        }
        if (timesList.getSize() >= 5) {
            updateLabel(analyzerLabel4, timesList.currentAvg5());
            updateLabel(analyzerLabel5, timesList.bestAvg5());
            updateAvg5Scrambles();
        } else {
            updateLabel(analyzerLabel4, "");
            updateLabel(analyzerLabel5, "");
        }
        if (timesList.getSize() >= 12) {
            updateLabel(analyzerLabel6, timesList.currentAvg12());
            updateLabel(analyzerLabel7, timesList.bestAvg12());
            updateAvg12Scrambles();
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
            deleteButton.setDisable(true);
        }
    }

    @FXML
    private void plusTwoButtonAction() {
        timesList.plusTwo();
        timesArea.setText(timesList.printTimes());
        updateAnalyzer();
        plusTwoButton.setDisable(true);
    }

    @FXML
    private void generateScrButtonAction() {
        scrambleLabel.setText(generateScramble(choicePuzzle()));
    }

    @FXML
    private void startStopTimer() {
        String time;
        String scramble;
        if (running) {
            scramble = scrambleLabel.getText();
            timer.stop();
            startStopButton.setText("START");
            scrambleLabel.setText(generateScramble(choicePuzzle()));
            time = timerLabel.getText();
            timesList.addTime(time);
            scrambles.addRecord(scramble);
            timesArea.setText(timesList.printTimes());
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date resultDate = new Date(System.currentTimeMillis());
            String filename = "SCTimerResults" + sdf.format(resultDate) + ".txt";
            Path path = Paths.get(filename);
            BufferedWriter bw = Files.newBufferedWriter(path);
            int[] positions = new int[timesList.getSize()];
            for (int i = 0; i < timesList.getSize(); i++) {
                positions[i] = i;
            }
            try {
                for (int position : positions) {
                    bw.write(String.format("%d\t%s\t%s",
                            position,
                            timesList.getTime(position),
                            scrambles.getScrambleForPosition(position)));
                    bw.newLine();
                }
            } finally {
                if (bw != null) {
                    bw.close();
                }
            }
        }
    }

    private String getTimeForScramble(String scramble) {
        return timesList.getTime(scrambles.getPositionForScramble(scramble));
    }

    private String getScrambleForTime(String time) {
        return scrambles.getScrambleForPosition(timesList.getPosition(time));
    }

    private String printScrambleForPosition(String positionList) {
        String[] positions = positionList.split(",");
        int intPosition=0;
        for (String position : positions) {
            intPosition=Integer.parseInt(position);
        }
        return scrambles.getScrambleForPosition(intPosition);
    }

    private String printScrambles(String avgPositions) {
        int i = 1;
        String[] positions = avgPositions.split(",");
        List<String> scrambleList = new LinkedList<>();
        TimesAnalyzer smallTimesList = new TimesAnalyzer();
        for (String position : positions){
            smallTimesList.addTime(timesList.getTime(Integer.parseInt(position)));
        }
        String min=smallTimesList.minTime();
        String max=smallTimesList.maxTime();
        for (String position : positions) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(". ");
            if(timesList.getTime(Integer.parseInt(position)).equals(min) || timesList.getTime(Integer.parseInt(position)).equals(max)){
                sb.append("(");
            }
            sb.append(timesList.getTime(Integer.parseInt(position)));
            if(timesList.getTime(Integer.parseInt(position)).equals(min) || timesList.getTime(Integer.parseInt(position)).equals(max)){
                sb.append(")");
            }
            sb.append(" : ");
            sb.append(scrambles.getScrambleForPosition(Integer.parseInt(position)));
            scrambleList.add(sb.toString());
            i = i + 1;
        }
        StringBuilder scrambleBuilder = new StringBuilder();
        for (String scramble : scrambleList) {
            scrambleBuilder.append(scramble);
            scrambleBuilder.append("\n");
        }
        return scrambleBuilder.toString();
    }

    private void updateMinScramble() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generated by SCTimer");
        sb.append("\n");
        sb.append("Best single time: ");
        sb.append(timesList.minTime());
        sb.append("\n");
        sb.append(timesList.minTime());
        sb.append(" : ");
        sb.append(printScrambleForPosition(timesList.getMinPosition()));
        minScramble=sb.toString();
    }

    private void updateAvg5Scrambles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generated by SCTimer");
        sb.append("\n");
        sb.append("Best average of 5 times: ");
        sb.append(timesList.bestAvg5());
        sb.append("\n");
        sb.append(printScrambles(timesList.getBestAvg5Positions()));
        avg5Scrambles=sb.toString();
    }

    private void updateAvg12Scrambles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generated by SCTimer");
        sb.append("\n");
        sb.append("Best average of 12 times: ");
        sb.append(timesList.bestAvg12());
        sb.append("\n");
        sb.append(printScrambles(timesList.getBestAvg12Positions()));
        avg12Scrambles=sb.toString();
    }
}

