import java.io.File;

/**
 * Created by rcd18 on 27/01/2016.
 */
public class Main {

    /**
     * Entrypoint
     * @param args
     */
    public static void main( String[] args) {

        System.out.println("Starting...");
        File file = new File("boule 0.jpg");

        System.out.println( file.getAbsolutePath() );

        Window w = new Window();

        w.drawGrid();

        System.out.println("Waiting...");
    }
}
