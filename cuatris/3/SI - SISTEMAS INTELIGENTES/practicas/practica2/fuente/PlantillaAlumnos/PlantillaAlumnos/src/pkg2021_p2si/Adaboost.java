/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2021_p2si;

import com.sun.org.apache.bcel.internal.generic.DUP;
import com.sun.org.apache.bcel.internal.generic.NEW;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author fidel
 */
public class Adaboost {
    
    ArrayList<Imagen>X;

    
    public static int Byte2Unsigned(byte b) {
        return b & 0xFF;
    }
    
    /**
     * actualiza los pesos
     * @param tam
     * @param cDebil
     * @param D
     * @param Y
     * @return 
     */
    private static double[] updatePesos(int tam,ClasificadorDebil cDebil,double[] D,int[] Y){//
        double peso[]=new double[tam];
        double Z=0; 
        for(int i=0;i<tam;i++){//calculamos numerador y denoinador
                peso[i]=D[i]*Math.pow(Math.E, -cDebil.getConfianza()*Y[i]*cDebil.getPerteneceClase()[i]);
                Z+=peso[i];
            }
        for(int j=0;j<tam;j++){//actualizamos los pesos
          D[j]=peso[j]/Z;
        }
        return D;
    }
        
    /**
     * implementacion del adaboss
     * @param X
     * @param Y
     * @param Xtest override 
     * @param Ytest override
     * @param T
     * @param A
     * @return 
     */
    public static ClasificadorFuerte Adaboost(ArrayList<Imagen> X,int[] Y,ArrayList<Imagen> Xtest,int[] Ytest,int T,int A){//
        
        ClasificadorDebil c;
        double D[]=new double[X.size()];//array de pesos
        for(int i=0; i<X.size(); i++){//iteramos por cada clasificdor que hay en cada imagen
            D[i]=1.0/X.size();
        }
        ArrayList<ClasificadorDebil>bestClas=new ArrayList<>();
        for(int Nclas=0;Nclas<T;Nclas++){//recorremos los clasificadores
            double err=0.0;
            ClasificadorDebil cDebil=new ClasificadorDebil();
            for(int i=0;i<A;i++){//generamos pruebas
                c=new ClasificadorDebil().generarClasifAzar(X.size());
                c.aplicarClasifDebil(c, X);
                c.obtenerErrorClasif(c, X, Y, D);
                if(i==0){
                    err=c.getError();
                    cDebil=c;
                }else{
                    if(c.getError()<err){//cogemos el error mas pequeño
                        err=c.getError();
                        cDebil=c;
                    }
                }
                
                
            }
            

            
            cDebil.setConfianza(err);//calclamos y colocamos la confianza en el clasificador debil

            D=updatePesos(X.size(),cDebil,D,Y);//actualizamos los pesos de la imagen
            bestClas.add(cDebil);  
            
        }
        
        return new ClasificadorFuerte(bestClas);
        
    }
    /**
     * optenemos los conjuntos de imagenes de entrenamiento
     * @param X
     * @param Y
     * @param imagenes
     * @param carpeta 
     */
    public static void conjuntosImagenes(ArrayList<Imagen>X, int[] Y,int imagenes,int carpeta){//
        CIFAR10Loader m1=new CIFAR10Loader();
        m1.loadDBFromPath("./cifar10_2000");
        int tam=0;
         for(int i=0; i < 10; i++) {
            ArrayList d1imgs = m1.getImageDatabaseForDigit(i);
            if(i==carpeta){// si estoy en la carpeta de la clase indicada, optengo sus imagenes
                for (int j = 0; j < imagenes; j++) {//carpeta elegida
                    Imagen img = (Imagen) d1imgs.get(j);
                    X.add(img);
                    Y[tam]=1;
                    tam++;
                }
            }else{//resto de crpetas
                for (int j = 0; j < imagenes/9; j++) {
                    Imagen img = (Imagen) d1imgs.get(j);
                    X.add(img);
                    Y[tam]=-1;
                    tam++;
                }
            }
        }
    }
    /**
     * optenemos los conjuntos de imagenes de test
     * @param X
     * @param Y
     * @param imagenes
     * @param carpeta
     * @param totalImagenes 
     */    
    public static void conjuntosImagenesTest(ArrayList<Imagen>X, int[] Y,int imagenes,int carpeta,int totalImagenes){//
        CIFAR10Loader m1=new CIFAR10Loader();
        m1.loadDBFromPath("./cifar10_2000");
        int tam=0;
         for(int i=0; i < 10; i++) {
            ArrayList d1imgs = m1.getImageDatabaseForDigit(i);
            if(i==carpeta){// si estoy en la carpeta de la clase indicada, optengo sus imagenes
                for (int j = imagenes; j <totalImagenes ; j++) {//carpeta elegida
                    Imagen img = (Imagen) d1imgs.get(j);
                    X.add(img);
                    Y[tam]=1;
                    tam++;
                }
            }else{
                for (int j = 0; j <(totalImagenes-imagenes)/9 ; j++) {//resto de carpetas
                    Imagen img = (Imagen) d1imgs.get(j);
                    X.add(img);
                    Y[tam]=-1;
                    tam++;
                }
            }
        }
    }
    /**
     * //imprimimos le porcentaje de acierto de la prueba
     * @param clasFuerte
     * @param tam
     * @return 
     */
    private static double resultadoPrueba(ClasificadorFuerte clasFuerte,double tam){
        double porcentejeAciertos= ((clasFuerte.getAciertos())/(double)tam) *100;
        DecimalFormat df= new DecimalFormat("#.00");
        
        return porcentejeAciertos;
    }
    /**
     * imprimimos el porcentaje promedio
     * @param totalImagenes
     * @param fallos
     * @param porcentajePromedio
     * @param s 
     */
    private static void resultado(int totalImagenes,int fallos, double porcentajePromedio,String s){
        DecimalFormat df= new DecimalFormat("#.00");
         System.out.println("********** "+s+" **********\n"+"Nº imagenes: "+totalImagenes+"\n"+
                "Nº negativos: "+fallos+"\n"
                + "Porcentaje de acierto Promedio: "+df.format(porcentajePromedio)+"%\n");
    }
    
    /**
     * lee el fichero con los 10 clasificadores fuertes de entrenamiento
     * @param FILE
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private static ArrayList<ClasificadorFuerte> LeerFichero(String FILE) throws FileNotFoundException, IOException{
        ArrayList<ClasificadorFuerte>Fuertes=new ArrayList<>();
        FileReader f=new FileReader(FILE);
        BufferedReader br=new  BufferedReader(f);
        String linea="";
        while ((linea=br.readLine())!=null) {
            String cadena[]=linea.split("\\/");
            ArrayList<ClasificadorDebil>Debiles=new ArrayList<>();
            for (String cadena1 : cadena) {
                String[] datos = cadena1.split("\\;");
                ClasificadorDebil debil = new ClasificadorDebil(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), Integer.parseInt(datos[2]),Double.parseDouble(datos[3]));
                Debiles.add(debil);
            }
            ClasificadorFuerte fuerte=new ClasificadorFuerte(Debiles);
            Fuertes.add(fuerte);
        }
        return Fuertes;
    } 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        String FILE="clasificadoresFuertes.txt";    
        //Son necesarios dos argumentos 
        if (args.length == 2) {
            
            //Se ejecuta la práctica como entrenamiento
            if (args[0].equals("-t")) {
                
                //Cargador CIFAR10 de SI
                CIFAR10Loader ml = new CIFAR10Loader();
                ml.loadDBFromPath("./cifar10_2000");
                
                //EL SIGUIENTE CÓDIGO ES DE EJEMPLO PARA QUE VEÁIS CÓMO ACCEDER A LAS IMÁGENES Y SUS PÍXELES
                //Accedo a las imagenes del conjunto 1 (coches)
                ArrayList d1imgs = ml.getImageDatabaseForDigit(1);
        
                //Cojo la tercera imagen de las imágenes de coches cargadas en la línea anterior 
                Imagen img = (Imagen) d1imgs.get(2);
        
                //Obtengo el vector de bytes que forma la imagen cargada
                byte imageData[] = img.getImageData();
        
                //En CIFAR las imágenes son de 32x32x3. Por tanto el array imageData tendrá
                //como tamaño 3072 posiciones.
        
                //Los pixes están en orden BGR, con lo que el primer pixel se almacena en las 
                //posiciones 0 (B), 1(G) y 2 (R) del array.
        
                //Recorro todo el vector de bytes que forma la imagen
                /*for (int i = 0; i < imageData.length; i++){
            
                    //Visualizo solo un canal para ilustrar como acceder a los pixels 
                    //Dejo el canal R y los otros canales los pongo a 0. 
                    //Puedes comentar todo el IF para ver la imagen sin modificar
                    if (i%3==2) {
                        ;
                    } else {
                        imageData[i] = (byte) 0;
                    }
            
                    System.out.print(Byte2Unsigned(imageData[i])+ ",");
                }*/
        
                //Muestro la imagen que contendrá únicamente el canal R
               
                    
                /**
                 * inicializo los fallos y promedios
                 */
                int fallos=0;
                int fallosTest=0;
                int totalImagenes=0;
                int totalImagenesTest=0;
                double porcentajePromedio=0.0;
                double porcentajePromedioTest=0.0;
                try {
                    FileWriter fichero = new FileWriter(FILE);
                    for (int numCarpeta = 0; numCarpeta < 10; numCarpeta++) {//iteramos por cada clase que hay
                        ArrayList<Imagen> X=new ArrayList<>();
                        File carpeta = new File("./cifar10_2000/" + numCarpeta);//obtenemos el fichero de la clase a probar
                        File[] lista= carpeta.listFiles();
                        int imagenes=lista.length*80/100;// el 80% de las imagenes
                        int Y[]=new int[imagenes*2];
                        ArrayList<Imagen> Xtest=new ArrayList<>();
                        int Ytest[]=new int[imagenes*2];
                        conjuntosImagenes(X, Y, imagenes,numCarpeta);//optenemos los conjuntos de imagenes correspondientes a la carpeta que se encuntra la clase a probar
                        conjuntosImagenesTest(Xtest,Ytest,imagenes,numCarpeta,lista.length);

                        /*
                        *ENTRENAMIENTO
                        */
                        System.out.println("********** PRUEBA Nº"+(numCarpeta+1)+ " **********");
                        /**
                         * llamada a adaboost mejores valores 120/100
                         */
                        ClasificadorFuerte clasFuerte=Adaboost(X,Y,Xtest,Ytest,120,100);//llamada a adaboost
                        clasFuerte.aplicarClasFuerte(X);//aplicacion del clasificador
                        clasFuerte.obtenerAciertosClasif(Y,X);
                        /**
                         * calculos estadisticos
                         */
                        totalImagenes+=X.size();
                        fallos+=(X.size()-clasFuerte.getAciertos());
                        porcentajePromedio+=resultadoPrueba(clasFuerte,(double)X.size());//optenemos el resultado
                        DecimalFormat df= new DecimalFormat("#.00");
                        System.out.println("Nº imagenes: "+X.size()+"\n"+
                                "Nº negativos: "+(X.size()-clasFuerte.getAciertos())+"\n"
                                   + "Porcentaje de acierto Promedio: "
                                        + ""+df.format(resultadoPrueba(clasFuerte,(double)X.size()))+"%\n");
                        fichero.write(clasFuerte.toString().replaceAll("\\,","."));// escritura en fichero
                       
                        
                        
                        /*
                        *TEST
                        */
                        System.out.println("********** Test Nº"+(numCarpeta+1)+ " **********");
                        totalImagenesTest+=Xtest.size();
                        clasFuerte.aplicarClasFuerte(Xtest);//aplicacion del clasificador
                        clasFuerte.obtenerAciertosClasif(Ytest,Xtest);
                        /**
                         * calculos estadisticos
                         */
                        fallosTest+=(Xtest.size()-clasFuerte.getAciertos());
                        porcentajePromedioTest+=resultadoPrueba(clasFuerte,(double)Xtest.size());//optenemos el resultado
                       System.out.println("Nº imagenes: "+Xtest.size()+"\n"+
                                "Nº negativos: "+(Xtest.size()-clasFuerte.getAciertos())+"\n"
                                    + "Porcentaje de acierto Promedio: "+df.format(resultadoPrueba(clasFuerte,(double)Xtest.size()))+"%\n");

                        
                        
                        
                    }
                    fichero.close();
                   System.out.println("--------------------------------------\n\t   Promedios totales \n--------------------------------------\n");
                    resultado(totalImagenes,fallos,porcentajePromedio/10,"Entrenamiento");
                    resultado(totalImagenesTest,fallosTest,porcentajePromedioTest/10,"Test");
                } catch (Exception e) {
                }
                
            } else {
                //Se ejecuta la práctica como test
               ArrayList<ClasificadorFuerte>Fuertes=LeerFichero(FILE);
               Imagen img=new Imagen();
               img.loadFromPath(args[1]);
               int tipo=0;
               double aciertos=0;
                for (int i = 0; i < 10; i++) {//recorremos los 10 clasificadores
                    double h=0;
                        for (int j = 0; j<Fuertes.get(i).getDebiles().size() ; j++) {//optenemos el valor de los clasificadores
                            ClasificadorDebil debil=Fuertes.get(i).getDebiles().get(j);
                            h+=debil.getConfianza()*debil.clasDebil(debil,img);
                        }

                    if(i==0){
                        aciertos=h;
                    }
                    if(h>aciertos){//optenemos el mejor clasificador
                        aciertos=h;
                        tipo=i;
                    }
                }
                switch(tipo){
                    case 0:
                        System.out.println("Aviones");
                        break;
                    case 1:
                        System.out.println("Automóviles");
                        break;
                    case 2:
                        System.out.println("Pájaros");
                        break;
                    case 3:
                        System.out.println("Gatos");
                        break;
                    case 4:
                        System.out.println("Ciervos");
                        break;
                    case 5:
                        System.out.println("Perros");
                        break;
                    case 6:
                        System.out.println("Ranas");
                        break;
                    case 7:
                        System.out.println("Caballos");
                        break;
                    case 8:
                        System.out.println("Barcos");
                        break;
                    case 9:
                        System.out.println("Camiones");
                        break;
                    
                    
                }
                

            }
        } else {
            System.out.println("El número de parámetros es incorrecto");
            System.out.println("Las posibilidades son:");
            System.out.println("Adaboost –t <fichero_almacenamiento_clasificador_fuerte>");
            System.out.println("Adaboost <fichero_origen_clasificador_fuerte> <imagen_prueba>");
        }
    }
    
}
