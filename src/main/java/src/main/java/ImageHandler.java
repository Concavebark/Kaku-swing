package src.main.java;

import javax.swing.*;
import java.io.File;

public class ImageHandler {

    public ImageIcon getImage(String dir) { //most likely deprecate and remove later
        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL imageURL = cldr.getResource(dir);
        ImageIcon image = new ImageIcon(imageURL);
        return image;
    }

    public ImageIcon getSimpleImage(String dir) {
        File imageCheck = new File(dir);
        if(imageCheck.exists()) {
            return new ImageIcon(dir);
        }
        //below could even return a missing texture file if I'm feeling funky
        return new ImageIcon(); // returns empty if it can't find it just in case
    }
}
