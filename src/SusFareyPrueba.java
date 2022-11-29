import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class SusFareyPrueba {

    static List<Fraccion> fracciones;


    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();


        fracciones = new ArrayList<Fraccion>();
        Scanner lectura = new Scanner(System.in);

        System.out.println("Ingrese numero de maquinas: ");

        String maquinas = lectura.next();

        lectura.reset();

        System.out.println("Ingrese numero para calcular sucesión de Farey: ");

        String numero = lectura.next();

        //System.out.println("Maquinas: " + maquinas);
        System.out.println("Numero: " + numero);

        int num = parseInt(numero);
        for(int i=1; i< num+ 1 ; i++)
        {
            for(int j=1; j<num  + 1 ; j++) {
                fracciones.add(ConstruccionFracciones(i,j));
            }
        }
        //ImprimirFracciones(fracciones);
        System.out.println("");
        System.out.println("Eliminacion fracciones > 1: ");


        int busqueda = 0;
        while (busqueda != fracciones.size()) {

            if (EliminarMayores(fracciones.get(busqueda).numerador, fracciones.get(busqueda).denominador)) {
                //System.out.println("Se debe eliminar:" + fracciones.get(busqueda).fraccion);
                fracciones.remove(busqueda);
                //ImprimirFracciones(fracciones);
                busqueda = busqueda - 1;
            } else {
                busqueda = busqueda + 1;
            }


        }
        //ImprimirFracciones(fracciones);
        System.out.println("");
        System.out.println("Eliminacion fracciones repetidas: ");
        busqueda = 0;
        while (busqueda != fracciones.size()) {
            int valor = BuscarRepetidos(fracciones, busqueda);
            if (valor != 0) {
                //System.out.println("Se debe eliminar repetido:" + fracciones.get(valor).fraccion);
                fracciones.remove(valor);
                //ImprimirFracciones(fracciones);

            } else {
                busqueda = busqueda + 1;
            }


        }
        System.out.println("");
        System.out.println("Ordenamiento: ");
        fracciones.add(AgregarFinal());
        fracciones = Ordenamiento(fracciones);
        ImprimirFracciones(fracciones);



        long fin = System.currentTimeMillis();

        double tiempo = (double) ((fin - inicio)/1000);

        System.out.println(tiempo +" segundos");

    }

    public static Fraccion ConstruccionFracciones(Integer numerador, Integer denominador) {

        Fraccion fraccion = new Fraccion();
        fraccion.fraccion = Integer.toString(numerador) + "/" + Integer.toString(denominador);
        fraccion.numerador = numerador;
        fraccion.denominador = denominador;
        return fraccion;
    }

    public static Fraccion AgregarFinal() {

        Fraccion fraccion = new Fraccion();
        fraccion.fraccion = 0 + "/" + 1;
        fraccion.numerador = 0;
        fraccion.denominador = 1;
        return fraccion;
    }

    public static boolean EliminarMayores(Float numerador, Float denominador) {

        return numerador > denominador;
    }

    public static void ImprimirFracciones(List<Fraccion> fracciones) {
        System.out.println("");
        System.out.print("Fracciones: ");

        System.out.print(fracciones.get(0).getFraccion());

        for (int j = 1; j < fracciones.size(); j++) {
            System.out.print("," + fracciones.get(j).getFraccion());
        }
        System.out.println("");

    }

    public static int BuscarRepetidos(List<Fraccion> fracciones, int posicion) {

        for (int j = 0; j < fracciones.size(); j++) {
            if (j != posicion && (fracciones.get(j).numerador / fracciones.get(j).denominador) == (fracciones.get(posicion).numerador / fracciones.get(posicion).denominador)) {
                return j;
            }
        }
        return 0;
    }

    public static List<Fraccion> Ordenamiento(List<Fraccion> fracciones) {

        for (int i = 0; i < fracciones.size(); i++) {
            for (int j = 0; j < fracciones.size(); j++) {
                if (j > i && (fracciones.get(j).numerador / fracciones.get(j).denominador) < (fracciones.get(i).numerador / fracciones.get(i).denominador)) {
                    Fraccion tempFraccion = new Fraccion();

                    tempFraccion.numerador = fracciones.get(i).numerador;
                    tempFraccion.denominador = fracciones.get(i).denominador;
                    tempFraccion.fraccion = fracciones.get(i).fraccion;

                    fracciones.get(i).numerador = fracciones.get(j).numerador;
                    fracciones.get(i).denominador = fracciones.get(j).denominador;
                    fracciones.get(i).fraccion = fracciones.get(j).fraccion;

                    fracciones.get(j).numerador = tempFraccion.numerador;
                    fracciones.get(j).denominador = tempFraccion.denominador;
                    fracciones.get(j).fraccion = tempFraccion.fraccion;

                }
            }
        }

        return fracciones;
    }

    public static class Fraccion {
        String fraccion;
        float numerador;
        float denominador;

        public Fraccion() {
            this.fraccion = "";
        }

        public Fraccion(String fraccion) {
            this.fraccion = fraccion;
        }

        public String getFraccion() {
            return fraccion;
        }

        public void setFraccion(String fraccion) {
            this.fraccion = fraccion;
        }

    }
}
