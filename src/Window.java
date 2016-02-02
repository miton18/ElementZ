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
    private Boule[][] grid = new Boule[8][8];

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

        //Panel de droite
        GridBagConstraints rightConstrainte = new GridBagConstraints();
        rightConstrainte.gridx      = 1;
        rightConstrainte.weightx    = 5;
        rightConstrainte.weighty    = 1;
        rightConstrainte.fill       = GridBagConstraints.BOTH;
        paneRight = new JPanel();
        paneRight.setBackground(Color.orange);
        this.add(paneRight, rightConstrainte);

        paneRight.setLayout(new BoxLayout(paneRight, BoxLayout.Y_AXIS));
        // BOUTON START
        JButton buttonStart = new JButton("Démarer");
        paneRight.add(buttonStart);
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

    public void drawGrid(int[][] matrix) {
        paneLeft.setLayout(new GridLayout( ElementZ_model.getSize() , ElementZ_model.getSize(), 8, 8) );

        for(int i = 0; i< matrix.length; i++) {
            for(int j = 0; j< matrix.length; j++) {

                grid[i][j] = new Boule(matrix[i][j]);

                paneLeft.add( (grid[i][j]).button );

                // ON APPUIE SUR UNE BOULE
                (grid[i][j]).button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if ((grid[i][j]).button == e.getSource()){
                                    // On a la Boule en I et J
                                    System.out.println( grid[i][j].toString() );
                                }

                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        System.out.println( Integer.toString(e.getX()) + ' ' + Integer.toString(e.getY()) );
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
        }
        this.pack();
    }
    public void updateGrid(int[][] matrix) {

        for(int i = 0; i< matrix.length; i++) {
            for(int j = 0; j< matrix.length; j++) {

                if( (grid[i][j]).color != matrix[i][j] ){
                    (grid[i][j]).setColor( matrix[i][j] );
                }
            }
        }
    }
}
