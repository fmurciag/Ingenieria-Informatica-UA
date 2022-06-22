package ppss;

public class DataArray {
    private int[] coleccion;
    private int numElem;

    public DataArray() {
        coleccion = new int[10];
        numElem=0;
    }

    public DataArray(int[] datos) {
        coleccion = datos;
        numElem=datos.length;
    }

    public int size() {
        return numElem;
    }

    public int[] getColeccion() {
        int [] elementos = new int[numElem];
        for (int i=0; i<numElem; i++) {
            elementos[i]=coleccion[i];
        }
        return elementos;
    }

    //método para borrar un entero de la colección
    public void delete(int elem) throws DataException {
        int i=0;
        boolean encontrado = false;

        if ((numElem==0)&&(elem<=0)) {
            throw new DataException("Colección vacía. "+
                    "Y el valor a borrar debe ser > 0");
        } else if (numElem==0) {
            throw new DataException("No hay elementos en la colección");
        } else if (elem<=0) {
            throw new DataException("El valor a borrar debe ser > 0");
        } else {
            //buscamos el elemento en la coleccion
            while (i < numElem && !encontrado) {
                if (coleccion[i] == elem) {
                    encontrado = true;
                } else {
                    i++;
                }
            }

            if (encontrado) {
                while (i < (numElem - 1) && coleccion[i] != 0) {
                    coleccion[i] = coleccion[i + 1];
                    i++;
                }
                if (i == (numElem - 1)) {
                    coleccion[numElem - 1] = 0;
                }
                numElem--;
            } else throw new DataException("Elemento no encontrado");
        }
    }
}
