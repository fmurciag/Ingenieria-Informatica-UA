package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

public class OperacionStub extends Operacion{
    int tipoException;
    public void setResult(int tipoException) {
        this.tipoException = tipoException;
    }
    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        switch (tipoException){
            case 1:
                throw new JDBCException();
            case 2:
                throw new IsbnInvalidoException();
            case 3:
                throw new SocioInvalidoException();
            default:
        }
    }
}
