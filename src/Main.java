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

        ElementZ_model M = new ElementZ_model();

        Window w = new Window();

        w.drawGrid( M.getMatrix() );

        System.out.println("Waiting...");
    }
}
