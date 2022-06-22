package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    IMocksControl control;
    FileReader mock;
    FicheroTexto sut;

    @BeforeEach
    void crearMocks(){
        control= EasyMock.createStrictControl();
        sut=partialMockBuilder(FicheroTexto.class).addMockedMethod("ficheroFactory").mock(control);
        mock=control.createMock(FileReader.class);
    }

    @Test
    void contarCaracteresC1() {
        //1 datos de entrada
        String nombreFichero = "src/test/resources/ficheroC1.txt";
        String esperado ="src/test/resources/ficheroC1.txt (Error al leer el archivo)";

        //2 creamos los dobles
            //beforeeach
        //3 programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.ficheroFactory(nombreFichero)).andReturn(mock)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mock.read()).andReturn((int)'a').andReturn((int)'b').andThrow(new IOException())
        );
        //4 inyectamos
        //5 activamos el mock
        control.replay();
        //6 invocamos al sut

        String ex =assertThrows(FicheroException.class,()->sut.contarCaracteres(nombreFichero)).getMessage();
        // 7 verificar
        control.verify();
        //8 informe
        assertEquals(esperado,ex);
    }

    @Test
    void contarCaracteresC2() {
        //1 datos de entrada
        String nombreFichero = "src/test/resources/ficheroC2.txt";
        String esperado ="src/test/resources/ficheroC2.txt (Error al cerrar el archivo)";

        //2 creamos los dobles
        //beforeeach
        //3 programamos las expectativas
        assertDoesNotThrow(
                ()->EasyMock.expect(sut.ficheroFactory(nombreFichero)).andReturn(mock)
        );
        assertDoesNotThrow(
                ()->EasyMock.expect(mock.read()).andReturn((int)'a').andReturn((int)'b').andReturn((int)'c').andReturn(-1)
        );
        assertDoesNotThrow(()->mock.close());

        EasyMock.expectLastCall().andThrow(new IOException());

        //4 inyectamos
        //5 activamos el mock
        control.replay();
        //6 invocamos al sut

        String ex =assertThrows(FicheroException.class,()->sut.contarCaracteres(nombreFichero)).getMessage();
        // 7 verificar
        control.verify();
        //8 informe
        assertEquals(esperado,ex);
    }
}