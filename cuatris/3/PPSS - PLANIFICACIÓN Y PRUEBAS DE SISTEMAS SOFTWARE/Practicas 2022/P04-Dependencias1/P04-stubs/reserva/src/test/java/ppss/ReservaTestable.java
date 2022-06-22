package ppss;

public class ReservaTestable extends Reserva{
    boolean permiso;
    public void setPermiso(boolean permiso) {
        this.permiso = permiso;
    }
    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        return permiso;
    }
}
