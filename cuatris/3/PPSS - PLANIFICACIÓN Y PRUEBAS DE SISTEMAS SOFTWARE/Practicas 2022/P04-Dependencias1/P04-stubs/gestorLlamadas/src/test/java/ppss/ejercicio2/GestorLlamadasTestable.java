package ppss.ejercicio2;


//clase testabla para sobreescribir
public class GestorLlamadasTestable extends GestorLlamadas{
    Calendario calendario;
    public void setcalendario(Calendario calendarioStub) {
        this.calendario = calendarioStub;
    }
    @Override
    public Calendario getCalendario() {
        return calendario;
    }
}
