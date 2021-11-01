package app.classes.GUI;

import app.classes.Individual;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIManager {
    private int width = 0;
    private int height = 0;
    private Button submit;

    //Text fields
    private TextField rangeA;
    private TextField rangeB;
    private TextField population;
    private TextField numberOfBits;
    private TextField epochsAmount;
    private TextField bestAndTournamentIndividualsAmount;
    private TextField eliteStrategy;
    private TextField crossProbability;
    private TextField mutationProbability;
    private TextField inverseProbability;
    //Choice boxes
    private ChoiceBox selectionMethod;
    private ChoiceBox crossMethod;
    private ChoiceBox mutationMethod;
    //Radio button
    private RadioButton maximization;


    //Stage
    Stage primaryStage;

    public GUIManager(int width, int height, Stage primaryStage){
        this.width = width;
        this.height = height;
        this.primaryStage = primaryStage;
    }

    public void setUpGUI(){
        primaryStage.setTitle("Obliczenia ewolucyjne - projekt 1");
        Pane pane = new Pane();
        Text text = createText("Genetic algorithm for optimizing min/max in Drop-wave function", 30 ,50);

        submit = createButton("Calculate", width/2-50, height-50, 100, 30);
        //TextFields init
        rangeA = createTextField("Begin of the range - a", 20, 100, width-50, 20, true, true);
        rangeB = createTextField("Begin of the range - b", 20, 150, width-50, 20, true, true);
        population = createTextField("Population amount", 20, 200, width-50, 20, false, false);
        numberOfBits = createTextField("Number of bits", 20, 250, width-50, 20, false, false);
        epochsAmount = createTextField("Epochs amount", 20, 300, width-50, 20, false, false);
        bestAndTournamentIndividualsAmount = createTextField("Best and tournament individuals amount", 20, 350, width-50, 20, false, false);
        eliteStrategy = createTextField("Elite Strategy amount", 20, 400, width-50, 20, false, false);
        crossProbability = createTextField("Cross probability", 20, 450, width-50, 20, true, false);
        mutationProbability = createTextField("Mutation probability", 20, 500, width-50, 20, true, false);
        inverseProbability = createTextField("Inverse probability", 20, 550, width-50, 20, true, false);
        //ChoiceBoxes init
        Text selectionDesc = createText("Select Selection method", 20, 645);
        selectionMethod = createChoiceBox(20, 650, width-50, 20);
        selectionMethod.getItems().addAll("BEST", "TOURNAMENT", "ROULETTE");
        selectionMethod.setValue(selectionMethod.getItems().get(0));

        Text crossDesc = createText("Select Cross method", 20, 695);
        crossMethod = createChoiceBox(20, 700, width-50, 20);
        crossMethod.getItems().addAll("ONE_POINT", "TWO_POINTS", "THREE_POINTS", "UNIFORM");
        crossMethod.setValue(crossMethod.getItems().get(0));

        Text mutationDesc = createText("Select Mutation method", 20, 745);
        mutationMethod = createChoiceBox(20, 750, width-50, 20);
        mutationMethod.getItems().addAll("EDGE", "ONE_POINT", "TWO_POINTS");
        mutationMethod.setValue(mutationMethod.getItems().get(0));

        maximization = createRadioButton("Maximization", 20, 800, width-50, 20);

        pane.getChildren().addAll(text, submit,
                rangeA, rangeB, population, numberOfBits, epochsAmount, bestAndTournamentIndividualsAmount, eliteStrategy, crossProbability, mutationProbability, inverseProbability,
                selectionMethod, crossMethod, mutationMethod, selectionDesc, crossDesc, mutationDesc, maximization);

        primaryStage.setScene(new Scene(pane, width, height));

    }

    public void displaySolution(Individual solution, double time){
        Stage fin = new Stage();
        Pane pane = new Pane();
        fin.setTitle("Solution");
        Text header = createText("Found solution in " + time + "s", 20,20);
        Text body = createText("F("+solution.getDecimalValue_x1() + ", " + solution.getDecimalValue_x2() +") = \n =" + solution.getY(), 20, 100);
        pane.getChildren().addAll(header, body);
        fin.setScene(new Scene(pane, width, height/4));
        fin.show();
    }

    public Button createButton(String title, int layoutX, int layoutY, int width, int height){
        Button button = new Button(title);
        button.setLayoutX(layoutX); button.setLayoutY(layoutY);
        button.setPrefSize(width, height);
        return button;
    }

    public Text createText(String content, int x, int y){
        Text text = new Text();
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        text.setX(x);
        text.setY(y);
        text.setText(content);
        return text;
    }

    public TextField createTextField(String content, int layoutX, int layoutY, int width, int height, boolean isDouble, boolean signed){
        TextField textField = new TextField();
        textField.setPromptText(content);
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefSize(width, height);

        if(isDouble)
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if(signed) {
                        if (!newValue.matches("-?\\d{0,7}([\\.]\\d{0,2})?")) {
                            textField.setText(oldValue);
                        }
                    }
                    else {
                            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                                textField.setText(oldValue);
                            }
                        }
                }
            });
        else{
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }

        return textField;
    }

    public ChoiceBox createChoiceBox(int layoutX, int layoutY, int width, int height){
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setLayoutX(layoutX);
        choiceBox.setLayoutY(layoutY);
        choiceBox.setPrefSize(width, height);
        return choiceBox;
    }

    public RadioButton createRadioButton(String content, int layoutX, int layoutY, int width, int height){
        RadioButton button = new RadioButton(content);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setPrefSize(width, height);
        return button;
    }

    public Button getSubmit(){
        return submit;
    }
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public TextField getRangeA() {
        return rangeA;
    }

    public TextField getRangeB() {
        return rangeB;
    }

    public TextField getPopulation() {
        return population;
    }

    public TextField getNumberOfBits() {
        return numberOfBits;
    }

    public TextField getEpochsAmount() {
        return epochsAmount;
    }

    public TextField getBestAndTournamentIndividualsAmount() {
        return bestAndTournamentIndividualsAmount;
    }

    public TextField getEliteStrategy() {
        return eliteStrategy;
    }

    public TextField getCrossProbability() {
        return crossProbability;
    }

    public TextField getMutationProbability() {
        return mutationProbability;
    }

    public TextField getInverseProbability() {
        return inverseProbability;
    }

    public ChoiceBox getSelectionMethod() {
        return selectionMethod;
    }

    public ChoiceBox getCrossMethod() {
        return crossMethod;
    }

    public ChoiceBox getMutationMethod() {
        return mutationMethod;
    }

    public RadioButton getMaximization() {
        return maximization;
    }
}
