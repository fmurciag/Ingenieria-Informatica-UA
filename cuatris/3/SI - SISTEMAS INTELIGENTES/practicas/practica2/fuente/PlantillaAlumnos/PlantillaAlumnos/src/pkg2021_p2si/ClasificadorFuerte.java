/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2021_p2si;

import java.util.ArrayList;

/**
 *
 * @author FRAN
 */
public class ClasificadorFuerte {
    private ArrayList<ClasificadorDebil>Debiles;
    private int [] predicciones;
    private double aciertos;

    /**
     * constructor del clasifcador
     * @param Debiles 
     */
    public ClasificadorFuerte(ArrayList<ClasificadorDebil> Debiles) {
        this.Debiles = Debiles;
        
    }
    
    
    /**
     * aplica el clasificador fuerte a las imagen
     * @param X 
     */
     public void aplicarClasFuerte(ArrayList<Imagen> X){
        
        predicciones=new int[X.size()];
        for(int i=0;i<X.size();i++){//recorremos las imagenes
            double h=0;
            for (int j = 0; j <Debiles.size() ; j++) {
                ClasificadorDebil debil=Debiles.get(j);
                 h+=debil.getConfianza()*debil.clasDebil(debil, X.get(i));
            }
            if(h<0){
             predicciones[i]= -1;
            }else if(h>0){
             predicciones[i]= 1;
            }
            
        }
    }
     
     
     /**
      * optiene los aciertos del clasificador
      * @param Y
      * @param X 
      */
     public void obtenerAciertosClasif(int[] Y, ArrayList<Imagen>X){
        double match=0;
        for(int i=0; i<X.size();i++){
            if(Y[i]==this.predicciones[i]){//aciertos
               match++;
            }
        }
        aciertos=match;
    }
    
     
    public ArrayList<ClasificadorDebil> getDebiles() {
        return Debiles;
    }

    public void setDebiles(ArrayList<ClasificadorDebil> Debiles) {
        this.Debiles = Debiles;
    }

    public int[] getPredicciones() {
        return predicciones;
    }

    public void setPredicciones(int[] predicciones) {
        this.predicciones = predicciones;
    }

    public double getAciertos() {
        return aciertos;
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < Debiles.size(); i++) {
            s+=Debiles.get(i).toString()+"/";
            
        }
        s+="\n";
        return s;
    }
   
     
    
}
