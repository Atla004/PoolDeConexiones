import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
  
        ArrayList<Clients> listCL = new ArrayList<>();
        int numCL = 1;
        int i = 1;

        long start=System.currentTimeMillis() ;

        while(i<=numCL){                 
            Clients cl = new Clients(i);
            listCL.add(cl);
            cl.start();
            i++;
        }
        if (!(i<=numCL)) {
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            
        }

        
    }
}
