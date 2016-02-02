import javax.swing.*;

/**
 * Created by rcd18 on 29/01/2016.
 */
public class Boule {

    ImageIcon bouleIcon;
    ImageIcon bouleIconHover;

    JLabel label;
    JLabel labelHover;
    int color;

    public Boule(int color) {

        bouleIcon  = new ImageIcon(this.getClass().getResource("boule_"+ color +".jpg"));
        bouleIconHover  = new ImageIcon(this.getClass().getResource("boule_o_"+ color +".jpg"));
        this.color = color;

        label      = new JLabel(bouleIcon);
        labelHover = new JLabel(bouleIconHover);
    }

    public void setColor(int newColor){

        this.color = newColor;
    }
}
