package ppss;

import org.junit.jupiter.api.Test;
import ppss.excepciones.ReservaException;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    @Test
    void realizaReservaC1(){
        //datos de entrada
        String login="xxxx";
        String password="xxxx";
        String socio="Luis";
        String isbn[]={"11111"};

        //inyeccion
        ReservaTestable reservaTestable=new ReservaTestable();
        reservaTestable.setPermiso(false);

        //informe
        String esperado="ERROR de permisos; ";
        ReservaException ex = assertThrows(ReservaException.class,
                () -> reservaTestable.realizaReserva(login,password,socio,isbn));
        assertEquals(esperado, ex.getMessage());

    }
    @Test
    void realizaReservaC2(){
        //datos de entrada
        String login="ppss";
        String password="ppss";
        String socio="Luis";
        String isbn[]={"11111","22222"};

        //inyeccion
        ReservaTestable reservaTestable=new ReservaTestable();
        reservaTestable.setPermiso(true);

        //informe
        assertDoesNotThrow(() ->reservaTestable.realizaReserva(login,password,socio,isbn));

    }
    @Test
    void realizaReservaC3(){
        //datos de entrada
        String login="ppss";
        String password="ppss";
        String socio="Luis";
        String isbn[]={"33333"};

        //crear a los dobles e inyeccion
        ReservaTestable reservaTestable=new ReservaTestable();
        reservaTestable.setPermiso(true);
        OperacionStub operacionStub=new OperacionStub();
        operacionStub.setResult(2);
        IOperacionBOFactory.setServicio(operacionStub);

        //informe
        String esperado="ISBN invalido:33333; ";
        ReservaException ex = assertThrows(ReservaException.class,
                () -> reservaTestable.realizaReserva(login,password,socio,isbn));
        assertEquals(esperado, ex.getMessage());
    }

    @Test
    void realizaReservaC4(){
        //datos de entrada
        String login="ppss";
        String password="ppss";
        String socio="Pepe";
        String isbn[]={"11111"};

        //crear a los dobles e inyeccion
        ReservaTestable reservaTestable=new ReservaTestable();
        reservaTestable.setPermiso(true);
        OperacionStub operacionStub=new OperacionStub();
        operacionStub.setResult(3);
        IOperacionBOFactory.setServicio(operacionStub);

        //informe
        String esperado="SOCIO invalido; ";
        ReservaException ex = assertThrows(ReservaException.class,
                () -> reservaTestable.realizaReserva(login,password,socio,isbn));
        assertEquals(esperado, ex.getMessage());
    }
    @Test
    void realizaReservaC5(){
        //datos de entrada
        String login="ppss";
        String password="ppss";
        String socio="Luis";
        String isbn[]={"11111"};

        //crear a los dobles e inyeccion
        ReservaTestable reservaTestable=new ReservaTestable();
        reservaTestable.setPermiso(true);
        OperacionStub operacionStub=new OperacionStub();
        operacionStub.setResult(1);
        IOperacionBOFactory.setServicio(operacionStub);

        //informe
        String esperado="CONEXION invalida; ";
        ReservaException ex = assertThrows(ReservaException.class,
                () -> reservaTestable.realizaReserva(login,password,socio,isbn));
        assertEquals(esperado, ex.getMessage());
    }
}