package PropertiesClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class PropertyHandler {

    private String CHF_FILE = null;
    private Properties property;
    

    public PropertyHandler( String source){
        this.property = new Properties();

        InputStream input = null;
        this.CHF_FILE = source ;

        try {
            input = new FileInputStream(CHF_FILE);
            property.load(input);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getProp(String prop, String defaultvalue){
        return property.getProperty(prop,defaultvalue );
    }

    public void updateProp(String prop, String value) throws FileNotFoundException, IOException {
        property.setProperty(prop, value);
        property.store(new FileOutputStream(CHF_FILE), null);
    }

}

    

