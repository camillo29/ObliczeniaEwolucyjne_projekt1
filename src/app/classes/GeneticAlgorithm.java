package app.classes;



public class GeneticAlgorithm {
    private int generation = 0;
    private int maxGenerations = 0;
    private int rangeA, rangeB = 0;
    private int initialPopulationSize = 0;
    private int bestAndTourneyIndividualsAmount = 0;
    private Population population;

    public GeneticAlgorithm(int rangeA, int rangeB, int maxGenerations, int initialPopulationSize, int geneLength, int bestAndTourneyIndividualsAmount ){
        this.rangeA = rangeA;
        this.rangeB = rangeB;
        this.maxGenerations = maxGenerations;
        this.initialPopulationSize = initialPopulationSize;
        this.bestAndTourneyIndividualsAmount = bestAndTourneyIndividualsAmount;
        population = new Population(initialPopulationSize, geneLength, bestAndTourneyIndividualsAmount);
    }



    public void mainLoop(){
        population.calculateIndividualsDecimalValue(rangeA, rangeB);
        population.calculateIndividualsY();

        population.debug();
        //population.bestSelection("min");
        //population.tournamentSelection("min");
        //population.rouletteSelection("min");
        int[] test1 = {1,1,0,1,0,1,1,0,0,0};
        int[] test2 = {1,0,1,1,1,0,0,1,1,0};
        //population.crossOver_onePoint(test1, test2);
        //population.crossOver_twoPoints(test1, test2);
        //population.crossOver_threePoints(population.individuals.get(0).genes_x1, population.individuals.get(1).genes_x1);
        Individual individual = population.crossOver_uniform(test1, test2);

        //Debug
        String s1 = "", s2 = "";
        for(int i = 0;i<individual.getGenes_x1().length; i++){
            s1+=individual.getGenes_x1()[i];
            s2+=individual.getGenes_x2()[i];
        }
        System.out.println("x1 = "+s1 + " | x2 = " + s2);

        population.addIndividualToPopulation(individual);

    }




}
