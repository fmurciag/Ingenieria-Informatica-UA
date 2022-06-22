import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {

    @Test
    void calculaPrecioC1(){
        //datos de eentrada
        int dias=10;
        TipoCoche coche=TipoCoche.TURISMO;
        LocalDate inicio = LocalDate.of(2022, 5, 18);

        //declaramos los dobles
        ServicioStub servicioStub=new ServicioStub();
        servicioStub.setResult(10);
        CalendarioStub calendarioStub=new CalendarioStub();

        //hacemos inyeccion
        ArquilaCochesTestable arquilaCochesTestable=new ArquilaCochesTestable();
        arquilaCochesTestable.setServicio(servicioStub);
        arquilaCochesTestable.setCalStub(calendarioStub);

        //generar informe
        Ticket ticket=new Ticket();
        ticket=assertDoesNotThrow(() -> arquilaCochesTestable.calculaPrecio(coche,inicio,dias), "Excepción lanzada");
        assertEquals(75,ticket.getPrecio_final());

    }
    @Test
    void calculaPrecioC2(){
        //datos de entrada
        int dias=7;
        TipoCoche coche=TipoCoche.TURISMO;
        LocalDate inicio = LocalDate.of(2022, 6, 19);
        LocalDate fiesta1 = LocalDate.of(2022, 6, 24);
        LocalDate fiesta2 = LocalDate.of(2022, 6, 20);
        ArrayList<LocalDate>fiestas=new ArrayList<>();
        fiestas.add(fiesta1);
        fiestas.add(fiesta2);

        //declaramos los dobles
        ServicioStub servicioStub=new ServicioStub();
        servicioStub.setResult(10);
        CalendarioStub calendarioStub=new CalendarioStub();
        calendarioStub.setResult(fiestas,new ArrayList<LocalDate>());

        //hacemos inyeccion
        ArquilaCochesTestable arquilaCochesTestable=new ArquilaCochesTestable();
        arquilaCochesTestable.setServicio(servicioStub);
        arquilaCochesTestable.setCalStub(calendarioStub);

        //generar informe
        Ticket ticket=new Ticket();
        ticket=assertDoesNotThrow(() -> arquilaCochesTestable.calculaPrecio(coche,inicio,dias), "Excepción lanzada");
        assertEquals(62.5,ticket.getPrecio_final());
    }
    @Test
    void calculaPrecioC3(){
        //datos de entrada
        int dias=8;
        TipoCoche coche=TipoCoche.TURISMO;
        LocalDate inicio = LocalDate.of(2022, 4, 17);
        LocalDate diaError1 = LocalDate.of(2022, 4, 18);
        LocalDate diaError2 = LocalDate.of(2022, 4, 21);
        LocalDate diaError3 = LocalDate.of(2022, 4, 22);
        ArrayList<LocalDate>fiestasError=new ArrayList<>();
        fiestasError.add(diaError1);
        fiestasError.add(diaError2);
        fiestasError.add(diaError3);

        //declaramos los dobles
        ServicioStub servicioStub=new ServicioStub();
        servicioStub.setResult(10);
        CalendarioStub calendarioStub=new CalendarioStub();
        calendarioStub.setResult(new ArrayList<LocalDate>(),fiestasError);

        //hacemos inyeccion
        ArquilaCochesTestable arquilaCochesTestable=new ArquilaCochesTestable();
        arquilaCochesTestable.setServicio(servicioStub);
        arquilaCochesTestable.setCalStub(calendarioStub);

        //generar informe
        Ticket ticket=new Ticket();
        String msError="Error en dia: 2022-04-18; " + "Error en dia: 2022-04-21; " + "Error en dia: 2022-04-22; ";
        MensajeException ex = assertThrows(MensajeException.class,
                () -> arquilaCochesTestable.calculaPrecio(coche,inicio,dias));
        assertEquals(msError, ex.getMessage());






    }

}