package app.classes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class SelectionManager {
    private Random rn = new Random();
    private int bestAndTourneyIndividualsAmount;

    public SelectionManager(int bestAndBestAndTourneyIndividualsAmount){
        this.bestAndTourneyIndividualsAmount = bestAndBestAndTourneyIndividualsAmount;
    }

    public LinkedList<Individual> decideMethod(String mode, LinkedList<Individual> individuals, String method){
        if(method.equals("BEST")) return bestSelection(mode, individuals);
        else if(method.equals("TOURNAMENT")) return tournamentSelection(mode, individuals);
        else return rouletteSelection(mode, individuals);
    }

    //SELECTION USING "BEST" STRATEGY
    public LinkedList<Individual> bestSelection(String mode, LinkedList<Individual> individuals) {
        //int individualsToSelect = (int) (individuals.size() * 0.3);
        int individualsToSelect = bestAndTourneyIndividualsAmount;
        //System.out.println(individualsToSelect);
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

        return listOfBest;
    }

    //SELECTION USING "TOURNAMENT" STRATEGY
    public LinkedList<Individual> tournamentSelection(String mode, LinkedList<Individual> individuals) {
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
        //for(Tournament t: tournaments){
        //    System.out.println("pop size in tournament: " + t.getPopulationSize());
        //}
        //Determining winners
        LinkedList<Individual> winners = new LinkedList<Individual>();
        for(Tournament t: tournaments){
            t.determineWinner(mode);
            winners.add(t.getWinner());
        }
        return winners;

    }

    //SELECTION USING "ROULETTE" STRATEGY
    public LinkedList<Individual> rouletteSelection(String mode, LinkedList<Individual> individuals){
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
            //System.out.println("y = " + i.getY() + " distributor = " + i.getDistributor());
        }
        double random = 0.0f;
        mainFor: for(int i = 0; i<bestAndTourneyIndividualsAmount; i++){     //<- i<bestAndTourneyIndividualsAmount TO CHANGE
            random = 0+(1-0) * rn.nextDouble();
            for(int j = 0; j<individuals.size(); j++){
                if(random < individuals.get(j).getDistributor()){
                    selected.add(individuals.get(j));
                    //System.out.println("random = " + random + " distributor = " + individuals.get(j).getDistributor());
                    //System.out.println("x1 = " + individuals.get(j).getDecimalValue_x1() + "x2 = " + individuals.get(j).getDecimalValue_x2());
                    //System.out.println("y = " + individuals.get(j).getY());
                    continue mainFor;
                }
            }
        }

        //DUPLICATE REMOVAL
        selected = new LinkedList<>(new HashSet<>(selected));

        return selected;

    }
}
