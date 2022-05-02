/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2021_p2si;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author FRAN
 */
public class ClasificadorDebil {
    private int pixel;
    private int umbral;
    private int direccion;
    private int[] perteneceClase;//guardamos si pertenece o no a la clase 
    private double error;//guardamos el error
    double confianza;
    int aciertos;
   /**
    * constructor por defectos
    */
    public ClasificadorDebil() {
        this.pixel = 0;
        this.umbral = 0;
        this.direccion = 0;
        this.error=0;
    }
    /**
     * constructor 1
     * @param Pixel
     * @param Umbral
     * @param Direccion
     * @param TAM 
     */
    public ClasificadorDebil(int Pixel, int Umbral, int Direccion, int TAM) {
        this.pixel = Pixel;
        this.umbral = Umbral;
        this.direccion = Direccion;
        this.perteneceClase=new int[TAM];
        this.error=0;
    }
    /**
     * constructor  2
     * @param Pixel
     * @param Umbral
     * @param Direccion
     * @param confianza 
     */
    public ClasificadorDebil(int Pixel, int Umbral, int Direccion,double confianza) {
        this.pixel = Pixel;
        this.umbral = Umbral;
        this.direccion = Direccion;
        this.confianza=confianza;
    }

    ClasificadorDebil(int parseInt, int parseInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * genera un clasificador al azar
     * @param TAM
     * @return 
     */
    public ClasificadorDebil generarClasifAzar(int TAM){
        
        Random r = new Random();
        int p = r.nextInt(3072);
        int u = r.nextInt(255);
        int d = r.nextInt(2)+1;//1-2
        if(d==2)d=-1;//si es 2 pasa a -1
        return new ClasificadorDebil(p, u, d,TAM);
        
    }
    /**
     * aplica el clasificar debil a las imagenes
     * @param clasificador
     * @param X 
     */
    public void aplicarClasifDebil(ClasificadorDebil clasificador,ArrayList <Imagen> X){

         for(int i=0; i<X.size();i++){
             if(clasificador.direccion==1){
                 if(Adaboost.Byte2Unsigned(X.get(i).getImageData()[clasificador.pixel])>clasificador.umbral){
                    clasificador.perteneceClase[i]=1;
                 }else{
                    clasificador.perteneceClase[i]=-1;
                 }
             }else{
                 if(Adaboost.Byte2Unsigned(X.get(i).getImageData()[clasificador.pixel])>clasificador.umbral){
                     clasificador.perteneceClase[i]=-1;
                 }else{
                     clasificador.perteneceClase[i]=1;
                 }
             }
             
         }
     }
    /**
     * aplica el clasificador debil a una imagen
     * @param clasificador
     * @param x
     * @return 
     */
        public int clasDebil(ClasificadorDebil clasificador, Imagen x){//optieene el clasificador debil para una foto
        if(clasificador.getDireccion()==1){
                 if(Adaboost.Byte2Unsigned(x.getImageData()[clasificador.getPixel()])>clasificador.getUmbral()){
                    return 1;
                 }else{
                    return-1;
                 }
             }else{
                 if(Adaboost.Byte2Unsigned(x.getImageData()[clasificador.getPixel()])>clasificador.getUmbral()){
                     return-1;
                 }else{
                     return 1;
                 }
             }
    }
    
    
    
    
    
    /**
     * optiene el error y aciertos del clasificador
     * @param clasificador
     * @param X
     * @param Y
     * @param D 
     */ 
    public void obtenerErrorClasif(ClasificadorDebil clasificador,ArrayList <Imagen> X, int[] Y, double[] D ){
        double err=0;
        for(int i=0; i<X.size();i++){
            if(Y[i]!=clasificador.perteneceClase[i]){//fallo
               err += D[i];
            }else{
                aciertos++;
            }
        }
        clasificador.error=err;
    }
    
    public void setConfianza(double err){
        confianza=0.5*(Math.log((1- err)/err)/Math.log(2));
    }

    public double getConfianza() {
        return confianza;
    }
    
    public int[] getPerteneceClase() {
        return perteneceClase;
    }

    public void setPerteneceClase(int[] perteneceClase) {
        this.perteneceClase = perteneceClase;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public int getPixel() {
        return pixel;
    }

    public void setPixel(int Pixel) {
        this.pixel = Pixel;
    }

    public int getUmbral() {
        return umbral;
    }

    public void setUmbral(int Umbral) {
        this.umbral = Umbral;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int Direccion) {
        this.direccion = Direccion;
    }

    public int getAciertos() {
        return aciertos;
    }

    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00000000000");
        return pixel + ";" + umbral + ";" + direccion + ";"+confianza;
    }

   

   

    
    
    
}
