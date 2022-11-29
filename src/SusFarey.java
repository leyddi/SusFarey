import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class SusFarey extends ThreadGroup implements Runnable{

    static List<Fraccion> fracciones;
    static List<Fraccion> fraccionesNew = new ArrayList<Fraccion>();
    int numcliente; int nummaximo;
    public SusFarey(String name,int cliente, int maximo) {
        super(name);
        this.numcliente = cliente;
        this.nummaximo = maximo;
    }

    public void CalcularSusFarey(int cliente, int maximo) {
        int busqueda =cliente * maximo;
        int inicial = busqueda;
        while(busqueda < fracciones.size() && busqueda <= inicial + maximo){

            if(!EliminarMayores(fracciones.get(busqueda).numerador, fracciones.get(busqueda).denominador)){
                Fraccion fraccion = new Fraccion();

                fraccion.numerador = fracciones.get(busqueda).numerador;
                fraccion.denominador = fracciones.get(busqueda).denominador;
                fraccion.fraccion = fracciones.get(busqueda).fraccion;
                fraccionesNew.add(fraccion);
            }
            busqueda = busqueda + 1;
            //System.out.println("Hilo"+ cliente+ "Posicion" + busqueda);

        }
    }

    public static void main(String[] args) throws InterruptedException {

        long inicio = System.currentTimeMillis();

        fracciones = new ArrayList<Fraccion>();
        Scanner lectura = new Scanner (System.in);

        System.out.println("Ingrese numero de maquinas: ");

        String maquinas = lectura.next();

        lectura.reset();

        System.out.println("Ingrese numero para calcular sucesión de Farey: ");

        String numero = lectura.next();

        System.out.println("Maquinas: "+maquinas);
        System.out.println("Numero: "+numero);
        int num = parseInt(numero);
        for(int i=1; i< num+ 1 ; i++)
        {
            for(int j=1; j<num  + 1 ; j++) {
                fracciones.add(ConstruccionFracciones(i,j));
            }
        }
        //ImprimirFracciones(fracciones);
        //System.out.println(fracciones.size());
        System.out.println("");
        System.out.println("Eliminacion fracciones > 1: ");


        int busqueda =0;
        /*while(busqueda != fracciones.size()){

            if(EliminarMayores(fracciones.get(busqueda).numerador, fracciones.get(busqueda).denominador)){
                System.out.println("Se debe eliminar:" + fracciones.get(busqueda).fraccion);
                fracciones.remove(busqueda);
                ImprimirFracciones(fracciones);
                busqueda = busqueda - 1;
            }else{
                busqueda = busqueda + 1;
            }


        }*/
        int clientesXnumero = fracciones.size()/parseInt(maquinas);
        Runnable proceso1 = new SusFarey("Hilo 1", 0, clientesXnumero);
        Runnable proceso2 = new SusFarey("Hilo 2", 1, clientesXnumero);
        Runnable proceso3 = new SusFarey("Hilo 3", 2, clientesXnumero);
        Runnable proceso4 = new SusFarey("Hilo 4", 3, clientesXnumero);
        Thread th = new Thread(proceso1);
        Thread th2 = new Thread(proceso2);
        Thread th3 = new Thread(proceso3);
        Thread th4 = new Thread(proceso4);
        th.start();
        th2.start();
        th3.start();
        th4.start();
        if(!((float)fracciones.size()/parseInt(maquinas)%1==0)){
            Runnable proceso5 = new SusFarey("Hilo 5", 4, clientesXnumero*5- fracciones.size());
            Thread th5 = new Thread(proceso5);
            th5.start();
            while (th5.isAlive()){

            }
        }



        while (th.isAlive() || th2.isAlive() || th3.isAlive() || th4.isAlive()){

        }
        synchronized (fraccionesNew){

            //ImprimirFracciones(fraccionesNew);
            System.out.println(fraccionesNew.size());
            System.out.println("");
            System.out.println("Eliminacion fracciones repetidas: ");
            busqueda =0;
            while(busqueda < fraccionesNew.size()){
                int valor = BuscarRepetidos(fraccionesNew, busqueda);
                if(valor!= 0){
                    //System.out.println("Se debe eliminar repetido:" + fraccionesNew.get(valor).fraccion);
                    fraccionesNew.remove(valor);
                    // ImprimirFracciones(fraccionesNew);

                }else {
                    busqueda = busqueda + 1;
                }


            }
            System.out.println("");
            System.out.println("Ordenamiento: ");
            fraccionesNew.add(AgregarFinal());
            fraccionesNew = Ordenamiento(fraccionesNew);
            ImprimirFracciones(fraccionesNew);


        }

        long fin = System.currentTimeMillis();

        double tiempo = (double) ((fin - inicio)/1000);

        System.out.println(tiempo +" segundos");
    }
    public void run() {

            this.CalcularSusFarey(this.numcliente, this.nummaximo);


    }
        public static Fraccion ConstruccionFracciones(int numerador, int denominador){

        Fraccion fraccion = new Fraccion();
        fraccion.fraccion =  Integer.toString(numerador)+"/" + Integer.toString(denominador);
        fraccion.numerador = numerador;
        fraccion.denominador = denominador;
        return fraccion;
    }
    public static Fraccion AgregarFinal(){

        Fraccion fraccion = new Fraccion();
        fraccion.fraccion =  0+"/" + 1;
        fraccion.numerador = 0;
        fraccion.denominador = 1;
        return fraccion;
    }
    public static boolean EliminarMayores(Float numerador, Float denominador){

        return numerador>denominador;
    }
    public static void ImprimirFracciones(List<Fraccion> fracciones){
        System.out.println("");
        System.out.print("Fracciones: ");

        System.out.print(fracciones.get(0).getFraccion());

        for(int j=1; j<fracciones.size() ; j++) {
            System.out.print(","+ fracciones.get(j).getFraccion());
        }
        System.out.println("");

    }

    public static int BuscarRepetidos (List<Fraccion> fracciones, int posicion){

        for(int j=0; j<fracciones.size() ; j++) {
            if(j!=posicion && (fracciones.get(j).numerador/fracciones.get(j).denominador) == (fracciones.get(posicion).numerador/fracciones.get(posicion).denominador)  ){
                return j;
            }
        }
        return 0;
    }
    public static List<Fraccion> Ordenamiento (List<Fraccion> fracciones){

        for(int i=0; i<fracciones.size() ; i++) {
            for(int j=0; j<fracciones.size() ; j++) {
                if(j>i && (fracciones.get(j).numerador/fracciones.get(j).denominador) < (fracciones.get(i).numerador/fracciones.get(i).denominador)  ){
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

        return  fracciones;
    }
    public static class Fraccion{
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
