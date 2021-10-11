package app.classes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Population {
    Random rn = new Random();
    int popSize;
    LinkedList<Individual> individuals;
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

    /*public double fitnessFunction(double x1, double x2){
        return 2 * (x1*x1) + x2 + 5;
    }*/

    public double fitnessFunction(double x1, double x2){
        return (Math.pow((1.5- x1 + x1*x2), 2) + Math.pow((2.25 - x1+x1*x2*x2), 2) + Math.pow((2.625 - x1 + x1*Math.pow(x2, 3)), 2));
    }

    public void calculateIndividualsDecimalValue(int rangeA, int rangeB){
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

    //SELECTION USING "BEST" STRATEGY
    public void bestSelection(String mode) {
        //int individualsToSelect = (int) (individuals.size() * 0.3);
        int individualsToSelect = bestAndTourneyIndividualsAmount;
        System.out.println(individualsToSelect);
        LinkedList<Individual> listOfBest = new LinkedList<Individual>();
        Individual bestIndividual = individuals.getFirst();

        //SELECTING BEST INDIVIDUALS
        for (int i = 0; i < individualsToSelect; i++) {
            for (Individual j : individuals) {
                if(mode == "min") {
                    if (bestIndividual.isChosen() || j.getY() < bestIndividual.getY())
                        bestIndividual = j;
                }
                else {
                    if (bestIndividual.isChosen() || j.getY() > bestIndividual.getY())
                        bestIndividual = j;
                    }
            }
            listOfBest.add(bestIndividual);
            individuals.remove(bestIndividual);
            bestIndividual.setChosen(true);
            }


        for(Individual i: listOfBest)
            i.setChosen(false);

        individuals = listOfBest;

        System.out.println("["+mode+"] AFTER BEST SELECTION");
        for(Individual i: individuals){
            System.out.println();
            System.out.println("x1 = " + i.getDecimalValue_x1() + "|" + " x2 = " + i.getDecimalValue_x2());
            System.out.println("y = " + i.getY());
        }



    }

    //SELECTION USING "TOURNAMENT" STRATEGY
    public void tournamentSelection(String mode) {
        int amountOfTournaments = (int)individuals.size()/bestAndTourneyIndividualsAmount;
        LinkedList<Tournament> tournaments = new LinkedList<Tournament>();

        //Creating tournaments
        for(int i = 0; i<amountOfTournaments; i++){
            tournaments.add(new Tournament());
        }

        //Individuals assignment to tournaments
        boolean assigned = false;
        int index = 0;
        for(Individual i: individuals){
            assigned = false;
            while(!assigned){
                index = rn.nextInt(tournaments.size());
                if(tournaments.get(index).getPopulationSize() < bestAndTourneyIndividualsAmount){
                    tournaments.get(index).addIndividual(i);
                    assigned=true;
                }
            }
        }
        for(Tournament t: tournaments){
            System.out.println("pop size in tournament: " + t.getPopulationSize());
        }
        //Determining winners
        LinkedList<Individual> winners = new LinkedList<Individual>();
        for(Tournament t: tournaments){
            t.determineWinner(mode);
            winners.add(t.getWinner());
        }
        individuals = winners;

        //DEBUG
        System.out.println("["+mode+"] AFTER TOURNAMENT SELECTION");
        for(Individual i: individuals){
            System.out.println();
            System.out.println("x1 = " + i.getDecimalValue_x1() + "|" + "x2 = " + i.getDecimalValue_x2());
            System.out.println("y = " + i.getY());
        }

    }

    //SELECTION USING "ROULETTE" STRATEGY
    public void rouletteSelection(String mode){
        LinkedList<Individual> selected = new LinkedList<>();
        double sum = 0.0f;
        if(mode == "min") {
            for (Individual i : individuals) {
                sum += 1/i.getY();
            }
        } else {
            for (Individual i : individuals) {
                sum += i.getY();
            }
        }
        double distributor = 0.0f;
        for(Individual i: individuals){
            if(mode == "min") {
                i.setProbability((1/ i.getY()) / sum);
            }
            else{
                i.setProbability(i.getY() / sum);
            }
            i.setDistributor(distributor + i.getProbability());
            distributor = i.getDistributor();
            System.out.println("y = " + i.getY() + " distributor = " + i.getDistributor());
        }
        double random = 0.0f;
        mainFor: for(int i = 0; i<bestAndTourneyIndividualsAmount; i++){     //<- i<bestAndTourneyIndividualsAmount TO CHANGE
            random = 0+(1-0) * rn.nextDouble();
            for(int j = 0; j<individuals.size(); j++){
                if(random < individuals.get(j).getDistributor()){
                    selected.add(individuals.get(j));
                    System.out.println("random = " + random + " distributor = " + individuals.get(j).getDistributor());
                    System.out.println("x1 = " + individuals.get(j).getDecimalValue_x1() + "x2 = " + individuals.get(j).getDecimalValue_x2());
                    System.out.println("y = " + individuals.get(j).getY());
                    continue mainFor;
                }
            }
        }

        //DUPLICATE REMOVAL
        selected = new LinkedList<>(new HashSet<>(selected));

        individuals = selected;

        //DEBUG
        System.out.println("["+mode+"] AFTER ROULETTE SELECTION");
        for(Individual i: individuals){
            System.out.println();
            System.out.println("x1 = " + i.getDecimalValue_x1() + "|" + "x2 = " + i.getDecimalValue_x2());
            System.out.println("y = " + i.getY());
        }

    }

    public Individual crossOver_onePoint(int[] firstIndividualX, int[] secondIndividualX){
        int[] tmpX1 = firstIndividualX;
        int[] tmpX2 = secondIndividualX;
        int crossOverPoint = rn.nextInt(firstIndividualX.length);

        /*//DEBUG
        System.out.println("BEFORE");
        System.out.println("CrossoverPoint: " + crossOverPoint);
        String x1 = "", x2 = "";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("First individual x:" + x1 + "| Second individual x:" +x2);*/

        for(int i = crossOverPoint; i<firstIndividualX.length;i++){
            int tmp = tmpX1[i];
            tmpX1[i] = tmpX2[i];
            tmpX2[i] = tmp;
        }
        /*//DEBUG
        x1=x2="";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("AFTER");
        System.out.println("First individual x:" + x1 + "| Second individual x:" + x2);*/

        return new Individual(tmpX1, tmpX2);

    }

    public Individual crossOver_twoPoints(int[] firstIndividualX, int[] secondIndividualX){
        int[] tmpX1 = firstIndividualX;
        int[] tmpX2 = secondIndividualX;
        int firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
        int secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
        //random = 0+(1-0) * rn.nextDouble();

        /*//DEBUG
        System.out.println("BEFORE");
        System.out.println("firstCrossoverPoint: " + firstCrossOverPoint);
        System.out.println("secondCrossoverPoint: " + secondCrossOverPoint);
        String x1 = "", x2 = "";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("First individual x:" + x1 + "| Second individual x:" +x2);*/

        for(int i = firstCrossOverPoint; i<secondCrossOverPoint;i++){
            int tmp = tmpX1[i];
            tmpX1[i] = tmpX2[i];
            tmpX2[i] = tmp;
        }

        /*//DEBUG
        x1=x2="";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("AFTER");
        System.out.println("First individual x:" + x1 + "| Second individual x:" + x2);*/
        return new Individual(tmpX1, tmpX2);
    }

    public Individual crossOver_threePoints(int[] firstIndividualX, int[] secondIndividualX){
        int[] tmpX1 = firstIndividualX;
        int[] tmpX2 = secondIndividualX;
        int firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
        int secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
        int thirdCrossOverPoint = rn.nextInt(firstIndividualX.length - secondCrossOverPoint) + secondCrossOverPoint; //random.nextInt(max - min) + min;
        //random = 0+(1-0) * rn.nextDouble();

        //DEBUG
        System.out.println("BEFORE");
        System.out.println("firstCrossoverPoint: " + firstCrossOverPoint);
        System.out.println("secondCrossoverPoint: " + secondCrossOverPoint);
        System.out.println("thirdCrossoverPoint: " + thirdCrossOverPoint);
        String x1 = "", x2 = "";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("First individual x:" + x1 + "| Second individual x:" +x2);

        for(int i = firstCrossOverPoint; i<secondCrossOverPoint;i++){
            int tmp = tmpX1[i];
            tmpX1[i] = tmpX2[i];
            tmpX2[i] = tmp;
        }
        for(int i = thirdCrossOverPoint; i<firstIndividualX.length;i++){
            int tmp = tmpX1[i];
            tmpX1[i] = tmpX2[i];
            tmpX2[i] = tmp;
        }

        //DEBUG
        x1=x2="";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("AFTER");
        System.out.println("First individual x:" + x1 + "| Second individual x:" + x2);
        return new Individual(tmpX1, tmpX2);
    }

    public Individual crossOver_uniform(int[] firstIndividualX, int[] secondIndividualX){
        int[] tmpX1 = firstIndividualX;
        int[] tmpX2 = secondIndividualX;
        int random = 0;
        //DEBUG
        System.out.println("BEFORE");
        String x1 = "", x2 = "";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("First individual x:" + x1 + "| Second individual x:" +x2);
        //DEBUG END
        for(int i = 0; i<firstIndividualX.length; i++){
            random = Math.abs(rn.nextInt() % 2);
            if(random == 1){
                int tmp = tmpX1[i];
                tmpX1[i] = tmpX2[i];
                tmpX2[i] = tmp;
            }
        }
        //DEBUG
        x1=x2="";
        for(int i = 0; i<firstIndividualX.length; i++){
            x1+=firstIndividualX[i];
            x2+=secondIndividualX[i];
        }
        System.out.println("AFTER");
        System.out.println("First individual x:" + x1 + "| Second individual x:" + x2);

        return new Individual(tmpX1, tmpX2);
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
