package com.donoso.easyflight.vista;

import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Respaldo;
import com.donoso.easyflight.utils.URLApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        HttpClient<Respaldo, Respaldo> client = new HttpClient<>(Respaldo.class);
        try {
            client.execute(URLApi.API_RESPALDO_CREATE, null, "POST");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Login.fxml"));
        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }


}
