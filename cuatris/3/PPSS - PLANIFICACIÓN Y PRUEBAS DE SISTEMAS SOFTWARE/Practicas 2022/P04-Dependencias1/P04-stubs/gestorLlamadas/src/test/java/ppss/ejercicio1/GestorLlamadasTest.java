package ppss.ejercicio1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestorLlamadasTest {

    @Test
    void calculaConsumoC1(){
        int minutos=10;
        int hora=15;
        int resulatdoEsperado=208;
        GestorLlamadasTestable doble=new GestorLlamadasTestable();//declaracion del doble
        doble.setResult(hora);
        assertEquals(resulatdoEsperado,doble.calculaConsumo(minutos));
    }
    @Test
    void calculaConsumoC2(){
        int minutos=10;
        int hora=22;
        int resulatdoEsperado=105;
        GestorLlamadasTestable doble=new GestorLlamadasTestable();
        doble.setResult(hora);
        assertEquals(resulatdoEsperado,doble.calculaConsumo(minutos));
    }

}