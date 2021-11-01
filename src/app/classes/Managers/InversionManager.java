package app.classes.Managers;

import app.classes.Individual;

import java.util.Random;

public class InversionManager {
    Random rn = new Random();
    private double probability = 0;

    public InversionManager(double probability){
        this.probability = probability;
    }

    public void inverse(Individual individual) {
        int firstPoint = 0, secondPoint = 0;
        if(0 + (1 - 0) * rn.nextDouble() <= probability){
            while (firstPoint == secondPoint) {
                firstPoint = rn.nextInt(individual.getGenes_x1().length);
                secondPoint = rn.nextInt(individual.getGenes_x1().length - firstPoint) + firstPoint;
            }
            for (int i = firstPoint; i < secondPoint; i++) {
                if (individual.getGenes_x1()[i] == 0) {
                    individual.getGenes_x1()[i] = 1;
                }
                else {
                    individual.getGenes_x1()[i] = 0;
                }
            }
        }
        firstPoint=secondPoint=0;
        if(0 + (1 - 0) * rn.nextDouble() <= probability){
            while (firstPoint == secondPoint) {
                firstPoint = rn.nextInt(individual.getGenes_x1().length);
                secondPoint = rn.nextInt(individual.getGenes_x1().length - firstPoint) + firstPoint;
            }
            for (int i = firstPoint; i < secondPoint; i++) {
                if (individual.getGenes_x2()[i] == 0) {
                    individual.getGenes_x2()[i] = 1;
                }
                else {
                    individual.getGenes_x2()[i] = 0;
                }
            }
        }
    }
}
