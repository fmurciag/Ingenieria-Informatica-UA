import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioStub extends Calendario{
    ArrayList<LocalDate> dias = new ArrayList<>();
    ArrayList<LocalDate> diasExepcion = new ArrayList<>();
    boolean result;
    public void setResult(ArrayList<LocalDate> dias,ArrayList<LocalDate> diasExepcion) {
        this.dias = dias;
        this.diasExepcion=diasExepcion;
    }

    @Override
    public boolean es_festivo(LocalDate otroDia) throws CalendarioException{

        if(diasExepcion.contains(otroDia)){
            throw new CalendarioException();
        }else if(dias.contains(otroDia)){
            return true;
        }
        return false;
    }
}
