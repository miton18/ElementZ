import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by rcd18 on 27/01/2016.
 */
public class Window extends JFrame{

    JLabel score = new JLabel("0");
    JPanel paneLeft;
    JPanel paneRight;
    private ElementZ_model M;
    private Boule[][] grid = new Boule[8][8];
    private Pos src = new Pos();
    private Pos dest=  new Pos();

    /**
     * Constructor
     */
    public Window() {

        super();
        setVisible( true );
        this.setLayout( new GridBagLayout() );

        // POSITION / TAILLE FENETRE
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        // PANEL DE GAUCHE
        GridBagConstraints leftConstrainte  = new GridBagConstraints();
        leftConstrainte.gridx   = 0;  //position plus a gauche
        leftConstrainte.weightx = 10; // nb de colonnes
        leftConstrainte.weighty = 1;
        leftConstrainte.fill    = GridBagConstraints.BOTH;
        paneLeft                = new JPanel();
        paneLeft.setBackground( Color.black );
        paneLeft.setLayout( new GridLayout( ElementZ_model.getSize() , ElementZ_model.getSize(), -20, -20) );
        this.add( paneLeft,  leftConstrainte );

        // PANEL DE DROITE
        GridBagConstraints rightConstrainte = new GridBagConstraints();
        rightConstrainte.gridx              = 1;
        rightConstrainte.weightx            = 10;
        rightConstrainte.weighty            = 10;
        rightConstrainte.fill               = GridBagConstraints.BOTH;
        paneRight                           = new JPanel();
        paneRight.setBackground( Color.orange );
        paneRight.setLayout( new BoxLayout(paneRight, BoxLayout.Y_AXIS) );
        this.add( paneRight, rightConstrainte );

        // BOUTON START
        JButton buttonStart = new JButton("Démarer");
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawGrid();
            }
        });
        buttonStart.setAlignmentX( Component.CENTER_ALIGNMENT );
        //buttonStart.
        paneRight.add( buttonStart );

        // ICON BOULE
        ImageIcon isenIcon  = new ImageIcon( this.getClass().getResource("boule_expo.jpg") );
        JLabel isen         = new JLabel( isenIcon );
        isen.setAlignmentX( Component.CENTER_ALIGNMENT );
        paneRight.add(isen);

        // SCORE TEXT
        JLabel scoreText = new JLabel("Score :");
        paneRight.add(scoreText);

        // SCORE VALUE
        paneRight.add(this.score);

        this.pack();
    }

    public void drawGrid() {

        paneLeft.removeAll(); // enleve les anciennes boules
        M = new ElementZ_model();
        do {                  // Enleve les familles au démarrage
            M.delete_famille();
            M.gravity();   // Fait descendre les boules
            M.remplir();   // comble les trous
            M.detect(); // marque en booleen les familles
        }while( M.hasToClean() );
        Score.getInstance().reset(); // Reset le score
        updateScore();

        for(int i = 0; i< M.matrix.length; i++) {
            for(int j = 0; j< M.matrix.length; j++) {

                grid[i][j] = new Boule( M.matrix[i][j] );
                paneLeft.add( (grid[i][j]).button );

                // ON APPUIE SUR UNE BOULE
                (grid[i][j]).button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < M.getSize(); i++) {
                            for (int j = 0; j < M.getSize(); j++) {
                                if ((grid[i][j]).button == e.getSource()){
                                    // On a la Boule en I et J
                                    src.x = i;
                                    src.y = j;
                                }
                            }
                        }
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        int dx      = e.getX() -42;
                        int dy      = e.getY() -32;
                        int dxA     = Math.abs(dx) ;
                        int dyA     = Math.abs(dy);
                        int seuil   = 40; // seul de detection du mouvement
                        dest.x = -1;
                        dest.y = -1;

                        if( dxA > dyA && dyA <= seuil ){ // MOUVEMENT Horizontal
                            if(dx > 0){
                                dest.x = src.x;
                                dest.y = src.y + 1;
                            }
                            else{
                                dest.x = src.x;
                                dest.y = src.y - 1;
                            }
                        }
                        else if( dyA > dxA && dxA <= seuil ){ // MOUVEMENT Vertical
                            if(dy > 0){
                                dest.x = src.x + 1;
                                dest.y = src.y;
                            }
                            else{
                                dest.x = src.x - 1;
                                dest.y = src.y;
                            }
                        }
                        if( dest.x >= 0 && dest.y >= 0 && dest.x <= M.getSize() -1 && dest.y <= M.getSize() -1 ) {

                            M.permut(src.x, src.y, dest.x, dest.y);
                            updateGrid();
                            M.detect(); // marque en booleen les familles

                            do {
                                M.delete_famille();
                                M.gravity();   // Fait descendre les boules
                                M.remplir();   // comble les trous
                                M.detect(); // marque en booleen les familles
                            }while( M.hasToClean() );

                            updateGrid();
                            updateScore();
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }
        this.pack();
    }

    /**
     * Met a jour l'affichage en changant la couleur des boutons
     */
    private void updateGrid() {

        for(int i = 0; i< M.matrix.length; i++) {
            for(int j = 0; j< M.matrix.length; j++) {

                if( (grid[i][j]).color != M.matrix[i][j] ){
                    (grid[i][j]).setColor( M.matrix[i][j] );
                    //System.out.println("color updated : " + (grid[i][j]).bouleIcon );
                }
            }
        }
        paneLeft.updateUI();
    }
    private void updateScore(){
        this.score.setText( (Score.getInstance()).getScore() );
        this.score.updateUI();
    }
}
