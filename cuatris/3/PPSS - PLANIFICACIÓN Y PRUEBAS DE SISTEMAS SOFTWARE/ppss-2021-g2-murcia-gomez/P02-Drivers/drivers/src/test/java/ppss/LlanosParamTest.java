package ppss;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LlanosParamTest {


    @ParameterizedTest
    @MethodSource("casosDePrueba")
    void testParametrizado(ArrayList<Integer> arg, int origen, int longitud) {
        Llanos llanos = new Llanos();
        Tramo esperado = new Tramo(origen, longitud);
        Tramo tramo = llanos.buscarTramoLlanoMasLargo(arg);
        assertEquals(esperado,tramo);
    }



    private static Stream<Arguments> casosDePrueba(){
        Tramo c1=new Tramo();
        ArrayList<Integer> c1A =new ArrayList<>();
        c1A.add(3);
        ArrayList<Integer> c2A =new ArrayList<>();
        c2A.add(100); c2A.add(100);
        ArrayList<Integer> c3A =new ArrayList<>();
        c3A.add(180); c3A.add(180); c3A.add(180);
        ArrayList<Integer> c1B =new ArrayList<>();
        c1B.add(-1);
        ArrayList<Integer> c2B =new ArrayList<>();
        c2B.add(-1); c2B.add(-1);c2B.add(-1); c2B.add(-1);
        ArrayList<Integer> c3B =new ArrayList<>();
        c3B.add(120); c3B.add(140);c3B.add(-10); c3B.add(-10); c3B.add(-10);
        return Stream.of(
                Arguments.of(c1A, 0, 0),
                Arguments.of(c2A, 0, 1),
                Arguments.of(c3A, 0, 2),
                Arguments.of(c1B,0, 0),
                Arguments.of(c2B, 0, 3),
                Arguments.of(c3B, 2, 2)
        );
    }
}
