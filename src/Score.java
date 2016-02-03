import java.util.Observable;

/**
 * Created by rcd18 on 03/02/2016.
 */
public class Score extends Observable {

    private int score;
    private int coups;
    private static Score inst;

    public Score(){
        this.reset();
    }
    public void reset(){
        this.score = 0;
        this.coups = 0;
    }
    public String getScore(){
        return Integer.toString( this.score );
    }
    public String getCoups(){
        return Integer.toString( this.coups );
    }
    public String getRatio(){
        if(this.coups == 0) {
            return "0 %";
        }
        else{
            return Integer.toString( this.score / this.coups ) + " %";
        }
    }
    public void inc(int n) {
        this.score += (n-2)*10;
    }
    public void coup(){
        this.coups ++;
    }

    public static Score getInstance(){
        if( inst == null ){
            inst = new Score();
        }
        return inst;
    }
}
