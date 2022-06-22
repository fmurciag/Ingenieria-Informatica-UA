package ppss.ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ppss.ejercicio1.MultipathExample;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MultipathExampleTest {

    @Test
    public void C1_Multiplicacion(){
        int a = 6;
        int b=6;
        int c=0;

        int esperado =12;
        int real= new MultipathExample().multiPath1(a,b,c);
        Assertions.assertEquals(esperado,real);
    }

    @Test
    public void C2_Multiplicacion(){
        int a = 3;
        int b=3;
        int c=0;

        int esperado =0;
        int real= new MultipathExample().multiPath1(a,b,c);
        Assertions.assertEquals(esperado,real);
    }

    @Test
    public void C3_Multiplicacion(){
        int a = 3;
        int b=6;
        int c=2;

        int esperado =8;
        int real= new MultipathExample().multiPath1(a,b,c);
        Assertions.assertEquals(esperado,real);
    }

    @ParameterizedTest
    @MethodSource("testParametrizadoMultiPath2")
    void testMultiPath2(int a, int b, int c, int result){
        assertEquals(result, new MultipathExample().multiPath2(a,b,c));
    }
    private static Stream<Arguments> testParametrizadoMultiPath2(){
        return Stream.of(
                Arguments.of(6,4,6,16), //todo true
                Arguments.of(5,5,5,5), //no entra en el if
                Arguments.of(6,5,6,11)  // T F , T
        );
    }

    @ParameterizedTest
    @MethodSource("testParametrizadoMultiPath3")
    void testMultiPath3(int a, int b, int c, int result){
        assertEquals(result, new MultipathExample().multiPath3(a,b,c));
    }

    private static Stream<Arguments> testParametrizadoMultiPath3(){

        return Stream.of(
                Arguments.of(6,4,6,16), //todo true
                Arguments.of(5,5,5,5), //no entra en el if
                Arguments.of(6,5,6,11)  // T F , T
        );

    }



}