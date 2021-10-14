package app.classes;


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

    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter pw;

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
        try{
            File file = new File("algorithmOutput.txt");
            //File testFile = new File("test.txt");
            //testFile.delete();
            file.delete();
            fw = new FileWriter("algorithmOutput.txt", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.print("");
        } catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i<maxGenerations; i++){
            //System.out.println("Generation = "+ i + "( Population: " + population.individuals.size() + ")" );
            children.clear();
            //EVALUATION
            population.calculateIndividualsDecimalValue(rangeA, rangeB);
            population.calculateIndividualsY();
            //pw.println("Generation: "+i);
            //pw.flush();
            //for(Individual t: population.individuals){
            //    pw.println("F("+t.getDecimalValue_x1()+", "+t.getDecimalValue_x2()+") = "+t.getY());
            //}
            //PRESENTATION
            Individual solution = population.getBestIndividual();
            gui.addDataToBestSeries(i, solution.getY());
            System.out.println(i+": " + solution.getY());
            pw.println(i+": " + solution.getY());
            pw.flush();

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
        pw.println(maxGenerations-1 +": " + solution.getY());
        pw.flush();
        try {
            pw.close();
            bw.close();
            fw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        double stop = System.currentTimeMillis();
        System.out.println("BEST SOLUTION");
        System.out.println("F(" + solution.getDecimalValue_x1() + ", " + solution.getDecimalValue_x2() + ") = " + solution.getY());
        System.out.println("Time to calculate = "+(stop-start)/1000 + "s");
        gui.addDataToBestSeries(maxGenerations-1, solution.getY());
        gui.exportBestChart();
        gui.displaySolution(solution, (stop-start)/1000);

    }
}
