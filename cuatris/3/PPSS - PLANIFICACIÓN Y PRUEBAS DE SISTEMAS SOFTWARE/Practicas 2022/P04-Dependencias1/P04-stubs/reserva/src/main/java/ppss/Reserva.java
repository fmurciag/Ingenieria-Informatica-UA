package ppss;

import ppss.excepciones.*;

import java.util.ArrayList;

public class Reserva {

    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //codigo refactorizado
    public void realizaReserva(String login, String password,
                               String socio, String [] isbns) throws Exception {


        ArrayList<String> errores = new ArrayList<>();
        if(!compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)) {/////////dependencia
            errores.add("ERROR de permisos");
        } else {
            IOperacionBO io = IOperacionBOFactory.create();//creamos la clase conla clase factoria
            try {
                for(String isbn: isbns) {
                    try {
                        io.operacionReserva(socio, isbn);//////////dependencia
                    } catch (IsbnInvalidoException iie) {
                        errores.add("ISBN invalido" + ":" + isbn);
                    }
                }
            } catch (SocioInvalidoException sie) {
                errores.add("SOCIO invalido");
            } catch (JDBCException je) {
                errores.add("CONEXION invalida");
            }
        }
        if (errores.size() > 0) {
            String mensajeError = "";
            for(String error: errores) {
                mensajeError += error + "; ";
            }
            throw new ReservaException(mensajeError);
        }
    }
}
