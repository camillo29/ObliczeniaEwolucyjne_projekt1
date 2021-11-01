package app.classes.Managers;

import app.classes.Individual;

import java.util.Random;

public class MutationManager {
    Random rn = new Random();
    private double probability = 0.0f;

    public MutationManager(double probability){
        this.probability = probability;
    }

    public void decideMethod(Individual individual, String method){
        if(method.equals("EDGE")) mutate_edge(individual);
        else if(method.equals("ONE_POINT")) mutate_onePoint(individual);
        else mutate_twoPoints(individual);
    }

    public void mutate_edge(Individual individual){
        if(0+(1-0) * rn.nextDouble()<=probability) {
            if (individual.getGenes_x1()[individual.getGenes_x1().length - 1] == 0)
                individual.getGenes_x1()[individual.getGenes_x1().length - 1] = 1;
            else individual.getGenes_x1()[individual.getGenes_x1().length - 1] = 0;
        }

        if(0+(1-0) * rn.nextDouble()<=probability) {
            if (individual.getGenes_x2()[individual.getGenes_x2().length - 1] == 0)
                individual.getGenes_x2()[individual.getGenes_x2().length - 1] = 1;
            else individual.getGenes_x2()[individual.getGenes_x2().length - 1] = 0;
        }
    }

    public void mutate_onePoint(Individual individual){
        int index = 0;
        if(0+(1-0) * rn.nextDouble()<=probability) {
            index = rn.nextInt(individual.getGenes_x1().length);

            if (individual.getGenes_x1()[index] == 0)
                individual.getGenes_x1()[index] = 1;
            else individual.getGenes_x1()[index] = 0;
        }

        if(0+(1-0) * rn.nextDouble()<=probability) {
            index = rn.nextInt(individual.getGenes_x2().length);
            if (individual.getGenes_x2()[index] == 0)
                individual.getGenes_x2()[index] = 1;
            else individual.getGenes_x2()[index] = 0;
        }
    }

    public void mutate_twoPoints(Individual individual){
        int index1 = 0, index2 = 0;
        //X1 MUTATION
        if(0+(1-0) * rn.nextDouble()<=probability) {
            while(index1==index2) {
                index1 = rn.nextInt(individual.getGenes_x1().length);
                index2 = rn.nextInt(individual.getGenes_x1().length);
            }
            if (individual.getGenes_x1()[index1] == 0)
                individual.getGenes_x1()[index1] = 1;
            else individual.getGenes_x1()[index1] = 0;
            if (individual.getGenes_x1()[index2] == 0)
                individual.getGenes_x1()[index2] = 1;
            else individual.getGenes_x1()[index2] = 0;
        }
        //X2 MUTATION
        index1 = index2 = 0;
        if(0+(1-0) * rn.nextDouble()<=probability) {
            while(index1!=index2) {
                index1 = rn.nextInt(individual.getGenes_x1().length);
                index2 = rn.nextInt(individual.getGenes_x1().length);
            }
            if (individual.getGenes_x2()[index1] == 0)
                individual.getGenes_x2()[index1] = 1;
            else individual.getGenes_x2()[index1] = 0;
            if (individual.getGenes_x2()[index2] == 0)
                individual.getGenes_x2()[index2] = 1;
            else individual.getGenes_x2()[index2] = 0;
        }
    }
}
