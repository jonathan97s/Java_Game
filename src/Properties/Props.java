package Properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Props {

    public Properties cargarProperties() {
        Properties p = new Properties();
        try {
            FileReader fr = new FileReader("Config.txt");
            p.load(fr);
            fr.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return p;
    }

    public void guardarProperties(Properties p) {
        try {
            FileWriter fw = new FileWriter("Config.txt");
            p.store(fw, "Propiedades de longiudes");
            fw.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void muestraProperties(Properties p) {
        System.out.print("Longitud de la combinación secreta: ");
        System.out.println(p.getProperty("longitud"));
    }

    public void modificarProperties(Properties p, int longi) {
        System.out.println("La longitud se ha cambiado de " + p.getProperty("longitud") + " a " + longi);
        p.setProperty("longitud", String.valueOf(longi));
    }

    public int propertiesIniciales(Properties p) {
        String longitud = p.getProperty("longitud");       
        return Integer.parseInt(longitud);
    }

}
