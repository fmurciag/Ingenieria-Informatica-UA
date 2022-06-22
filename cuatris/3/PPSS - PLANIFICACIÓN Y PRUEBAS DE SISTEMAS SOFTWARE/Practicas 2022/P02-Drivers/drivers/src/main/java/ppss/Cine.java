package ppss;

public class Cine {
    public boolean reservaButacasV1(boolean[] asientos, int solicitados) {
        boolean reserva = false;
        int j = 0;
        int sitiosLibres = 0;
        int primerLibre;

        while ((j < asientos.length) && (sitiosLibres < solicitados)) {
            if (!asientos[j]) {
                sitiosLibres++;
            } else {
                sitiosLibres = 0;
            }
            j++;
        }
        if (sitiosLibres == solicitados) {
            primerLibre = (j - solicitados);
            reserva = true;
            for (int k = primerLibre; k < (primerLibre + solicitados); k++) {
                asientos[k] = true;
            }
        }

        return reserva;
    }


    //solucion
    public boolean reservaButacasV2(boolean[] asientos, int solicitados) {
        boolean reserva = false;
        int j = 0;
        int sitiosLibres = 0;
        int primerLibre;
        if (solicitados!=0){// condicion para el caso de prueba c2
            while ((j < asientos.length) && (sitiosLibres < solicitados)) {
                if (!asientos[j]) {
                    sitiosLibres++;
                } else {
                    sitiosLibres = 0;
                }
                j++;
            }
            if (sitiosLibres == solicitados) {
                primerLibre = (j - solicitados);
                reserva = true;
                for (int k = primerLibre; k < (primerLibre + solicitados); k++) {
                    asientos[k] = true;
                }
            }
        }


        return reserva;
    }
}
