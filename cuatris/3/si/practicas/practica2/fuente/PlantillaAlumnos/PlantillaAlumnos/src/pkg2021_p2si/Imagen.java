/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2021_p2si;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fidel
 */
public class Imagen {
    
    private BufferedImage biImage;
    private byte [] imageData;
    
    Imagen(){
        biImage = null;
    }
    
    
    Imagen(File file){
        try {
            biImage = ImageIO.read(file);
            imageData = ((DataBufferByte)biImage.getRaster().getDataBuffer()).getData();
        } catch (IOException ex) {
            Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void loadFromPath(String filename){
        try
        {
            File file = new File(filename);
            biImage = ImageIO.read(file);
            imageData = ((DataBufferByte)biImage.getRaster().getDataBuffer()).getData();

            
            //Conversion rápida de color a grises, por si es necesario...
            //BufferedImage biColor = ImageIO.read(file);
            //biImage = new BufferedImage(biColor.getWidth(), biColor.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            //biImage.getGraphics().drawImage(biColor, 0, 0, null);
                           
        } catch (IOException e)
        {
                System.out.println(e.getMessage());
        }
    }
    
    byte [] getImageData(){
        return imageData;
    }
    
    BufferedImage getBufferedImage(){
        return biImage;
    }
    
    
}
