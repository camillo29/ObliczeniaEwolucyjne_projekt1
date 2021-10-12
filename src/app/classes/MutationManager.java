package app.classes;

import java.util.Random;

public class MutationManager {
    Random rn = new Random();
    private double probability = 0.0f;

    public MutationManager(double probability){
        this.probability = probability;
    }

    public void mutate_edge(Individual individual){
        if(0+(1-0) * rn.nextDouble()<=probability) {
            if (individual.genes_x1[individual.genes_x1.length - 1] == 0)
                individual.genes_x1[individual.genes_x1.length - 1] = 1;
            else individual.genes_x1[individual.genes_x1.length - 1] = 0;
        }

        if(0+(1-0) * rn.nextDouble()<=probability) {
            if (individual.genes_x2[individual.genes_x2.length - 1] == 0)
                individual.genes_x2[individual.genes_x2.length - 1] = 1;
            else individual.genes_x2[individual.genes_x2.length - 1] = 0;
        }
    }

    public void mutate_onePoint(Individual individual){
        int index = 0;
        if(0+(1-0) * rn.nextDouble()<=probability) {
            index = rn.nextInt(individual.genes_x1.length);

            if (individual.genes_x1[index] == 0)
                individual.genes_x1[index] = 1;
            else individual.genes_x1[index] = 0;
        }

        if(0+(1-0) * rn.nextDouble()<=probability) {
            index = rn.nextInt(individual.genes_x2.length);
            if (individual.genes_x2[index] == 0)
                individual.genes_x2[index] = 1;
            else individual.genes_x2[index] = 0;
        }
    }

    public void mutate_twoPoints(Individual individual){
        int index1 = 0, index2 = 0;
        //X1 MUTATION
        if(0+(1-0) * rn.nextDouble()<=probability) {
            while(index1==index2) {
                index1 = rn.nextInt(individual.genes_x1.length);
                index2 = rn.nextInt(individual.genes_x1.length);
            }
            if (individual.genes_x1[index1] == 0)
                individual.genes_x1[index1] = 1;
            else individual.genes_x1[index1] = 0;
            if (individual.genes_x1[index2] == 0)
                individual.genes_x1[index2] = 1;
            else individual.genes_x1[index2] = 0;
        }
        //X2 MUTATION
        index1 = index2 = 0;
        if(0+(1-0) * rn.nextDouble()<=probability) {
            while(index1!=index2) {
                index1 = rn.nextInt(individual.genes_x1.length);
                index2 = rn.nextInt(individual.genes_x1.length);
            }
            if (individual.genes_x2[index1] == 0)
                individual.genes_x2[index1] = 1;
            else individual.genes_x2[index1] = 0;
            if (individual.genes_x2[index2] == 0)
                individual.genes_x2[index2] = 1;
            else individual.genes_x2[index2] = 0;
        }
    }
}
