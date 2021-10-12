package app.classes;
import java.math.BigInteger;
import java.util.Random;

public class Individual {
    Random rn = new Random();
    int[] genes_x1; //binary length
    int[] genes_x2; //binary length
    double decimalValue_x1 = 0;
    double decimalValue_x2 = 0;

    private Boolean elite = false;

    double y = 0;

    //for best selection
    boolean chosen = false;

    //for roulette selection
    double probability = 0.0f;
    double distributor = 0.0f;

    public Individual(int genesLength) {
        genes_x1 = new int[genesLength];
        genes_x2 = new int[genesLength];

        for(int i = 0; i< genes_x1.length; i++){
            genes_x1[i] = Math.abs(rn.nextInt() % 2);
            genes_x2[i] = Math.abs(rn.nextInt() % 2);
        }

        y = 0;
    }
    public Individual(int[] x1, int[] x2){
        genes_x1 = x1;
        genes_x2 = x2;
        y=0;
    }

    public void calculateDecimalValue(int rangeA, int rangeB){
        String binaryValue_x1 = new String();
        String binaryValue_x2 = new String();
        //System.out.println(Integer.parseInt("1010", 2));

        for(int i=genes_x1.length-1;i>=0; i--){
            binaryValue_x1+=genes_x1[i];
            binaryValue_x2+=genes_x2[i];
        }
        long x1 = Long.parseLong(binaryValue_x1, 2);
        long x2 = Long.parseLong(binaryValue_x2, 2);

        //System.out.println(x1.intValue());
        //System.out.println(x1.byteValue());
        decimalValue_x1 = rangeA + x1 * (rangeB-rangeA) / (Math.pow(2, genes_x1.length) - 1);
        decimalValue_x2 = rangeA + x2 * (rangeB-rangeA) / (Math.pow(2, genes_x2.length) - 1);
    }

    public void setY(double value){
        y = value;
    }

    public int[] getGenes_x1() {
        return genes_x1;
    }

    public void setGenes_x1(int[] genes_x1) {
        this.genes_x1 = genes_x1;
    }

    public int[] getGenes_x2() {
        return genes_x2;
    }

    public void setGenes_x2(int[] genes_x2) {
        this.genes_x2 = genes_x2;
    }

    public double getDecimalValue_x1() {
        return decimalValue_x1;
    }

    public void setDecimalValue_x1(double decimalValue_x1) {
        this.decimalValue_x1 = decimalValue_x1;
    }

    public double getDecimalValue_x2() {
        return decimalValue_x2;
    }

    public void setDecimalValue_x2(double decimalValue_x2) {
        this.decimalValue_x2 = decimalValue_x2;
    }

    public double getY(){
        return y;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getDistributor() {
        return distributor;
    }

    public void setDistributor(double distributor) {
        this.distributor = distributor;
    }
}
