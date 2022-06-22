package ppss;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DataArrayTest {
    @Test
    void dataArrayC1() {
        DataArray dr=new DataArray(new int[]{1,3,5,7});
        int[] arraySalida = {1,3,7};
        int elementoEsperado = 3;
        assertDoesNotThrow(() -> dr.delete(5), "Excepción lanzada");
        assertAll("TestC1",
                () -> assertArrayEquals(arraySalida, dr.getColeccion()),
                () -> assertEquals(elementoEsperado, dr.size())
        );
    }
    @Test
    void dataArrayC2() {
        DataArray dr=new DataArray(new int[]{1,3,3,5,7});
        int[] arraySalida = {1,3,5,7};
        int elementoEsperado = 4;
        assertDoesNotThrow(() -> dr.delete(3), "Excepción lanzada");
        assertAll("TestC1",
                () -> assertArrayEquals(arraySalida, dr.getColeccion()),
                () -> assertEquals(elementoEsperado, dr.size())
        );
    }

    @Test
    void dataArrayC3() {
        DataArray dr=new DataArray(new int[]{1,2,3,4,5,6,7,8,9,10});
        int[] arraySalida = {1,2,3,5,6,7,8,9,10};
        int elementoEsperado = 9;
        assertDoesNotThrow(() -> dr.delete(4), "Excepción lanzada");
        assertAll("TestC1",
                () -> assertArrayEquals(arraySalida, dr.getColeccion()),
                () -> assertEquals(elementoEsperado, dr.size())
        );
    }
    @Test
    void dataArrayC4() {
        DataArray dr=new DataArray(new int[]{});
        int[] arraySalida = new int[0];
        int elementoEsperado = 0;
        DataException ex = assertThrows(DataException.class,
                () -> dr.delete(8));
        assertEquals("No hay elementos en la colección", ex.getMessage());
    }

    @Test
    void dataArrayC5() {
        DataArray dr=new DataArray(new int[]{1,3,5,7});
        int[] arraySalida = {1,3,5,7};
        int elementoEsperado = 4;
        DataException ex = assertThrows(DataException.class,
                () -> dr.delete(-5));
        assertEquals("El valor a borrar debe ser > 0", ex.getMessage());
    }

    @Test
    void dataArrayC6() {
        DataArray dr=new DataArray(new int[]{});
        int[] arraySalida = {1,2,3,5,6,7,8,9,10};
        int elementoEsperado = 0;
        DataException ex = assertThrows(DataException.class,
                () -> dr.delete(0));
        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", ex.getMessage());
    }

    @Test
    void dataArrayC7() {
        DataArray dr=new DataArray(new int[]{1,3,5,7});
        int[] arraySalida = {1,2,3,5,6,7,8,9,10};
        int elementoEsperado = 0;
        DataException ex = assertThrows(DataException.class,
                () -> dr.delete(8));
        assertEquals("Elemento no encontrado", ex.getMessage());
    }

    @ParameterizedTest
    @MethodSource("testParametrizadoC4C7")
    @Tag("parametrizado")
    @Tag("conExepciones")
    void testParametrizadoC8(String msError,int[] array, int elemento){
        DataArray dr=new DataArray(array);
        DataException ex = assertThrows(DataException.class,
                () -> dr.delete(elemento));
        assertEquals(msError, ex.getMessage());
    }

    private static Stream<Arguments> testParametrizadoC4C7(){
        int c4[] = new int[0];
        int c5[] = {1,3,5,7};
        int c6[] = new int[0];
        int c7[] = {1,3,5,7};
        return Stream.of(
                Arguments.of("No hay elementos en la colección", c4, 8),
                Arguments.of("El valor a borrar debe ser > 0", c5, 0),
                Arguments.of("Colección vacía. Y el valor a borrar debe ser > 0", c6, -5),
                Arguments.of("Elemento no encontrado", c7,8)
        );

    }

    @ParameterizedTest
    @MethodSource("testParametrizadoC1C3")
    @Tag("parametrizado")
    @Tag("conExepciones")
    void testParametrizadoC9(int[] arrayEsperado,int elemEsperado,int[] array, int borrar){
        DataArray dr=new DataArray(array);
        assertDoesNotThrow(() -> dr.delete(borrar), "Excepción lanzada");
        assertAll("TestC1",
                () -> assertArrayEquals(arrayEsperado, dr.getColeccion()),
                () -> assertEquals(elemEsperado, dr.size())
        );
    }

    private static Stream<Arguments> testParametrizadoC1C3(){
        int c1[] = {1,3,5,7};
        int c1Esperado[] = {1,3,7};
        int c2[] = {1,3,3,5,7};
        int c2Esperado[] = {1,3,5,7};
        int c3[] = {1,2,3,4,5,6,7,8,9,10};
        int c3Esperado[] = {1,2,3,5,6,7,8,9,10};
        return Stream.of(
                Arguments.of(c1Esperado, 3,c1, 5),
                Arguments.of(c2Esperado, 4,c2, 3),
                Arguments.of(c3Esperado, 9,c3, 4)
        );

    }


}