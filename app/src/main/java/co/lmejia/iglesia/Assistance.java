package co.lmejia.iglesia;

import java.util.Date;

/**
 * Created by luis on 2/2/15.
 */
public class Assistance {

    private long id;
    private Date fecha;
    private int hermanos;
    private int visitas;
    private int adolescentes;
    private int ninos;

    public Assistance() {
        this(new Date(), 0, 0, 0, 0);
    }

    public Assistance(Date fecha, int hermanos, int visitas, int adolescentes, int ninos) {
        this.fecha = fecha;
        this.hermanos = hermanos;
        this.visitas = visitas;
        this.adolescentes = adolescentes;
        this.ninos = ninos;
    }

    public int getTotal() {
        return hermanos + visitas + adolescentes + ninos;
    }

    /**
     *
     * SETTERS AND GETTERS
     *
     * */

    public void setId(long id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHermanos(int hermanos) {
        this.hermanos = hermanos;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public void setAdolescentes(int adolescentes) {
        this.adolescentes = adolescentes;
    }

    public void setNinos(int ninos) {
        this.ninos = ninos;
    }

    public long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getHermanos() {
        return hermanos;
    }

    public int getVisitas() {
        return visitas;
    }

    public int getAdolescentes() {
        return adolescentes;
    }

    public int getNinos() {
        return ninos;
    }

}
