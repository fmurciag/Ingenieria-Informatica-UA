package ppss;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FicheroTexto {

    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            fichero = new FileReader(nombreFichero);
            int i=0;
            while (i != -1) {
                i = fichero.read();
                if(i!=-1)contador++;//se contara siempre que i no sea -1 para contar solo los caracteres

            }
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero + " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero + " (Error al leer el archivo)");
        }
        if (fichero != null) {
            try {
                fichero.close();
            } catch (IOException e) {
                throw new FicheroException(nombreFichero + " (Error al cerrar el archivo)");
            }
        }
        return contador;
    }
}
