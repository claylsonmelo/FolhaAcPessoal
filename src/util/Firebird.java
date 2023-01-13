/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author claylson
 */
public class Firebird {

    public boolean isConnected;
    private static Firebird conexaoUtil;

    public static Firebird getInstance() {
        if (conexaoUtil == null) {
            conexaoUtil = new Firebird();
        }
        return conexaoUtil;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        isConnected = false;
        Connection conn = null;

        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
        conn = DriverManager.getConnection("jdbc:firebirdsql:192.168.100.4/53052:C:\\fortes\\Ac\\Ac.fdb", "SYSDBA", "masterkey");

        isConnected = true;

        return conn;
    }
}
