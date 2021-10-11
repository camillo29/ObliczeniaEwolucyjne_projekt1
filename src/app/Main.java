package app;

import app.classes.GeneticAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        setUpAlgorithm();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setUpAlgorithm(){
        GeneticAlgorithm ga = new GeneticAlgorithm(1, 10, 10, 1000, 25, 20); //!!! AFTER SETTING NOT DIVISIBLE AMOUNT FOR SELECTION INFINITE LOOP OCCURS IN TOURNAMENT
        ga.mainLoop();
        //System.out.println("AAA");

    }
}
