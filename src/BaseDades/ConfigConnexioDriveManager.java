package BaseDades;

import java.sql.*;

public class ConfigConnexioDriveManager {

    private Connection con = null;
    private String cadenaConnexio;
    private String usuari;
    private String contrasenya;

    public ConfigConnexioDriveManager() {
        cadenaConnexio = "jdbc:mysql://localhost:3306/mastermind_jx";
        usuari = "root";
        contrasenya = "1234";
    }
    

    public ConfigConnexioDriveManager(String cadenaConnexio, String usuari, String contrasenya) {
        this.cadenaConnexio = cadenaConnexio;
        this.usuari = usuari;
        this.contrasenya = contrasenya;
    }

    /**
     * Carrega la classe OracleDriver de la llibreria jdbc per a Oracle, obté
     * una instància de la classe Connection, amb la connexió oberta amb el SGBD
     * a la BD indicada a la cadena de connexió.
     *
     * @return torna la connexió oberta
     */
    public Connection getConnexio() throws MyException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cadenaConnexio, usuari, contrasenya);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new MyException(ex.getMessage(), ex);
        }
        return con;
    }

}
