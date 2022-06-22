package ppss;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    //excluidos
    @Test
    void contarCaracteresC1() {
        FicheroException ex = assertThrows(FicheroException.class,
                () -> (new FicheroTexto()).contarCaracteres("ficheroC1.txt"));
        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", ex.getMessage());
    }

    @Test
    void contarCaracteresC2(){
        String path="src/test/resources/ficheroCorrecto.txt";

        FicheroTexto ft=new FicheroTexto();


        assertDoesNotThrow( ()->  assertEquals(3, ft.contarCaracteres(path)));
    }

    @Test
    @Tag("excluidos")
    void contarCaracteresC3(){

    }

    @Test
    @Tag("excluidos")
    void contarCaracteresC4(){

    }
}