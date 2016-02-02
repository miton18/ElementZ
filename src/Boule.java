import javax.swing.*;

/**
 * Created by rcd18 on 29/01/2016.
 */
public class Boule {

    ImageIcon bouleIcon;
    ImageIcon bouleIconHover;

    JButton button;
    int color;

    public Boule(int color) {

        this.color = color;
        this.loadImage();
    }

    public void setColor(int newColor){

        this.color = newColor;
        this.loadImage();

        this.button.repaint();
    }
    private void loadImage(){
        // IMAGE
        bouleIcon  = new ImageIcon(this.getClass().getResource("boule_"+ this.color +".jpg"));
        bouleIconHover  = new ImageIcon(this.getClass().getResource("boule_o_"+ this.color +".jpg"));
        // BUTON
        button      = new JButton(bouleIcon);
        // ENLEVE STYLE PAR DEFAULT
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setRolloverEnabled(true);
        button.setRolloverIcon(bouleIconHover);
    }
    public String toString(){
        return "Boule de couleur " + this.color;
    }
}
