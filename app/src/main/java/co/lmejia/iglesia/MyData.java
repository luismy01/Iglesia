package co.lmejia.iglesia;

/**
 * Created by luis on 2/7/15.
 */
public class MyData {

    static Integer[] hermanos = {10, 20, 30, 40, 50, 60 , 70, 80, 90, 100};
    static Integer[] visitas = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
    static Integer[] adolescentes = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
    static Integer[] ninos = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

    static String[] months_name = {"Meses", "Enero", "Febrero","Marzo", "Abril", "Mayo", "Junio"
            , "Julio", "Agosto", "Septiembre", "Obtubre", "Noviembre", "Diciembre"};

    static String getMonthName (int month) {
        return months_name[month];
    }

}
