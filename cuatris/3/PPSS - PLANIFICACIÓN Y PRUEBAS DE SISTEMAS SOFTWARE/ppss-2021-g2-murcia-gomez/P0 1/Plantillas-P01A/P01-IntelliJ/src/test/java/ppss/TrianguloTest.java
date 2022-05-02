package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrianguloTest {
    int a,b,c;
    String resultadoReal, resultadoEsperado;
    Triangulo tri= new Triangulo();

    @Test
    public void testTipo_trianguloC1() {
       a = 1;
       b = 1;
       c = 1;
       resultadoEsperado = "Equilatero";
       resultadoReal = tri.tipo_triangulo(a,b,c);
       assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    /**
     * necesario, puede fallar la condicion (c >= (a+b))
     */
    public void testTipo_trianguloC2() {
        a = 1;
        b = 1;
        c = 11;
        resultadoEsperado = "No es un triangulo";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        //El método Assert.assertEquals devuelve cierto si el
        //resultadoEsperado = resultadoReal
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    /**
     * necesario, puede fallar la condicion ((c <1) || (c > 200))
     */
    public void testTipo_trianguloC3() {
        a = 1;
        b = 2;
        c = 0;
        resultadoEsperado = "Valor c sobrepasa del rango permitido";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }


    @Test
    public void testTipo_trianguloC4() {
        a = 14;
        b = 10;
        c = 10;
        resultadoEsperado = "Isosceles";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }


    //nuevos test

/*    @Test
    public void testTipo_trianguloC5() {
        a = 7;
        b = 7;
        c = 7;
        resultadoEsperado = "Equilatero";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }
    es inutil porque ya comprobamos si es equilatero
 */



    @Test
    /**
     * comprobacion de si es escaleno
     */
    public void testTipo_trianguloC6() {
        a = 14;
        b = 10;
        c = 12;
        resultadoEsperado = "Escaleno";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }
    @Test
    /**
     * comprobamos si (c > 200)
     */
    public void testTipo_trianguloC7() {
        a = 1;
        b = 2;
        c = 500;
        resultadoEsperado = "Valor c sobrepasa del rango permitido";
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

}
