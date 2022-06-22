package ppss;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ppss.Cine;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CineTest {

    @Test
    void reservaButacasC1(){
        boolean asientos[] = new boolean[0];
        int solicitado= 3;

        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV1(asientos,solicitado);
        boolean resultadoEsperadoAray[] = new boolean[0];
        assertAll(
                ()->assertEquals(false,resultado),
                ()->assertArrayEquals(resultadoEsperadoAray,asientos)
        );
    }

    //@Test
    void reservaButacasC2(){
        boolean asientos[] = new boolean[0];
        int solicitado= 0;

        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV1(asientos,solicitado);
        boolean resultadoEsperadoAray[] = new boolean[0];
        assertAll(
                ()->assertEquals(false,resultado),
                ()->assertArrayEquals(resultadoEsperadoAray,asientos)
        );
    }
    @Test
    void reservaButacasC2_fix(){
        boolean asientos[] = new boolean[0];
        int solicitado= 0;

        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV2(asientos,solicitado);
        boolean resultadoEsperadoAray[] = new boolean[0];
        assertAll(
                ()->assertEquals(false,resultado),
                ()->assertArrayEquals(resultadoEsperadoAray,asientos)
        );
    }

    @Test
    void reservaButacasC3(){
        boolean asientos[] = {false, false, false, true, true};
        int solicitado= 2;

        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV1(asientos,solicitado);
        boolean resultadoEsperadoAray[] = {true, true, false, true, true};
        assertAll(
                ()->assertEquals(true,resultado),
                ()->assertArrayEquals(resultadoEsperadoAray,asientos)
        );
    }

    @Test
    void reservaButacasC4(){
        boolean asientos[] = {true,true,true};
        int solicitado= 1;

        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV1(asientos,solicitado);
        boolean resultadoEsperadoAray[] = {true,true,true};
        assertAll(
                ()->assertEquals(false,resultado),
                ()->assertArrayEquals(resultadoEsperadoAray,asientos)
        );
    }

    @ParameterizedTest
    @MethodSource("testParametrizado")
    @Tag("parametrizado")
    void reservaButacasC5(boolean[] asientosExpected, boolean resultExpected,boolean[] asientos, int solicitado){
        Cine cine=new Cine();
        boolean resultado=cine.reservaButacasV2(asientos,solicitado);
        assertAll(
                ()->assertEquals(resultExpected,resultado),
                ()->assertArrayEquals(asientosExpected,asientos)
        );
    }

    private static Stream<Arguments> testParametrizado(){
        boolean c1Expected[] = new boolean[0];
        boolean c1[] = new boolean[0];
        boolean c2Expected[] = new boolean[0];
        boolean c2[] = new boolean[0];
        boolean c3[] = {false, false, false, true, true};
        boolean c3Expected[] = {true, true, false, true, true};
        boolean c4Expected[] = {true,true,true};
        boolean c4[] = {true,true,true};
        return Stream.of(
                Arguments.of(c1Expected, false, c1,3),
                Arguments.of(c2Expected, false, c2,0),
                Arguments.of(c3Expected, true, c3,2),
                Arguments.of(c4Expected, false, c4,1)
        );

    }
}