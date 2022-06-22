package ppss.ejercicio2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.ejercicio2.MyClass;

public class MyClassTest {

    @Test
    public void calcularKeyOK() {
        MyClass myclass = new MyClass();

        myclass.setId(2);
        myclass.setSecret(5);
        myclass.setName("My secret key");

        Assertions.assertEquals(myclass.calculateKey(),10);
    }

}
