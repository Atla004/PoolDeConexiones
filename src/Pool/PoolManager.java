package Pool;
import java.util.ArrayList;


public class PoolManager{
    // Atributo que almacena la instancia unica de la clase Pool
    private static Pool pool =  Pool.INSTANCE;
    public Conexion DBcon = null;
    private static ArrayList<Conexion> listDBcon = pool.listDBcon;


    // Metodo para obtener una conexión disponible de la lista de conexiones del pool
    public static synchronized Conexion getDBcon() {
        boolean loop = true;
        Conexion rConexion = null;

        while (loop ){

            for(Conexion x : listDBcon){
                Conexion con=x;
                if (con.getAvailable()) {
                    con.setAvailable(false);
                    rConexion = con;
                    loop = false;
                    break;

                }
            }
            
            // Incrementando si no encuentra conexiones
            if (loop) {
               increaseDbcon();
            }
        }
        reduce();
        return rConexion;

    }

    public static synchronized void executeQuery(Conexion DBcon,String query){
        DBcon.executeQuery(query);
        
    }

    // Metodo para devolver una conexion usada a la lista de conexiones del pool
    public  static  void returnDBcon(Conexion DBcon) {
        
        for (Conexion x : listDBcon) {
            Conexion con = x;
            if (con == DBcon) {
                con.setAvailable(true);
                break;
            }
        }

    }
    
    // Metodo para crear una nueva conexion y añadirla a la lista de conexiones del pool
    private static synchronized void increaseDbcon() {
        int currentSize = pool.listDBcon.size();
        int increase = pool.getINCREASE();
        int limit = pool.getLIMIT();

        for (int i = 0; i < increase; i++) {
            if (currentSize + i< limit) {
                Conexion con = new Conexion(currentSize+i);
                listDBcon.add(con);
                con.connect();
                    // System.out.println("incrementado");
            } else {

            }
        }
    }


    // Metodo para reducir el número de conexiones disponibles en la lista del pool
    private static synchronized void reduce() {
        int currentSize = pool.listDBcon.size();
        int increase = pool.getINCREASE();
        int defaults = pool.getDEFAULT();


        if ((increase *2) <= countAvailable()) {          
        
            for (int i = 0; i < increase; i++) {

                // ver si las conexiones del pool son mayores al Default
                if (currentSize - i > defaults) {
                    Conexion con = pool.listDBcon.get(currentSize - 1 - i);
                    if (con.getAvailable()) {
                        pool.listDBcon.remove(con);
                        con.disconnect();
                    }
                } else {
                    break;
                }
            }
        }
        
    }

        // Metodo para contar el numero de conexiones disponibles en la lista del pool
    private   static  int countAvailable() {
        int count = 0;
        ArrayList<Conexion> listDBcon = pool.listDBcon;
        for (Conexion con : listDBcon) {
            if (con.getAvailable()) {
                count++;
            }
        }
        return count;
    }

    // Metodo para contar el numero de conexiones no disponibles en la lista del pool
    private  static  int countUnavailable() {
        int count = 0;
        ArrayList<Conexion> listDBcon = pool.listDBcon;
        for (Conexion con : listDBcon) {
            if (!con.getAvailable()) {
                count++;
            }
        }
        return count;
    }

        // Metodo para mostrar el numero de conexiones activas y libres del pool
     public  synchronized static void status() {
        int available = countAvailable();
        int unavailable = countUnavailable();
        System.out.println( "Total de conxiones: "+ String.valueOf(available+ unavailable) + " "+ "disponibles: "+ String.valueOf(available) + " "+"no disponibles: "+String.valueOf(unavailable) );
    }



}








