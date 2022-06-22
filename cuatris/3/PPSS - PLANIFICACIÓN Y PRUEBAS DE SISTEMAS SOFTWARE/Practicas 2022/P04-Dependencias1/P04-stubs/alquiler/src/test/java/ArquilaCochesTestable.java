import java.time.LocalDate;

public class ArquilaCochesTestable extends AlquilaCoches{

    ServicioStub servicio;
    CalendarioStub calStub;

    public void setServicio(ServicioStub servicio) {
        this.servicio = servicio;
    }

    public void setCalStub(CalendarioStub calStub) {
        this.calStub = calStub;
    }

    @Override
    public Servicio crearServicio(){
        return servicio;
    }
    @Override
    public void crearCalendario(){
        calendario=calStub;
    }



}
