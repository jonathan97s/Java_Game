
package BaseDades;


import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author jgonza24
 */
public class ConnexioMySQLConfgiConnexioDataSource {

    private DataSource ds;

    public ConnexioMySQLConfgiConnexioDataSource() {
        MysqlDataSource myds = new MysqlDataSource();
        myds.setURL("jdbc:mysql://localhost:3306/mastermind_jx");                  
        myds.setUser("root");
        myds.setPassword("1234");
        ds = myds;
    }
  
    public Connection getConnection() throws SQLException {          
        return ds.getConnection();      
    }
}