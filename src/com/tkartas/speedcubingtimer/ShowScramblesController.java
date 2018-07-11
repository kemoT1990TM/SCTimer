package com.tkartas.speedcubingtimer;

import com.tkartas.speedcubingtimer.datamodel.ScramblesAndTimes;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ShowScramblesController{
    @FXML
    private TextArea scramblesArea;
    @FXML
    private ChoiceBox scrambleChoice;

    @FXML
    public void initialize(){
        scrambleChoice.getSelectionModel().select("Best single time");
    }

    @FXML
    public void scrambleChoiceAction() {
        String choice = scrambleChoice.getValue().toString();
        switch (choice) {
            case "Best single time":
                scramblesArea.setText(ScramblesAndTimes.minScramble);
                break;
            case "Best average of 5":
                scramblesArea.setText(ScramblesAndTimes.avg5Scrambles);
                break;
            case "Best average of 12":
                scramblesArea.setText(ScramblesAndTimes.avg12Scrambles);
                break;
        }
    }


}
