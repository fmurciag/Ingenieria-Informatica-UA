package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.partialMockBuilder;
import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    Calendario mock;
    GestorLlamadas sut;
    IMocksControl control;

    @BeforeEach
    void crearMocks(){
        control=EasyMock.createStrictControl();
        sut=partialMockBuilder(GestorLlamadas.class).addMockedMethod("getCalendario").mock(control);
        mock=control.createMock(Calendario.class);
    }

    @Test
    void calculaConsumoC1() {
        //1 datos de entrada
        int minutos =22;
        int horas=10;
        double esperado=457.6;


        //2 creamos los mocks
            //beforeEach
        //promamos las expectativas
        EasyMock.expect(sut.getCalendario()).andReturn(mock);
        assertDoesNotThrow(
                ()->EasyMock.expect(mock.getHoraActual()).andReturn(horas)
        );

        //3 inyectar el doble
            //ya se hace  en la expectativa

        //4 activamos el mock
        control.replay();

        //5 invocamos al sut
        double real=sut.calculaConsumo(minutos);

        //6 verificar
        control.verify();

        //7 informe
        assertEquals(esperado,real);
    }

    @Test
    void calculaConsumoC2() {
        //1 datos de entrada
        int minutos =12+1;
        int horas=21;
        double esperado=136.5;


        //2 creamos los mocks
            //beforeEach
        //promamos las expectativas
        EasyMock.expect(sut.getCalendario()).andReturn(mock);
        assertDoesNotThrow(
                ()->EasyMock.expect(mock.getHoraActual()).andReturn(horas)
        );


        //3 inyectar el doble
        //ya se hace  en la expectativa

        //4 activamos el mock
        control.replay();

        //5 invocamos al sut
        double real=sut.calculaConsumo(minutos);

        //6 verificar
        control.verify();

        //7 informe
        assertEquals(esperado,real);
    }

}