package ppss;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatriculaParamTest {
    @ParameterizedTest
    @MethodSource("casosDePrueba")
    void testParametrizado(float esperado, int edad, boolean Fnumerosa, boolean repetidor) {
        matricula mat = new matricula();
        assertEquals(esperado, mat.calculaTasaMatricula(edad, Fnumerosa, repetidor));
    }
    private static Stream<Arguments> casosDePrueba(){
        return Stream.of(
                Arguments.of(2000.00f, 19, false, true),
                Arguments.of(250.00f, 68, false, true),
                Arguments.of(250.00f, 19, true, true),
                Arguments.of(500.00f, 19, false, false),
                Arguments.of(400.00f, 61, false, false)
        );
    }



}
