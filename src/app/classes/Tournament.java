package app.classes;

import java.util.LinkedList;

public class Tournament {
    LinkedList<Individual> populationForSelection = new LinkedList<>();
    Individual winner;
    private int maxPop;

    public Tournament(int maxPop){
        this.maxPop = maxPop;
    }

    public void determineWinner(String mode){
        Individual best = populationForSelection.getFirst();
        if(mode == "min") {
            for (Individual i : populationForSelection) {
                if (i.getY() < best.getY())
                    best = i;
            }
        } else {
            for (Individual i : populationForSelection) {
                if (i.getY() > best.getY())
                    best = i;
            }
        }
        winner = best;
    }

    public Individual getWinner(){
        return winner;
    }

    public int getPopulationSize(){
        return populationForSelection.size();
    }
    public void addIndividual(Individual individual){
        populationForSelection.add(individual);
    }

    public int getMaxPop() {
        return maxPop;
    }
}
