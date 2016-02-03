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
        setVisible(true);
        this.setLayout(new GridBagLayout());
        // POSITION / TAILLE FENETRE
        this.setMinimumSize(new Dimension(600, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        // Panel de gauche
        GridBagConstraints leftConstrainte  = new GridBagConstraints();
        leftConstrainte.gridx   = 0;  //position plus a gauche
        leftConstrainte.weightx = 10; // nb de colonnes
        leftConstrainte.weighty = 1;
        leftConstrainte.fill    = GridBagConstraints.BOTH;
        paneLeft  = new JPanel();
        paneLeft.setBackground(Color.black);
        this.add(paneLeft,  leftConstrainte);
        paneLeft.setLayout(new GridLayout( ElementZ_model.getSize() , ElementZ_model.getSize(), -20, -20) );

        //Panel de droite
        GridBagConstraints rightConstrainte = new GridBagConstraints();
        rightConstrainte.gridx      = 1;
        rightConstrainte.weightx    = 10;
        rightConstrainte.weighty    = 1;
        rightConstrainte.fill       = GridBagConstraints.BOTH;
        paneRight = new JPanel();
        paneRight.setBackground(Color.orange);
        this.add(paneRight, rightConstrainte);

        paneRight.setLayout(new BoxLayout(paneRight, BoxLayout.Y_AXIS));
        // BOUTON START
        JButton buttonStart = new JButton("Démarer");
        paneRight.add(buttonStart);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawGrid();
            }
        });
        buttonStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ICON BOULE
        ImageIcon isenIcon  = new ImageIcon(this.getClass().getResource("boule_expo.jpg"));
        JLabel isen         = new JLabel(isenIcon);
        isen.setAlignmentX(Component.CENTER_ALIGNMENT);
        paneRight.add(isen);
        // SCORE TEXTR
        JLabel scoreText = new JLabel("Score :");
        paneRight.add(scoreText);
        // SCORE VALUE
        scoreText.setAlignmentX(Component.LEFT_ALIGNMENT);
        paneRight.add(this.score);
        this.score.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.pack();
    }

    public void setScore (int s) {
        this.score.setText( Integer.toString(s) );
        this.repaint();
    }

    public void drawGrid() {

        M = new ElementZ_model();

        for(int i = 0; i< M.matrix.length; i++) {
            for(int j = 0; j< M.matrix.length; j++) {

                grid[i][j] = new Boule(M.matrix[i][j]);

                paneLeft.add( (grid[i][j]).button );

                // ON APPUIE SUR UNE BOULE
                (grid[i][j]).button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if ((grid[i][j]).button == e.getSource()){
                                    // On a la Boule en I et J
                                    //System.out.println( grid[i][j].toString() );
                                    src.x = i;
                                    src.y = j;
                                    //System.out.println( "from " + Integer.toString(src[0]) + " " + Integer.toString(src[1]) );
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
                        int seuil   = 40;

                        //System.out.println( Integer.toString(dx) + ' ' + Integer.toString(dy) );
                        if( dxA > dyA && dyA <= seuil ){ // MOUVEMENT Horizontal
                            System.out.print("Hor");
                            if(dx > 0){
                                System.out.println(" droite");
                                dest.x = src.x;
                                dest.y = src.y + 1;
                                //System.out.println( "to " + Integer.toString(dest[0]) + " " + Integer.toString(dest[1]) );
                            }
                            else{
                                System.out.println(" gauche");
                                dest.x = src.x;
                                dest.y = src.y - 1;
                                //System.out.println( "to " + Integer.toString(dest[0]) + " " + Integer.toString(dest[1]) );
                            }
                        }
                        else if( dyA > dxA && dxA <= seuil ){ // MOUVEMENT Vertical
                            System.out.print("vert");
                            if(dy > 0){
                                System.out.println(" bas");
                                dest.x = src.x + 1;
                                dest.y = src.y;
                                //System.out.println( "to " + Integer.toString(dest[0]) + " " + Integer.toString(dest[1]) );
                            }
                            else{
                                System.out.println(" haut");
                                dest.x = src.x - 1;
                                dest.y = src.y;
                                //System.out.println( "to " + Integer.toString(dest[0]) + " " + Integer.toString(dest[1]) );
                            }
                        }
                        if( dest.x >= 0 && dest.y >= 0 && dest.x <= M.getSize() -1 && dest.y <= M.getSize() -1 ) {
                            M.permut(src.x, src.y, dest.x, dest.y);
                            updateGrid();
                            while( M.cleanGrid() ){}
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
    public void updateGrid() {

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
}
