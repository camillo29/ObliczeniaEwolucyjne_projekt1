package app.classes;

import java.util.Random;

public class CrossOverManager {
    public Random rn = new Random();
    public CrossOverManager(){}

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
}

