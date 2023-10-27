package BaseDades;

import BaseDades.ConnexioMySQLConfgiConnexioDataSource;
import DadesPartides.Partida;
import Login.LoginController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartidesDAOImpl implements PartidesDAO {

    private ConnexioMySQLConfgiConnexioDataSource configCon;

    public PartidesDAOImpl(ConnexioMySQLConfgiConnexioDataSource configCon) {
        this.configCon = configCon;
    }

    /**
     * El método que se encarga de añadir una partida ganada o perdida en la
     * base de datos
     *
     * @param p
     * @throws MyException
     */
    @Override
    public void afegirPartida(Partida p) throws MyException {

        String sentencia = "INSERT INTO PARTIDES(usuari_name, win_or_lose, attempts, num_level, score) VALUES(?,?,?,?,?)";
        try (Connection con = configCon.getConnection();
                PreparedStatement ps = con.prepareStatement(sentencia)) {
            ps.setString(1, p.getNomUsuari());
            ps.setBoolean(2, p.getGuanyada());
            ps.setInt(3, p.getIntents());
            ps.setInt(4, p.getDificultat());
            ps.setInt(5, p.getPuntuacio());
            ps.execute();
        } catch (SQLException ex) {
            throw new MyException(ex.getMessage());
        }
    }

    /**
     * Hace una consulta por la base de datos y devuelve la lista de 10 partidas
     * que tengan más puntos, si el jugador no se encuentra agrega una posicion
     * mas y indica los datos de su mejor partida .
     *
     * @return
     */
    @Override
    public ObservableList<Partida> getTop10Partidas(String nombreUsuario)throws MyException {
        ObservableList<Partida> partidas = FXCollections.observableArrayList();
        int posicio = 1;
        boolean usuarioTop10 = false;

        try (Connection con = configCon.getConnection();
                PreparedStatement st = con.prepareStatement(
                        "SELECT usuari_name, score FROM partides ORDER BY score DESC LIMIT 0, 10")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String usuario = rs.getString("usuari_name");
                if (usuario.equals(nombreUsuario)) {
                    usuarioTop10 = true;
                }
                int score = +rs.getInt("score");
                partidas.add(new Partida(posicio, usuario, score));
                posicio++;
            }
            if (usuarioTop10 == false) {
                int[] resultado = getPosicionUsuario(nombreUsuario);
                partidas.add(new Partida(resultado[1], nombreUsuario, resultado[0]));
            }
        } catch (SQLException ex) {
            throw new MyException(ex.getMessage());
        }
        return partidas;
    }

    /**
     * Devuelve la posición del jugador en la base de datos.
     *
     * @param usuario
     * @return
     */
    @Override
    public int[] getPosicionUsuario(String usuario)throws MyException {
        int[] resultado = null;

        try (Connection con = configCon.getConnection();
                PreparedStatement st = con.prepareStatement(
                        "SELECT score, position FROM (SELECT usuari_name, score, RANK()"
                        + "OVER (ORDER BY score DESC) as position FROM partides) t WHERE usuari_name = ? LIMIT 1")) {
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int score = rs.getInt("score");
                int position = rs.getInt("position");
                resultado = new int[]{score, position};
            }
        } catch (SQLException ex) {
            throw new MyException(ex.getMessage());
        }
        return resultado;
    }

    /**
     * Devuelve las partidas juagadas de diferentes niveles por el jugador que
     * ha iniciado la sesión.
     *
     * @param usuario
     * @return
     */
    @Override
    public List<Partida> getPartidasUsuario(String usuario)throws MyException {
        List<Partida> partidas = new ArrayList<>();
        String nom = "Game";
        int num = 1;

        try (Connection con = configCon.getConnection();
                PreparedStatement st = con.prepareStatement(
                        "SELECT num_level, score FROM partides WHERE usuari_name = ? ORDER BY num_level, score DESC")) {
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int num_level = rs.getInt("num_level");
                int score = rs.getInt("score");
                Partida partida = new Partida(nom + num, num_level, score);
                partidas.add(partida);
                num++;
            }
        } catch (SQLException ex) {
            throw new MyException(ex.getMessage());
        }
        return partidas;
    }

    /**
     * Devuelve el número de partidas jugadas por cada jugador.
     *
     * @return
     */
    @Override
    public Map<String, Integer> getNumPartidasPorUsuario()throws MyException {
        Map<String, Integer> resultado = new HashMap<>();

        try (Connection con = configCon.getConnection();
                PreparedStatement st = con.prepareStatement(
                        "SELECT p.usuari_name, COUNT(p.partida_id) TOTAL FROM partides"
                        + " p JOIN usuaris u ON (u.usuari_name = p.usuari_name)"
                        + " GROUP BY u.usuari_name ORDER BY TOTAL DESC")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String usuario = rs.getString("usuari_name");
                int total = rs.getInt("TOTAL");
                resultado.put(usuario, total);
            }
        } catch (SQLException ex) {
            throw new MyException(ex.getMessage());
        }
        return resultado;
    }

}
