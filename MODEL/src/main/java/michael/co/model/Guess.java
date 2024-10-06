package michael.co.model;

import java.util.concurrent.ThreadLocalRandom;

public class Guess {
    private int number;
    private int triesCounter;
    private int min;
    private int max;

    public void setNumber(int min, int max){
        number = ThreadLocalRandom.current().nextInt(min, max + 1);
        this.min = min;
        this.max = max;
    }
    public void resetTriesCount(){
        triesCounter = 0;
    }
    public int getTriesCount(){
        return triesCounter;
    }
    public int getNumber(){
        return number;
    }
    public void MakeTry(){
        triesCounter++;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public int[] repairMinMax(int low, int high){
        if (low > high){
            int temp = low;
            low = high;
            high = temp;
        }
        int[] repaired = new int[2];
        repaired[0] = low;
        repaired[1] = high;
        return repaired;
    }
}
