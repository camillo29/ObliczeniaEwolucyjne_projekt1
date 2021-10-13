package app.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Population {
    Random rn = new Random();
    int popSize;
    LinkedList<Individual> individuals;
    LinkedList<Individual> elites = new LinkedList<>();
    Individual elite;
    //int fittest = 0;
    int bestAndTourneyIndividualsAmount;

    public Population(int size, int geneLength, int bestAndTourneyIndividualsAmount ){
        popSize = size;
        this.bestAndTourneyIndividualsAmount = bestAndTourneyIndividualsAmount;
        individuals = new LinkedList<>();
        for(int i = 0; i < popSize; i++){
            individuals.add(new Individual(geneLength));
        }
    }

    public void setIndividuals(LinkedList<Individual> newIndividuals){
        individuals.clear();
        individuals.addAll(newIndividuals);
    }

    public void addIndividualsToPopulation(LinkedList<Individual> children){
        individuals.addAll(children);
    }

    public void getElites(int eliteAmount, String mode){
        SelectionManager selection = new SelectionManager(eliteAmount);
        elites.clear();
        elites.addAll(individuals);
        elites = selection.bestSelection(mode, elites);
        individuals.removeAll(elites);
        //System.out.println("ELITE SIZE = " + elites.size());
    }

    public void getOneElite(){
        elite = individuals.getFirst();
        for(Individual i: individuals){
            if(i.getY() < elite.getY())
                elite = i;
        }
        individuals.remove(elite);
    }

    public double fitnessFunction(double x1, double x2){
        return (Math.pow((1.5- x1 + x1*x2), 2) + Math.pow((2.25 - x1+x1*x2*x2), 2) + Math.pow((2.625 - x1 + x1*Math.pow(x2, 3)), 2));
    }

    public void calculateIndividualsDecimalValue(double rangeA, double rangeB){
        for(Individual i: individuals)
            i.calculateDecimalValue(rangeA, rangeB);
    }

    public void calculateIndividualsY(){
        for(Individual i: individuals)
            i.setY(fitnessFunction(i.getDecimalValue_x1(), i.getDecimalValue_x2()));
    }

    public void addIndividualToPopulation(Individual i){
        individuals.add(i);
    }

    public Individual getBestIndividual(){
        Individual best = individuals.get(0);
        for(Individual i: individuals){
            if(i.getY()<best.getY())
                best = i;
        }
        return best;
    }
    public void debug(){
        int j = 0;
        for(Individual i: individuals){
            System.out.println("i = " + j);
            System.out.println("x1 = " + i.getDecimalValue_x1() + "|" + "x2 = " + i.getDecimalValue_x2());
            System.out.println("y = " + i.getY());
            j++;
        }
    }

}
