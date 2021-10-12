package app.classes;


import java.security.spec.RSAOtherPrimeInfo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GeneticAlgorithm {
    private Random rn = new Random();
    private int generation = 0;
    private int maxGenerations = 0;
    private int rangeA, rangeB = 0;
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

    public GeneticAlgorithm(int rangeA, int rangeB, int maxGenerations, int initialPopulationSize, int geneLength, int bestAndTourneyIndividualsAmount, double mutationProbability, double inversionProbability, double crossProbability, int eliteAmount ){
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

    public void mainLoop(){
        //population.calculateIndividualsDecimalValue(rangeA, rangeB);
        //population.calculateIndividualsY();
        //population.debug();
        LinkedList<Individual> children = new LinkedList<>();
        for(int i = 0; i<maxGenerations; i++){
            //System.out.println("Generation = "+ i + "( Population: " + population.individuals.size() + ")" );
            //DEBUG
            //population.calculateIndividualsDecimalValue(rangeA, rangeB);
            //population.calculateIndividualsY();
            //Individual solution = population.getBestIndividual();

            //System.out.println("BEST CANDIDATE");
            //System.out.println("F(" + solution.getDecimalValue_x1() + ", " + solution.getDecimalValue_x2() + ") = " + solution.getY());
            //END OF DEBUG
            children.clear();
            //EVALUATION
            population.calculateIndividualsDecimalValue(rangeA, rangeB);
            population.calculateIndividualsY();

            //ELITE STRATEGY
            //population.getElites(eliteAmount, "min");
            //population.getOneElite();
            //System.out.println("ELITE Y = " + population.elite.getY());
            //System.out.println(i+": " + population.elite.getY());
            Individual solution = population.getBestIndividual();

            System.out.println(i+": " + solution.getY());
            //SELECTION
            population.setIndividuals(selectionManager.rouletteSelection("min", population.individuals));
            //CROSSOVER
            Individual newIndividual;
            for(int j = 0;j<population.individuals.size()-1; j+=2){
                if(0+(1-0) * rn.nextDouble()<=crossProbability) {
                    newIndividual = new Individual(crossOverManager.crossOver_twoPoints(population.individuals.get(j).genes_x1, population.individuals.get(j + 1).genes_x1)
                            , crossOverManager.crossOver_onePoint(population.individuals.get(j).genes_x2, population.individuals.get(j + 1).genes_x2));
                    children.add(newIndividual);
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
            population.addChildrenToPopulation(children);
            //population.addChildrenToPopulation(population.elites);
            //population.addIndividualToPopulation(population.elite);

        }

        population.calculateIndividualsDecimalValue(rangeA, rangeB);
        population.calculateIndividualsY();
        Individual solution = population.getBestIndividual();
        System.out.println("BEST SOLUTION");
        System.out.println("F(" + solution.getDecimalValue_x1() + ", " + solution.getDecimalValue_x2() + ") = " + solution.getY());


        //----------------------------------------DEBUG--------------------------------------------------//
        //population.debug();
        //population.setIndividuals(selectionManager.bestSelection("min", population.individuals));
        //population.setIndividuals(selectionManager.tournamentSelection("min", population.individuals));
        //population.setIndividuals(selectionManager.rouletteSelection("min", population.individuals));

        //DEBUG
        /*System.out.println("AFTER SELECTION");
        for(Individual i: population.individuals){
            System.out.println();
            System.out.println("x1 = " + i.getDecimalValue_x1() + "|" + "x2 = " + i.getDecimalValue_x2());
            System.out.println("y = " + i.getY());
        }*/

        //int[] test1 = {1,1,0,1,0,1,1,0,0,0};
        //int[] test2 = {1,0,1,1,1,0,0,1,1,0};
        //Individual newIndividual = new Individual(crossOverManager.crossOver_onePoint(test1, test2), crossOverManager.crossOver_onePoint(test2, test1));
        //Individual newIndividual = new Individual(crossOverManager.crossOver_twoPoints(test1, test2), crossOverManager.crossOver_twoPoints(test2, test1));
        //Individual newIndividual = new Individual(crossOverManager.crossOver_threePoints(population.individuals.get(0).genes_x1, population.individuals.get(0).genes_x2),
        //        crossOverManager.crossOver_threePoints(population.individuals.get(0).genes_x2, population.individuals.get(0).genes_x1));
        //Individual newIndividual = new Individual(crossOverManager.crossOver_uniform(population.individuals.get(0).genes_x1, population.individuals.get(0).genes_x2),
        //        crossOverManager.crossOver_uniform(population.individuals.get(0).genes_x2, population.individuals.get(0).genes_x1));
        /*String c1="",c2="", p1="", p2 = "" ;
        for(int i = 0; i< newIndividual.genes_x1.length; i++){
            p1+=population.individuals.get(0).genes_x1[i];
            p2+=population.individuals.get(0).genes_x2[i];
            c1+=newIndividual.genes_x1[i];
            c2+=newIndividual.genes_x2[i];
        }*/
        /*System.out.println("Parent x1 = " + p1);
        System.out.println("Parent x2 = " + p2);
        System.out.println("Child x1 = " + c1);
        System.out.println("Child x2 = " + c2);*/
        //crossOverManager.crossOver_onePoint(test1, test2);

        //crossOverManager.crossOver_twoPoints(test1, test2);
        //crossOverManager.crossOver_threePoints(population.individuals.get(0).genes_x1, population.individuals.get(1).genes_x1);
        //Individual individual = crossOverManager.crossOver_uniform(test1, test2);

        /*//Debug
        String s1 = "", s2 = "";
        for(int i = 0;i<individual.getGenes_x1().length; i++){
            s1+=individual.getGenes_x1()[i];
            s2+=individual.getGenes_x2()[i];
        }
        System.out.println("x1 = "+s1 + " | x2 = " + s2);

        population.addIndividualToPopulation(individual);*/
        /*String s1 = "";
        for(int i = 0;i<population.individuals.get(0).getGenes_x1().length; i++) {
            s1 += population.individuals.get(0).getGenes_x1()[i];
        }
        //mutationManager.mutate_twoPoints(population.individuals.get(0));
        inversionManager.inverse(population.individuals.get(0));
        //DEBUG
        String s2 = "";
        for(int i = 0;i<population.individuals.get(0).getGenes_x1().length; i++){
            s2+=population.individuals.get(0).getGenes_x1()[i];
        }
        System.out.println("x1 before = "+s1 + " | x1 after = " + s2);*/


    }




}
