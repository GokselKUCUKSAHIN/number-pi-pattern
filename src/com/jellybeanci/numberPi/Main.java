package com.jellybeanci.numberPi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        stage.setTitle("JellyBeanci");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
