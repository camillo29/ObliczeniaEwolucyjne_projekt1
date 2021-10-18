package app.classes;


import app.classes.GUI.ChartManager;
import app.classes.GUI.GUIManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GeneticAlgorithm {
    private Random rn = new Random();
    private GUIManager gui;
    private int generation = 0;
    private int maxGenerations = 0;
    private double rangeA, rangeB = 0;
    private int initialPopulationSize = 0;
    private int bestAndTourneyIndividualsAmount = 0;
    private int eliteAmount = 0;
    private double mutationProbability = 0.0f;
    private double inversionProbability = 0.0f;
    private double crossProbability = 0.0f;
    private Population population;
    private SelectionManager selectionManager;
    private CrossOverManager crossOverManager;
    private MutationManager mutationManager;
    private InversionManager inversionManager;

    private String selectionMethod;
    private String crossOverMethod;
    private String mutationMethod;

    private String mode = "min";

    public GeneticAlgorithm(GUIManager gui, double rangeA, double rangeB, int maxGenerations, int initialPopulationSize, int geneLength, int bestAndTourneyIndividualsAmount, double mutationProbability, double inversionProbability, double crossProbability, int eliteAmount){
        this.gui = gui;
        this.rangeA = rangeA;
        this.rangeB = rangeB;
        this.maxGenerations = maxGenerations;
        this.initialPopulationSize = initialPopulationSize;
        this.bestAndTourneyIndividualsAmount = bestAndTourneyIndividualsAmount;
        this.eliteAmount = eliteAmount;
        this.crossProbability = crossProbability;
        this.mutationProbability = mutationProbability;
        this.inversionProbability = inversionProbability;
        population = new Population(initialPopulationSize, geneLength, bestAndTourneyIndividualsAmount);
        //Managers
        selectionManager = new SelectionManager(bestAndTourneyIndividualsAmount);
        crossOverManager = new CrossOverManager();
        mutationManager = new MutationManager(mutationProbability);
        inversionManager = new InversionManager(inversionProbability);
    }

    public void setMethods(String selectionMethod, String crossOverMethod, String mutationMethod){
        this.selectionMethod = selectionMethod;
        this.crossOverMethod = crossOverMethod;
        this.mutationMethod = mutationMethod;
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public void mainLoop(){
        //population.calculateIndividualsDecimalValue(rangeA, rangeB);
        //population.calculateIndividualsY();
        //population.debug();
        LinkedList<Individual> children = new LinkedList<>();
        double start = System.currentTimeMillis();
        //FILEMANAGERS
        FileManager bestFile = new FileManager(selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_BEST");
        FileManager meanFile = new FileManager(selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_MEAN");
        FileManager deviationFile = new FileManager(selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_DEVIATION");
        //CHARTMANAGERS
        ChartManager bestChart = new ChartManager("Best value each generation");
        ChartManager meanChart = new ChartManager("Mean value each generation");
        ChartManager deviationChart = new ChartManager("Deviation value each generation");

        double mean = 0.0f;
        double deviation = 0.0f;
        for(int i = 0; i<maxGenerations; i++){
            //System.out.println("Generation = "+ i + "( Population: " + population.individuals.size() + ")" );
            children.clear();
            //EVALUATION
            population.calculateIndividualsDecimalValue(rangeA, rangeB);
            population.calculateIndividualsY();
            //PRESENTATION
            for(Individual individual: population.individuals){
                mean+=individual.getY();
            }
            mean /= population.individuals.size();
            for(Individual individual: population.individuals){
                deviation += Math.pow(individual.getY() - mean, 2);
            }
            deviation /= population.individuals.size();
            deviation = Math.sqrt(deviation);
            Individual solution = population.getBestIndividual();
            bestChart.addDataToSeries(i, solution.getY());
            meanChart.addDataToSeries(i, mean);
            deviationChart.addDataToSeries(i, deviation);

            bestFile.writeToFile(i + ": " + solution.getY());
            meanFile.writeToFile(i + ": " + mean);
            deviationFile.writeToFile(i + ": " + deviation);

            System.out.println(i+": " + solution.getY());

            //ELITE STRATEGY
            population.getElites(eliteAmount, mode);

            //SELECTION
            population.setIndividuals(selectionManager.decideMethod(mode, population.individuals, selectionMethod));
            //CROSSOVER
            Individual newIndividual1, newIndividual2;
            while(children.size() != initialPopulationSize - eliteAmount){
                //System.out.println("CROSSOVER, children size = " + children.size());
                Individual individual1 = population.individuals.get(rn.nextInt(population.individuals.size()));
                Individual individual2 = population.individuals.get(rn.nextInt(population.individuals.size()));

                if(0+(1-0) * rn.nextDouble()<=crossProbability) {
                    newIndividual1 = new Individual(crossOverManager.decideMethod(individual1.genes_x1, individual2.genes_x1, true, crossOverMethod)
                            , crossOverManager.decideMethod(individual1.genes_x2, individual2.genes_x2, true, crossOverMethod));
                    children.add(newIndividual1);

                    if(children.size()+eliteAmount != initialPopulationSize) {
                        newIndividual2 = new Individual(crossOverManager.decideMethod(individual2.genes_x1, individual1.genes_x1, false, crossOverMethod)
                                , crossOverManager.decideMethod(individual2.genes_x2, individual1.genes_x2, false, crossOverMethod));
                        children.add(newIndividual2);
                    }
                }
            }
            //MUTATION
            for(Individual individual: children){
                mutationManager.mutate_twoPoints(individual);
            }
            //INVERSION
            for(Individual individual: children){
                inversionManager.inverse(individual);
            }

            population.setIndividuals(children);

            population.addIndividualsToPopulation(population.elites);


        }

        population.calculateIndividualsDecimalValue(rangeA, rangeB);
        population.calculateIndividualsY();
        Individual solution = population.getBestIndividual();
        bestFile.writeToFile(maxGenerations-1 + ": " + solution.getY());
        bestFile.closeFile();
        meanFile.closeFile();
        deviationFile.closeFile();
        double stop = System.currentTimeMillis();
        System.out.println("BEST SOLUTION");
        System.out.println("F(" + solution.getDecimalValue_x1() + ", " + solution.getDecimalValue_x2() + ") = " + solution.getY());
        System.out.println("Time to calculate = "+(stop-start)/1000 + "s");
        bestChart.addDataToSeries(maxGenerations-1, solution.getY());
        bestChart.exportChart("Generation", "Value", "Best value each generation", selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_BESTCHART");
        meanChart.exportChart("Generation", "Value", "Mean value each generation", selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_MEANCHART");
        deviationChart.exportChart("Generation", "Value", "Deviation value each generation", selectionMethod + "_"+crossOverMethod + "_" + mutationMethod + "_" + mode + "_DEVIATIONCHART");
        gui.displaySolution(solution, (stop-start)/1000);

    }
}
