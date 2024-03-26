import Pool.PoolManager;

public class Clients extends Thread {
    int numCL;

    public Clients(int numCL) {
        this.numCL = numCL;
    }

    @Override
    public void run() {
        try {
            

        PoolManager poolM = new PoolManager();
        poolM.DBcon = PoolManager.getDBcon();

        // System.out.println("cliente #"+numCL+ "conectado");

        PoolManager.executeQuery(poolM.DBcon, "SELECT * FROM\"public\".clientes s;");




        PoolManager.returnDBcon(poolM.DBcon);



        PoolManager.status();

        System.out.println("cliente #"+numCL+ "desconectado");
        

        } catch (Exception e) {
        e.printStackTrace();
        }
            
    }

}

