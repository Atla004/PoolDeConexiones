package Pool;


import java.util.ArrayList;
import PropertiesClass.PropertyHandler;

public  enum Pool {
    INSTANCE; 


    private  PropertyHandler prop = new PropertyHandler("src/Property/Pool.properties");

    private int DEFAULT =Integer.parseInt(prop.getProp("DEFAULT", "20")) ;
    private int INCREASE = Integer.parseInt(prop.getProp("INCREASE" , "5"));
    private int LIMIT = Integer.parseInt(prop.getProp("LIMIT", "50"));
    protected ArrayList<Conexion> listDBcon;



    private Pool() {
        listDBcon = new ArrayList<>();
        for (int i = 0; i < DEFAULT; i++) {
            Conexion con = new Conexion(i);
            listDBcon.add(con);
            con.connect();
        }

    }
 
    public Pool getInstance() {
        return INSTANCE;
    }

    // Método para obtener el número máximo de conexiones permitidas
    protected int getDEFAULT() {
        return DEFAULT;
    }

    protected int getINCREASE() {
        return INCREASE;
    }

    protected int getLIMIT() {
        return LIMIT;
    }    

}