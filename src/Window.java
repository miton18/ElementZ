import javax.swing.*;
import java.awt.*;

/**
 * Created by rcd18 on 27/01/2016.
 */
public class Window extends JFrame{


    JLabel score = new JLabel("0");
    JPanel paneLeft;
    JPanel paneRight;

    /**
     * Constructor
     */
    public Window() {

        super();
        setVisible(true);
        this.setLayout(new GridBagLayout());

        this.setMinimumSize(new Dimension(500, 600));

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
    }

    public void drawGrid(int[][] matrix) {
        paneLeft.setLayout(new GridLayout( ElementZ_model.getSize() , ElementZ_model.getSize(), 8, 8) );

        for(int i = 0; i< matrix.length; i++) {
            for(int j = 0; j< matrix.length; j++) {

                Boule tmp = new Boule(matrix[i][j]);
                paneLeft.add( tmp.label );
            }
        }
        this.pack();
    }
    public void updateGrid(int[][] matrix) {

        for(int i = 0; i< matrix.length; i++) {
            for(int j = 0; j< matrix.length; j++) {

                JLabel x = (JLabel) paneLeft.getComponent(i * 8 + j);
            }
        }
    }
}
