import java.util.Random;

/**
 * Created by rcd18 on 27/01/2016.
 */
public class ElementZ_model {

    private static int  size    = 8;
    public int[][]      matrix  = new int[size][size];
    private boolean[][] matrixb = new boolean[size][size];
    private int         score   = 0;

    public ElementZ_model(){
        initMatrixb();
        printMatrix();

    }

    private void initMatrixb() {
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<this.size; j++) {
                if(matrix[i][j] == 0) {
                    matrix[i][j] = randomBall();
                }
            }
        }
    }

    /**
     * Detecte et supprime les familles de 4 boules ou plus en ligne
     */
    private boolean detect_ligne() {

        for( int i=0; i<this.size; i++ ) {

            int count = 0;

            for( int j=0; j< this.size; j++ ) { // parcours de la grille

                int x = 0;
                while(matrix[i][j] == matrix[i][j+x] ){
                    count ++;
                    x ++;
                }
                if( count >= 4 ) {
                    for( int v=0; v<count; v++ ) {
                        matrixb[i][j+v] = true;
                    }
                    delete_famille();
                    gravity();
                    return true;
                }
                count = 0;
            }
        }
        return false;
    }

    /**
     * Detecte et supprime les familles de 4 boules ou plus en colonne
     */
    private boolean detect_column() {

        for( int i=0; i<this.size; i++ ) {

            int count = 0;

            for( int j=0; j< this.size; j++ ) { // parcours de la grille

                int x = 0;
                while(matrix[j][i] == matrix[j][i+x] ){
                    count ++;
                    x ++;
                }
                if( count >= 4 ) { // on a une bonne famille
                    for( int v=0; v<count; v++ ) {
                        matrixb[j][i+v] = true;
                    }
                    delete_famille(); // on enleve
                    gravity(); // on fait descendre
                    return true;
                }
                count = 0;
            }
        }
        return false;
    }

    /**
     * Remplace les boules à 'true' par 0
     */
    private void delete_famille() {
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<this.size; j++) {
                if( matrixb[i][j] ) {
                    matrix[i][j] = 0;
                    matrixb[i][j] = false;
                }
            }
        }
    }

    public boolean cleanGrid() {
        detect_ligne();
        detect_column();
        delete_famille();
        return false;
    }

    /**
     * Nombre random entre 1 et 6
     * @return nombre aléatoire
     */
    private int randomBall() {
        Random r = new Random();
        //int valeur = valeurMin + r.nextInt(valeurMax - valeurMin)
        return 1 + r.nextInt(6 - 1);
    }

    /**
     * Affiche la grille de boule en console
     */
    public void printMatrix(){
        for(int i=0; i<this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print( Integer.toString(matrix[i][j]) + ' ' );
            }
            System.out.println();
        }
    }

    public void gravity() {
        for(int j=0; j<this.size; j++) {

            int k = this.size - 1;

            for(int i=this.size - 1; i>=0; i--) {
                if (matrix[i][j] != 0) {
                    matrix[k][j] = matrix[i][j];
                    k--;
                }
            }
            for(int i=k; i>=0; i--) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * Pemute 2 boules
     * @param i position boule actuel
     * @param j position boule actuel
     * @param ip position boule désiré
     * @param jp position boule désiré
     */
    public void permut(int i, int j, int ip, int jp) {
        if( ((i==ip) && (j==(jp+1)))
                ||
                ((i==ip) && (j==(jp-1)))
                ||
                ((j==jp) && (i==(ip+1)))
                ||
                ((j==jp) && (i==(ip-1)))
                ) {
            if( matrix[ip][jp] >= 0 && matrix[ip][jp] <= this.size ){
                int s = matrix[i][j];
                matrix[i][j] = matrix[ip][jp];
                matrix[ip][jp] = s;
            }
        }
        this.printMatrix();
    }


    /**
     * Renvoie la grille de boule
     * @return
     */
    public int[][] getMatrix() {
        return matrix;
    }
    /**
     * Retourne la taille de la grille
     * @return
     */
    public static int getSize() {
        return size;
    }
    /**
     * Modifie la taille de la grille
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }
}
