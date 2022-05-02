package ppss;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LlanosTest {
    Llanos llanos=new Llanos();
    Tramo tramo =new Tramo();
    @Test
    void C1A_buscarTramoLlano(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(3);
        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(0,trFinal.getLongitud()),
                ()->assertEquals(0,trFinal.getOrigen())
        );
    }
    @Test
    void C2A_buscarTramoLlano(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(100);
        lecturas.add(100);
        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(1,trFinal.getLongitud()),
                ()->assertEquals(0,trFinal.getOrigen())
        );
    }
    @Test
    void C3A_buscarTramoLlano(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(180);
        lecturas.add(180);
        lecturas.add(180);
        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(2,trFinal.getLongitud()),
                ()->assertEquals(0,trFinal.getOrigen())
        );
    }
}