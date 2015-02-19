package co.lmejia.iglesia;

import java.util.Date;

/**
 * Created by luis on 2/2/15.
 */
public class Assistance {

    public Date fecha;
    public long id;
    public int hermanos;
    public int visitas;
    public int adolescentes;
    public int ninos;

    public int image;

    public Assistance(Date fecha, int hermanos, int visitas, int adolescentes, int ninos, int image) {
        this.fecha = fecha;
        this.hermanos = hermanos;
        this.visitas = visitas;
        this.adolescentes = adolescentes;
        this.ninos = ninos;
        this.image = image;

    }

    public int getTotal() {
        return hermanos + visitas + adolescentes + ninos;
    }
}
