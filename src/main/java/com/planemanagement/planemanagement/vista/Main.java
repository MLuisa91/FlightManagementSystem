package com.planemanagement.planemanagement.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/planemanagement/planemanagement/vista/login.fxml"));
        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }

   /* @Override
    public void start(Stage primaryStage) throws Exception{

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aviones/vista/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/aviones/vista/login.fxml"));

        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();*/


    public static void main(String[] args) {
        launch(Main.class);
    }


}
