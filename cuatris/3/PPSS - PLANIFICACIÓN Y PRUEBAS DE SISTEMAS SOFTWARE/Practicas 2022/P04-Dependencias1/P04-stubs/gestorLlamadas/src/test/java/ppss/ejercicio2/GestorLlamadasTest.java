package ppss.ejercicio2;

import org.junit.jupiter.api.Test;
import ppss.ejercicio1.GestorLlamadasTestable;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    @Test
    void calculaConsumoC1(){
        int minutos=10;
        int hora=15;
        int resulatdoEsperado=208;

        //declaracion del doble
        CalendarioStub doble=new CalendarioStub();
        doble.setResult(hora);

        //llamamos a la clase testable
        GestorLlamadasTestable claseTestable=new GestorLlamadasTestable();
        claseTestable.setcalendario(doble);

        assertEquals(resulatdoEsperado,claseTestable.calculaConsumo(minutos));
    }

    @Test
    void calculaConsumoC2(){
        int minutos=10;
        int hora=22;
        int resulatdoEsperado=105;

        //declaracion del doble
        CalendarioStub doble=new CalendarioStub();
        doble.setResult(hora);

        //llamamos a la clase testable
        GestorLlamadasTestable claseTestable=new GestorLlamadasTestable();
        claseTestable.setcalendario(doble);

        assertEquals(resulatdoEsperado,claseTestable.calculaConsumo(minutos));
    }
}