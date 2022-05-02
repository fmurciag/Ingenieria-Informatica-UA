/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2021_p2si;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author fidel
 */
public class CIFAR10Loader {

    private ArrayList[] cifarImageDB;
    
    void loadDBFromPath(String path){
        
        int imagesCount=0;
        
        //Una arrayList por clase almacenará las imágenes
        cifarImageDB = new ArrayList[10];
        
        //Creo un array list de imagenes para cada conjunto y cargo cada una
        //de las imágenes disponibles por conjunto
        for (int i=0;i<10; i++){
            cifarImageDB[i] = new ArrayList();
            //System.out.println("Loaded set "+i);
            File[] files = new File(path,""+i).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                   cifarImageDB[i].add(new Imagen(file.getAbsoluteFile()));
                   imagesCount++;
                }
            }
        }
        
        
    }
    
    ArrayList getImageDatabaseForDigit(int digit){
        return cifarImageDB[digit];
    }
    
}
