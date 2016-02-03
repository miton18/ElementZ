import java.util.Observable;

/**
 * Created by rcd18 on 03/02/2016.
 */
public class Score extends Observable {

    private int score;

    private static Score inst;

    public Score(){
        this.reset();
    }

    public void reset(){
        this.score = 0;
    }

    public void inc(int n) {
        this.score += (n-2)*10;
    }

    public String getScore(){
        return Integer.toString( this.score );
    }

    public static Score getInstance(){
        if( inst == null ){
            inst = new Score();
        }
        return inst;
    }
}
