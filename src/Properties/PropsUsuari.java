package Properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


public class PropsUsuari {
    
    public Properties cargarProperties() {
        Properties p = new Properties();
        try {
            FileReader fr = new FileReader("User.txt");
            p.load(fr);
            fr.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return p;
    }

    public void guardarProperties(Properties p) {
        try {
            FileWriter fw = new FileWriter("User.txt");
            p.store(fw, "Propiedades de Usuario");
            fw.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void muestraProperties(Properties p) {
        System.out.print("UsuarioActual: ");
        System.out.println(p.getProperty("usuario"));
    }

    public void modificarProperties(Properties p, String nombre) {
        p.setProperty("usuario", nombre);
    }

    public String propertiesIniciales(Properties p) {
        String nombre = p.getProperty("usuario");       
        return nombre;
    }

    
}
