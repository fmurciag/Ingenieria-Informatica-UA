package ppss;

import java.util.Objects;

public class Tramo {
    private int origen;
    private int longitud;

    public Tramo() {
        origen=0;
        longitud=0;
    }

    public Tramo(int origen,int longitud) {
        this.origen = origen;
        this.longitud=longitud;
    }

    public void setOrigen(int _origen_tramoMax) {
        origen=_origen_tramoMax;
    }

    public void setLongitud(int _longitudMax_tramo) {
        longitud=_longitudMax_tramo;
    }
    public int getOrigen(){
        return origen;
    }
    public int getLongitud(){
        return longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tramo tramo = (Tramo) o;
        return origen == tramo.origen && longitud == tramo.longitud;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origen, longitud);
    }
}
