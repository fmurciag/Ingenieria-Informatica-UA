package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservaStubTest {
    Reserva sut;
    FactoriaBOs mockFactoria;
    IOperacionBO mockIOpe;
    @BeforeEach
    void crearMocks(){
        sut=partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").niceMock();
        mockFactoria=EasyMock.niceMock(FactoriaBOs.class);
        mockIOpe=EasyMock.niceMock(IOperacionBO.class);
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
                ()->EasyMock.expect(sut.compruebaPermisos(anyString(),anyString(),anyObject(Usuario.class))).andStubReturn(false)
        );
        //activamos el mock
        EasyMock.replay(sut,mockFactoria,mockIOpe);
        //invocamos al sut
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();

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
                ()->EasyMock.expect(sut.compruebaPermisos(anyString(),anyString(),anyObject(Usuario.class))).andStubReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andStubReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(anyString(),anyString())
        );
        EasyMock.expectLastCall();
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(anyString(),anyString())
        );
        EasyMock.expectLastCall();
        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        EasyMock.replay(sut,mockFactoria,mockIOpe);
        //invocamos al sut y el informe
        assertDoesNotThrow(
                ()->sut.realizaReserva(login,passwor,socio,isbns)
        );
        //verificamos

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
                ()->EasyMock.expect(sut.compruebaPermisos(anyString(),anyString(),anyObject())).andStubReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andStubReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(anyString(),anyString())
        );
        EasyMock.expectLastCall().andStubThrow(new IsbnInvalidoException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        EasyMock.replay(sut,mockFactoria,mockIOpe);
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos

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
                ()->EasyMock.expect(sut.compruebaPermisos(anyString(),anyString(),anyObject())).andStubReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andStubReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(anyString(),anyString())
        );
        EasyMock.expectLastCall().andStubThrow(new SocioInvalidoException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        EasyMock.replay(sut,mockFactoria,mockIOpe);
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos

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
                ()->EasyMock.expect(sut.compruebaPermisos(anyString(),anyString(),anyObject())).andStubReturn(true)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mockFactoria.getOperacionBO()).andStubReturn(mockIOpe)
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[0])
        );
        EasyMock.expectLastCall().andStubThrow(new IsbnInvalidoException());
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[1])
        );
        assertDoesNotThrow(
                ()->mockIOpe.operacionReserva(socio,isbns[2])
        );
        EasyMock.expectLastCall().andStubThrow(new JDBCException());

        //inyectamos
        sut.fd=mockFactoria;
        //activamos el mock
        EasyMock.replay(sut,mockFactoria,mockIOpe);
        //invocamos al sut y el informe
        String real =assertThrows(ReservaException.class,()->sut.realizaReserva(login,passwor,socio,isbns)).getMessage();
        //verificamos
        //informe
        assertEquals(esperado,real);

    }
}