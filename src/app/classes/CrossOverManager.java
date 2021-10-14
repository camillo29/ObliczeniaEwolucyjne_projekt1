package app.classes;

import java.util.Random;

public class CrossOverManager {
    public Random rn = new Random();
    private int firstCrossOverPoint = 0;
    private int secondCrossOverPoint = 0;
    private int thirdCrossOverPoint = 0;
    public CrossOverManager(){}

    public int[] decideMethod(int[] firstIndividualX, int[] secondIndividualX, boolean generatePoint, String method){
        if(method.equals("ONE_POINT"))
            return crossOver_onePoint(firstIndividualX, secondIndividualX, generatePoint);
        else if(method.equals("TWO_POINTS"))
            return crossOver_twoPoints(firstIndividualX, secondIndividualX, generatePoint);
        else if(method.equals("THREE_POINTS"))
            return crossOver_threePoints(firstIndividualX, secondIndividualX, generatePoint);
        else return crossOver_uniform(firstIndividualX, secondIndividualX);
    }

    public int[] crossOver_onePoint(int[] firstIndividualX, int[] secondIndividualX, boolean generatePoint){
        int[] newGenesX = new int[firstIndividualX.length];
        int crossOverPoint = firstCrossOverPoint;
        if(generatePoint) {
            crossOverPoint = rn.nextInt(firstIndividualX.length);
            firstCrossOverPoint = crossOverPoint;
        }
        firstCrossOverPoint = crossOverPoint;
        //System.out.println(crossOverPoint);
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=crossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }
        return newGenesX;
    }

    public int[] crossOver_twoPoints(int[] firstIndividualX, int[] secondIndividualX, boolean generatePoints){
        int[] newGenesX = new int[firstIndividualX.length];
        int firstCrossOverPoint = this.firstCrossOverPoint;
        int secondCrossOverPoint = this.secondCrossOverPoint;
        if(generatePoints){
            firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
            secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
            this.firstCrossOverPoint = firstCrossOverPoint;
            this.secondCrossOverPoint = secondCrossOverPoint;
        }

        //System.out.println("first = "+firstCrossOverPoint);
        //System.out.println("second = "+secondCrossOverPoint);
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=firstCrossOverPoint && i<secondCrossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }

        return newGenesX;
    }

    public int[] crossOver_threePoints(int[] firstIndividualX, int[] secondIndividualX, boolean generatePoints){
        int[] newGenesX = new int[firstIndividualX.length];
        int firstCrossOverPoint = this.firstCrossOverPoint;
        int secondCrossOverPoint = this.secondCrossOverPoint;
        int thirdCrossOverPoint = this.thirdCrossOverPoint;
        if(generatePoints){
            firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
            secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
            thirdCrossOverPoint = rn.nextInt(firstIndividualX.length - secondCrossOverPoint) + secondCrossOverPoint; //random.nextInt(max - min) + min;
            this.firstCrossOverPoint = firstCrossOverPoint;
            this.secondCrossOverPoint = secondCrossOverPoint;
            this.thirdCrossOverPoint = thirdCrossOverPoint;
        }
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=firstCrossOverPoint && i<secondCrossOverPoint || i>=thirdCrossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }

        return newGenesX;
    }

    public int[] crossOver_uniform(int[] firstIndividualX, int[] secondIndividualX){
        int[] newGenesX = new int[firstIndividualX.length];
        int random = 0;

        for(int i = 0; i<firstIndividualX.length; i++){
            random = Math.abs(rn.nextInt() % 2);
            if(random == 1)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];

        }

        return newGenesX;
    }
}


/*
public int[] crossOver_onePoint(int[] firstIndividualX, int[] secondIndividualX){
        int[] newGenesX = new int[firstIndividualX.length];
        int crossOverPoint = rn.nextInt(firstIndividualX.length);
        //System.out.println(crossOverPoint);
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=crossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }
        return newGenesX;
    }

    public int[] crossOver_twoPoints(int[] firstIndividualX, int[] secondIndividualX){
        int[] newGenesX = new int[firstIndividualX.length];
        int firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
        int secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
        //System.out.println("first = "+firstCrossOverPoint);
        //System.out.println("second = "+secondCrossOverPoint);
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=firstCrossOverPoint && i<secondCrossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }

        return newGenesX;
    }

    public int[] crossOver_threePoints(int[] firstIndividualX, int[] secondIndividualX){
        int[] newGenesX = new int[firstIndividualX.length];
        int firstCrossOverPoint = rn.nextInt(firstIndividualX.length);
        int secondCrossOverPoint = rn.nextInt(firstIndividualX.length - firstCrossOverPoint) + firstCrossOverPoint; //random.nextInt(max - min) + min;
        int thirdCrossOverPoint = rn.nextInt(firstIndividualX.length - secondCrossOverPoint) + secondCrossOverPoint; //random.nextInt(max - min) + min;
        //System.out.println("first = "+firstCrossOverPoint);
       //System.out.println("second = "+secondCrossOverPoint);
        //System.out.println("third = "+thirdCrossOverPoint);
        for(int i = 0; i<firstIndividualX.length;i++){
            if(i>=firstCrossOverPoint && i<secondCrossOverPoint || i>=thirdCrossOverPoint)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];
        }

        return newGenesX;
    }

    public int[] crossOver_uniform(int[] firstIndividualX, int[] secondIndividualX){
        int[] newGenesX = new int[firstIndividualX.length];
        int random = 0;

        for(int i = 0; i<firstIndividualX.length; i++){
            random = Math.abs(rn.nextInt() % 2);
            if(random == 1)
                newGenesX[i] = secondIndividualX[i];
            else newGenesX[i] = firstIndividualX[i];

        }

        return newGenesX;
    }
 */

