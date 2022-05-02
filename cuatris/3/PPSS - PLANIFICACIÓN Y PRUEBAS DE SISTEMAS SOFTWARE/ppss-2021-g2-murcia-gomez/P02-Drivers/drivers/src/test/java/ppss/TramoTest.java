package ppss;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TramoTest {
    Tramo trFinal=new Tramo();
    Llanos llanos=new Llanos();
    @Test
    void C1B_buscarTramoLlanoMasLargo(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(-1);
        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(0,trFinal.getLongitud()),
                ()->assertEquals(0,trFinal.getOrigen())
        );
    }
    @Test
    void C2B_buscarTramoLlanoMasLargo(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(-1);
        lecturas.add(-1);
        lecturas.add(-1);
        lecturas.add(-1);
        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(3,trFinal.getLongitud()),
                ()->assertEquals(0,trFinal.getOrigen())
        );
    }
    @Test
    void C3B_buscarTramoLlanoMasLargo(){
        ArrayList<Integer> lecturas =new ArrayList<>();
        lecturas.add(120);
        lecturas.add(140);
        lecturas.add(-10);
        lecturas.add(-10);
        lecturas.add(-10);

        Tramo trFinal=llanos.buscarTramoLlanoMasLargo(lecturas);
        assertAll(
                ()->assertEquals(2,trFinal.getLongitud()),
                ()->assertEquals(2,trFinal.getOrigen())
        );
    }
}