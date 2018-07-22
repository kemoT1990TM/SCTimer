package com.tkartas.speedcubingtimer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SCTimer.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("SCTimer");
        Scene mainScene = new Scene(root, 900, 500);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        Controller controller = loader.getController();
        controller.startStopButton.requestFocus();

        mainScene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
//                System.out.println("KeyCode = "+"("+event.getCharacter().+")");
//                if(event.getCharacter()==(char)' '){
                controller.startStopButton.requestFocus();
//                    controller.startStopTimer();
//                }
                event.consume();
            }
        });
//        mainScene.addEventHandler(KeyEvent.KEY_RELEASED,new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("KeyCode = "+event.getCode());
//                if(event.getCode()== KeyCode.UNDEFINED){
//                    controller.startStopButton.requestFocus();
//                    controller.startStopTimer();
//                }
//                event.consume();
//            }
//        });
//        mainScene.addEventHandler(KeyEvent.KEY_PRESSED,new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("KeyCode = "+event.getCode());
//                if(event.getCode()== KeyCode.UNDEFINED){
//                    controller.startStopButton.requestFocus();
//                    controller.startStopTimer();
//                }
//                event.consume();
//            }
//        });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
