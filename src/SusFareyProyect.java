import java.util.*;
import java.util.concurrent.*;

public class SusFareyProyect {
    public static int ARRAY_SIZE = 0;

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Ingrese un numero para realizar la Sucesion de Farey: ");
        String numero = lectura.next();
        ARRAY_SIZE = Integer.parseInt(numero);

        lectura.reset();
        System.out.println("Ingrese un numero de clientes: ");
        String clientes = lectura.next();

        long inicio = System.currentTimeMillis();


        ForkJoinPool pool = new ForkJoinPool(Integer.parseInt(clientes));

        List<String> array = createArray(ARRAY_SIZE);
        List<String> arrayTemp = new ArrayList<>();
        System.out.println("Creación Array: " + array);


        array.removeIf(s -> parse(s)>1);
        System.out.println("Eliminacion > 1 : " +array );


        Repetidas repetidas = new Repetidas(array, 0, array.size() - 1);
        pool.invoke(repetidas);
        array.removeIf(s -> s==null);
        System.out.println("Eliminación de repetidas " +array);


        Ordenamiento ordenamiento = new Ordenamiento(array, 0, array.size() - 1);
        pool.invoke(ordenamiento);
        array.add(0,"0/1");
        System.out.println("Ordenamiento Menor a Mayor " +array);



        long fin = System.currentTimeMillis();

        double tiempo = (double) ((fin - inicio)/1000);

        System.out.println("Fin en " +tiempo +" segundos");

    }

    private static List<String> createArray(final int size) {
        List<String> array = new ArrayList<>();
        String fraccion;
        for(int i=1; i< size+ 1 ; i++)
        {
            for(int j=1; j<size  + 1 ; j++) {
                fraccion = i+"/"+j;
                array.add(fraccion);
            }
        }
        return array;
    }




    private static double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
}

class Ordenamiento extends RecursiveAction {
    private List<String> array;
    private int left;
    private int right;

    public Ordenamiento(List<String> array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;
            RecursiveAction leftSort = new Ordenamiento(array, left, mid);
            RecursiveAction rightSort = new Ordenamiento(array, mid + 1, right);
            invokeAll(leftSort, rightSort);
            //System.out.println("Current Therad Name = "+Thread.currentThread().getName());

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        List<String> temp  = new ArrayList<>();
        int x = left;
        int y = mid + 1;
        int z = 0;
        while (x <= mid && y <= right) {
            if (parse(array.get(x)) <= parse(array.get(y))){
                temp.add(array.get(x));
                z++;
                x++;
            } else {
                temp.add(array.get(y));
                z++;
                y++;
            }
        }

        while (y <= right) {
            temp.add(array.get(y++));
        }
        while (x <= mid) {
            temp.add(array.get(x++));
        }

        for (z = 0; z < temp.size(); z++) {
            array.set(left + z,temp.get(z));
        }
    }
    double parse(String ratio) { if (ratio.contains("/")) { String[] rat = ratio.split("/"); return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]); } else { return Double.parseDouble(ratio); } }
}

class Repetidas extends RecursiveAction {
    private List<String> array;
    private int left;
    private int right;
    private List<String> arrayTemp;
    public Repetidas(List<String> array,  int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;

    }

    @Override
    protected void compute() {
        if(left<right){
            int mid = (left + right) / 2;
            RecursiveAction leftSort = new Repetidas(array, left, mid);
            RecursiveAction rightSort = new Repetidas(array, mid + 1, right);
            invokeAll(leftSort,rightSort);
            delete(left, mid, right);
        }
    }

    private  void delete(int left, int mid, int right) {
        int i = left;
        int j = 0;
        while (i<=right && i< array.size())
        {
            j=i;
            if(array.get(i)!=null)
            {
                while (j<array.size() ){
                    try{
                        if (array.get(j)!= null && parse(array.get(i)) == parse(array.get(j)) && i!=j) {
                            array.set(j,null);
                        }

                    }catch (Exception ex){}
                    j++;
                }

            }
            i++;
        }

    }


    double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
}