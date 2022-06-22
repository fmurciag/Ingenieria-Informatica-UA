package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.partialMockBuilder;
import static org.junit.jupiter.api.Assertions.*;

class PremioTest {
    ClienteWebService mock;
    Premio sut;
    IMocksControl ctrl = EasyMock.createStrictControl();//controlador para controlar el orden de invocacion
    @BeforeEach
    void crearMocks(){

        sut = partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(ctrl);
        mock=ctrl.createMock(ClienteWebService.class);
        //inyectamos al sut
        sut.cliente=mock;
    }
    @Test
    void compruebaPremioC1() {
        //1 datos de entrada
        float aleatorio = (float) 0.07;
        String premio="entrada final Champions";
        String esperado="Premiado con entrada final Champions";

        //2 creamos los mocks
                //@BeforeEach
        //promamos las expectativas
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);
        assertDoesNotThrow(
                ()->EasyMock.expect(mock.obtenerPremio()).andReturn(premio)
        );
        //3 inyectar el doble
            //@BeforeEach
        //4 activamos el mock
        ctrl.replay();

        //5 invocamos al sut
        String real=sut.compruebaPremio();

        //6 verificar
        ctrl.verify();

        //7 informe
        assertEquals(esperado,real);
    }
    @Test
    void compruebaPremioC2() {
        //1 datos de entrada
        float aleatorio = (float) 0.03;
        String esperado="No se ha podido obtener el premio";

        //2 creamos los mocks
        //@BeforeEach
        //promamos las expectativas
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);
        assertDoesNotThrow(
                () -> EasyMock.expect(mock.obtenerPremio()).andThrow(new ClienteWebServiceException()));

        //3 inyectar el doble
        //@BeforeEach
        //4 activamos el mock
        ctrl.replay();;

        //5 invocamos al sut
        String real=sut.compruebaPremio();

        //6 verificar
        ctrl.verify();

        //7 informe
        assertEquals(esperado,real);
    }

    @Test
    void compruebaPremioC3() {
        //1 datos de entrada
        float aleatorio = (float) 0.3;
        String esperado="Sin premio";

        //2 creamos los mocks
        //@BeforeEach
        //promamos las expectativas
        EasyMock.expect(sut.generaNumero()).andReturn(aleatorio);

        //3 inyectar el doble
        //@BeforeEach
        //4 activamos el mock
        ctrl.replay();

        //5 invocamos al sut
        String real=sut.compruebaPremio();

        //6 verificar
        ctrl.verify();

        //7 informe
        assertEquals(esperado,real);
    }
}