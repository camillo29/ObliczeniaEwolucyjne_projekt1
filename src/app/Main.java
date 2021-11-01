package app;

import app.classes.GUI.GUIManager;
import app.classes.GeneticAlgorithm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GUIManager gui = new GUIManager(600, 900, primaryStage);
        gui.setUpGUI();
        gui.getSubmit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setUpAlgorithm(gui,
                        Double.parseDouble(gui.getRangeA().getText()),
                        Double.parseDouble(gui.getRangeB().getText()),
                        Integer.parseInt(gui.getEpochsAmount().getText()),
                        Integer.parseInt(gui.getPopulation().getText()),
                        Integer.parseInt(gui.getNumberOfBits().getText()),
                        Integer.parseInt(gui.getBestAndTournamentIndividualsAmount().getText()),
                        Double.parseDouble(gui.getMutationProbability().getText()),
                        Double.parseDouble(gui.getMutationProbability().getText()),
                        Double.parseDouble(gui.getInverseProbability().getText()),
                        Integer.parseInt(gui.getEliteStrategy().getText()),
                        gui.getSelectionMethod().getValue().toString(),
                        gui.getCrossMethod().getValue().toString(),
                        gui.getMutationMethod().getValue().toString(),
                        gui.getMaximization().isSelected());
            }
        });
        gui.getPrimaryStage().show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setUpAlgorithm(GUIManager gui, double rangeA, double rangeB, int maxGenerations, int initialPopulationSize, int geneLength, int bestAndTournamentIndividuals, double mutationProbability, double inversionProbability, double crossProbability, int eliteAmount, String selectionMethod, String crossOverMethod, String mutationMethod, boolean mode){
        GeneticAlgorithm ga = new GeneticAlgorithm(gui, rangeA, rangeB, maxGenerations, initialPopulationSize, geneLength, bestAndTournamentIndividuals, mutationProbability, inversionProbability, crossProbability, eliteAmount); //!!! AFTER SETTING NOT DIVISIBLE AMOUNT FOR SELECTION INFINITE LOOP OCCURS IN TOURNAMENT
        ga.setMethods(selectionMethod, crossOverMethod, mutationMethod);
        if(mode == true) ga.setMode("max");
        else ga.setMode("min");
        ga.mainLoop();

    }
}
