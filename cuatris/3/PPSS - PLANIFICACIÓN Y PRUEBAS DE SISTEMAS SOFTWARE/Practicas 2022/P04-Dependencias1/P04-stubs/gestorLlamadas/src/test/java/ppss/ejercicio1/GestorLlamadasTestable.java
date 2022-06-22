package ppss.ejercicio1;

public class GestorLlamadasTestable extends GestorLlamadas{//clase para el doble
    int result;
    public void setResult(int hora) {
        this.result = hora;
    }
    @Override
    public int getHoraActual() {
        return result;
    }

}
