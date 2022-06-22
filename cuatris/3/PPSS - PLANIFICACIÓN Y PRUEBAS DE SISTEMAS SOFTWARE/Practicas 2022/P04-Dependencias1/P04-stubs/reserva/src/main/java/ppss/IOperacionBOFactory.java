package ppss;

import ppss.IOperacionBO;
import ppss.Operacion;

public class IOperacionBOFactory {
    private static IOperacionBO io= null;
    public static IOperacionBO create() {
        if (io != null) {
            return io;
        } else {
            return new Operacion() ;
            
        }
    }
    public static void setServicio (IOperacionBO srv){
        io = srv;
    }
}
