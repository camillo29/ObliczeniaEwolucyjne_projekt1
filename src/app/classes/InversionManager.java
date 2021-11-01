package app.classes;

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
                firstPoint = rn.nextInt(individual.genes_x1.length);
                secondPoint = rn.nextInt(individual.genes_x1.length - firstPoint) + firstPoint;
            }
            for (int i = firstPoint; i < secondPoint; i++) {
                if (individual.genes_x1[i] == 0) {
                    individual.genes_x1[i] = 1;
                }
                else {
                    individual.genes_x1[i] = 0;
                }
            }
        }
        firstPoint=secondPoint=0;
        if(0 + (1 - 0) * rn.nextDouble() <= probability){
            while (firstPoint == secondPoint) {
                firstPoint = rn.nextInt(individual.genes_x1.length);
                secondPoint = rn.nextInt(individual.genes_x1.length - firstPoint) + firstPoint;
            }
            //System.out.println(firstPoint);
            //System.out.println(secondPoint);
            for (int i = firstPoint; i < secondPoint; i++) {
                if (individual.genes_x2[i] == 0) {
                    individual.genes_x2[i] = 1;
                }
                else {
                    individual.genes_x2[i] = 0;
                }
            }
        }
    }
}
