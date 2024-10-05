package michael.co.model;

import java.util.concurrent.ThreadLocalRandom;

public class Guess {
    private int number;
    private int triesCounter;

    public void setNumber(int min, int max){
        number = ThreadLocalRandom.current().nextInt(min, max + 1);
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
}
