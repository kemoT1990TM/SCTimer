package com.tkartas.speedcubingtimer;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ShowScramblesController{
    @FXML
    private TextArea scramblesArea;
    @FXML
    private ChoiceBox scrambleChoice;

    @FXML
    public void scrambleChoiceAction() {
        String choice = scrambleChoice.getValue().toString();
        switch (choice) {
            case "Best single time":
                scramblesArea.setText(Controller.minScramble);
                break;
            case "Best average of 5":
                scramblesArea.setText(Controller.avg5Scrambles);
                break;
            case "Best average of 12":
                scramblesArea.setText(Controller.avg12Scrambles);
                break;
        }
    }


}
