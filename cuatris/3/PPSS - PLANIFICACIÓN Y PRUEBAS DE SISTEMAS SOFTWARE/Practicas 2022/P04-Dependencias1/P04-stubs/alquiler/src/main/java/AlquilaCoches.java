import java.time.LocalDate;

public class AlquilaCoches {
    protected Calendario calendario;//eliminamos la creacion para que lo cree la factoria local

    //metodo factoria local para crear un servicio
    public IService crearServicio(){
        IService s=new Servicio();
        return s;
    }
    //metodo factoria local para crear un claendario
    public void crearCalendario(){
        calendario=new Calendario();
    }

    //metodo refactorizado
    public Ticket calculaPrecio(TipoCoche tipo, LocalDate inicio, int ndias)
            throws MensajeException {
        Ticket ticket = new Ticket();
        float precioDia,precioTotal =0.0f;
        float porcentaje = 0.25f;

        String observaciones = "";

        crearCalendario();//creamos el calendario
        IService servicio=crearServicio();//creamos el servicio con la clase local
        precioDia = servicio.consultaPrecio(tipo);///////////////dependecia
        for (int i=0; i<ndias;i++) {
            LocalDate otroDia = inicio.plusDays((long)i);
            try {
                if (calendario.es_festivo(otroDia)) {////////////dependencia
                    precioTotal += (1+ porcentaje)*precioDia;
                } else {
                    precioTotal += (1- porcentaje)*precioDia;
                }
            } catch (CalendarioException ex) {
                observaciones += "Error en dia: "+otroDia+"; ";
            }
        }

        if (observaciones.length()>0) {
            throw new MensajeException(observaciones);
        }

        ticket.setPrecio_final(precioTotal);
        return ticket;
    }
}
