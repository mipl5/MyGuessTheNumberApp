package michael.co.model;

import java.util.Random;

public class Guess {
    private int number;
    private Random rnd;

    public void setNumber(int down, int up){
        number = rnd.nextInt(up);
    }
    public boolean isCorrect(int number){
        return this.number == number;
    }
}
