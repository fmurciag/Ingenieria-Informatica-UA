package ppss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class matriculaTest {
    @Test
    void calculaTasaMatriculaC1(){
        matricula mad=new matricula();
        assertEquals(2000.00f, mad.calculaTasaMatricula(19,false,true));
    }
    @Test
    void calculaTasaMatriculaC2(){
        matricula mad=new matricula();
        assertEquals(250.00f, mad.calculaTasaMatricula(68,false,true));
    }
    @Test
    void calculaTasaMatriculaC3(){
        matricula mad=new matricula();
        assertEquals(250.00f, mad.calculaTasaMatricula(19,true,true));
    }
    @Test
    void calculaTasaMatriculaC4(){
        matricula mad=new matricula();
        assertEquals(500.00f, mad.calculaTasaMatricula(19,false,false));
    }
    @Test
    void calculaTasaMatriculaC5(){
        matricula mad=new matricula();
        assertEquals(400.00f, mad.calculaTasaMatricula(60,true,true));
    }
}