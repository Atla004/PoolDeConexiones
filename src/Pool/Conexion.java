package Pool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import PropertiesClass.PropertyHandler;

@SuppressWarnings("unused")
public class Conexion {

private  PropertyHandler prop = new PropertyHandler("src/Property/Conexion.properties");
 // Atributos de la clase Conexion
private Connection connection; //  conexión con la base de datos
private Statement statement; // permite ejecutar sentencias SQL
private ResultSet resultSet; //  resultado de una consulta SQL
private boolean available; // indica si la conexión está disponible o no
private final int num;     // identificador de conexion
private String url = prop.getProp("url", "jdbc:postgresql://localhost:5432/local_gorras"); 
private String user = prop.getProp("user", "postgres"); 
private String password = prop.getProp("password", "1234"); 


protected Conexion(int num) {
    connection = null;
    statement = null;
    resultSet = null;
    available = false;
    this.num = num;

}

// Metodo para establecer la conexion con la base de datos
protected void connect() {
    try {

		connection = DriverManager.getConnection(url,user, password); 
        statement = connection.createStatement();
        available = true;
    } catch (SQLException e) {

        System.err.println("Error en la conexion con la base de datos");
        e.printStackTrace();
    }
}

// Metodo para cerrar la conexion con la base de datos
protected void disconnect() {
    try {

        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
        // Marcar la conexión como no disponible
        available = false;
    } catch (SQLException e) {
        System.err.println("Error al cerrar conxion con la base de datos");
        e.printStackTrace();
    }
}

// Metodo para ejecutar una consulta SQL y devolver el resultado
protected void executeQuery(String query) {
    try {
        if (statement != null) {
            resultSet = statement.executeQuery(query);
            
            
            // while (resultSet.next()){
            //     int id = resultSet.getInt("id");
            //     String nombre= resultSet.getString("nombre");				
            

            //     System.out.println( "id" + id);
            //     System.out.println("nombre:" + nombre);					

            // }
            // System.out.println("execute query from " + this.num+ " Con");
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
// ! LOGGER
    PropertyHandler prop = new PropertyHandler("src/Property/log.properties");
    String conex = "conexion" + num;
    String querys = prop.getProp(conex, "");
    querys += query + "\n ";
    try {
        prop.updateProp(conex, querys);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Metodo para asignar el valor del atributo available
protected void setAvailable(boolean available) {
    this.available = available;
}

// Metodo para obtener el valor del atributo available
protected boolean getAvailable() {
    return available;
}

protected int getNumCon(){
    return num;
}

}

