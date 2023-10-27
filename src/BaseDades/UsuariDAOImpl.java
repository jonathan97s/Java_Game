package BaseDades;

import BaseDades.ConnexioMySQLConfgiConnexioDataSource;
import BaseDades.MyException;
import DadesUsuaris.Usuari;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuariDAOImpl implements UsuariDAO {

    private ConnexioMySQLConfgiConnexioDataSource configCon;

    public UsuariDAOImpl(ConnexioMySQLConfgiConnexioDataSource configCon) {
        this.configCon = configCon;
    }

    /**
     * Es el método que sirve para registrar nuevos usuarios y almacena la
     * contraseña encriptada en la base de datos.
     *
     * @param u
     * @return
     * @throws MyException
     */
    @Override
    public boolean afegirUsuari(Usuari u) throws MyException {
        boolean afegit = false;
        byte[] passwordHash = null;
        String sentencia = "INSERT INTO USUARIS(usuari_name, usuari_password) VALUES(?,?)";

        try (Connection con = configCon.getConnection();
                PreparedStatement pt = con.prepareStatement(sentencia)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            passwordHash = md.digest(u.getContrasenya().getBytes());
            pt.setString(1, u.getNom());
            pt.setBytes(2, passwordHash);
            if (pt.executeUpdate() > 0) {
                afegit = true;
            }
        } catch (SQLException ex) {
            afegit = false;
            throw new MyException("Error registrando usuario", ex);
        } catch (NoSuchAlgorithmException ex) {
            afegit = false;
            throw new MyException("Error encriptando contraseña", ex);
        }
        return afegit;
    }

    /**
     * Compruba que el usuario con el que quiere iniciar sesión esté almacenado
     * en la base de datos.
     *
     * @param u
     * @return
     * @throws MyException
     */
    @Override
    public boolean cercarUser(Usuari u) throws MyException {
        boolean trobat = false;
        String consulta = "SELECT usuari_password FROM USUARIS WHERE usuari_name = ?";

        try (Connection con = configCon.getConnection();
                PreparedStatement ps = con.prepareStatement(consulta);) {
            ResultSet rs;
            ps.setString(1, u.getNom());
            rs = ps.executeQuery();
            if (rs.next()) {
                String contrasenyaAlmacenada = rs.getString("usuari_password");
                trobat = decryptPassword(u.getContrasenya(), contrasenyaAlmacenada);
            } else {
                trobat = false;
            }
        } catch (SQLException ex) {
            trobat = false;
            throw new MyException("Error iniciando sesión", ex);
        }
        return trobat;
    }

    /**
     * El método que encripta la contraseña.
     *
     * @param enteredPassword
     * @param storedHash
     * @return
     */
    @Override
    public boolean decryptPassword(String enteredPassword, String storedHash) throws MyException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messageDigest.digest(enteredPassword.getBytes());
            String hashedPasswordString = new String(hashedPassword);
            
            return hashedPasswordString.equals(storedHash);
        } catch (Exception ex) {
            throw new MyException("Error desencriptando la contraseña", ex);
        }
    }

}
