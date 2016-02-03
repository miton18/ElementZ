import java.util.Random;

/**
 * Created by rcd18 on 27/01/2016.
 */
public class ElementZ_model {

    private static int  size    = 8;
    public int[][]      matrix  = new int[size][size];
    private boolean[][] matrixb = new boolean[size][size];
    private int         score;

    public ElementZ_model(){
        initMatrixb();
        printMatrix();
    }
    public ElementZ_model(int s){
        initMatrixb();
        printMatrix();
        this.setSize(s);
    }

    private void initMatrixb() {

        this.score = 0;

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
    public void detectLine(int n) {
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<(this.size -n+1); j++) {
                boolean lineOk = true;
                int first = matrix[i][j];
                int k = j;
                while(k<j+n && lineOk) {
                    if (matrix[i][k] != first && matrix[i][k]!=0) lineOk = false;
                    k++;
                }
                if (lineOk) {
                    for (k=j; k<(j+n);k++) matrixb[i][k]=true;
                    j+=n+1;
                    (Score.getInstance()).inc(n); // envoi la famille pour comptabilisation
                }
            }
        }
    }

    /**
     * Detecte et supprime les familles de 4 boules ou plus en colonne
     */
    public void detectCol(int n) {
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<(this.size-n+1); j++) {
                boolean lineOk = true;
                int first = matrix[j][i];
                int k = j;
                while(k<j+n && lineOk) {
                    if (matrix[k][i] != first && matrix[k][i]!=0) lineOk = false;
                    k++;
                }
                if (lineOk) {
                    for (k=j; k<(j+n);k++) matrixb[k][i]=true;
                    j+=n+1;
                    (Score.getInstance()).inc(n); // envoi la famille pour comptabilisation
                }
            }
        }
    }

    /**
     * Remplace les boules à 'true' par 0
     */
    public boolean delete_famille() {
        boolean ret = false;

        for(int i=0; i<this.size; i++) {
            for(int j=0; j<this.size; j++) {
                if( matrixb[i][j] ) {
                    matrix[i][j] = 0;
                    matrixb[i][j] = false;
                    ret = true;
                }
            }
        }
        return ret;
    }

    public void detect() {

        for (int i=(this.size)-1; i>2; i--) {
            detectCol(i);
            detectLine(i);
        }
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

    public void remplir() {
        for(int i=0; i<this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if( matrix[i][j] == 0 ) {
                    matrix[i][j] = randomBall();
                }
            }
        }
    }

    public boolean hasToClean(){
        boolean ret = false;
        for(int i=0; i<this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if( matrixb[i][j] ) {
                    ret = true;
                }
            }
        }
        return ret;
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
