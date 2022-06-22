package ppss;

import org.easymock.EasyMock;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.easymock.EasyMock.partialMockBuilder;

class ReservaMockTest {
    IMocksControl control;
    Reserva sut;
    FactoriaBOs mockFactoria;
    IOperacionBO mockIOpe;
    @BeforeEach
    void crearMocks(){
        control= EasyMock.createStrictControl();
        sut=partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").mock(control);
        mockFactoria=control.mock(FactoriaBOs.class);
        mockIOpe=control.mock(IOperacionBO.class);
    }

    @Test
    void realizaReservaC1() {
        //datos de entrada
        String login="xxxx";
        String passwor=login;
        String socio="Pepe";
        String [] isbns={"22222"};
        String esperado="ERROR de permisos; ";
        //programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.compruebaPermisos(login,passwor,Usuario.BIBLIOTECARIO)).andReturn(false)
        );
        //activamos el mock
        control.replay();
        //invocamos al sut
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos
        control.verify();
        //informe
        assertEquals(esperado,real);
    }
    @Test
    void realizaReservaC2() {
        //datos de entrada
        String login="ppss";
        String passwor=login;
        String socio="Pepe";
        String [] isbns={"22222","33333"};

        //programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.compruebaPermisos(login,passwor,Usuario.BIBLIOTECARIO)).andReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[0])
        );
        EasyMock.expectLastCall();
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[1])
        );
        EasyMock.expectLastCall();
        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        control.replay();
        //invocamos al sut y el informe
        assertDoesNotThrow(
                ()->sut.realizaReserva(login,passwor,socio,isbns)
        );
        //verificamos
        control.verify();

    }
    @Test
    void realizaReservaC3() {
        //datos de entrada
        String login="ppss";
        String passwor=login;
        String socio="Pepe";
        String [] isbns={"11111"};
        String esperado="ISBN invalido:11111; ";
        //programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.compruebaPermisos(login,passwor,Usuario.BIBLIOTECARIO)).andReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[0])
        );
        EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        control.replay();
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos
        control.verify();
        //informe
        assertEquals(esperado,real);

    }
    @Test
    void realizaReservaC4() {
        //datos de entrada
        String login="ppss";
        String passwor=login;
        String socio="Pepe";
        String [] isbns={"22222"};
        String esperado="SOCIO invalido; ";
        //programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.compruebaPermisos(login,passwor,Usuario.BIBLIOTECARIO)).andReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[0])
        );
        EasyMock.expectLastCall().andThrow(new SocioInvalidoException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        control.replay();
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos
        control.verify();
        //informe
        assertEquals(esperado,real);

    }

    @Test
    void realizaReservaC5() {
        //datos de entrada
        String login="ppss";
        String passwor=login;
        String socio="Pepe";
        String [] isbns={"11111","22222","33333"};
        String esperado="ISBN invalido:11111; CONEXION invalida; ";
        //programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.compruebaPermisos(login,passwor,Usuario.BIBLIOTECARIO)).andReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[0])
        );
        EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[1])
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[2])
        );
        EasyMock.expectLastCall().andThrow(new JDBCException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        control.replay();
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos
        control.verify();
        //informe
        assertEquals(esperado,real);

    }
}