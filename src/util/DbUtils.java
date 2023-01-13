/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claylson
 */
public class DbUtils implements Serializable {

    public static Connection createConnection() {

        Connection conn = null;

        try {

            conn = Firebird.getInstance().getConnection();
            conn.setAutoCommit(false);

        } catch (Exception ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    public static Savepoint startTransaction(Connection conn) {

        Savepoint sp = null;

        try {

            sp = conn.setSavepoint();

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sp;
    }

    public static Savepoint startTransaction(PreparedStatement ps) {

        Savepoint sp = null;

        try {

            sp = ps.getConnection().setSavepoint();

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sp;
    }

    public static Savepoint startTransaction(CallableStatement cs) {

        Savepoint sp = null;

        try {

            sp = cs.getConnection().setSavepoint();

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sp;
    }

    public static PreparedStatement createStatement(Connection conn, String sql) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ps;
    }

    public static PreparedStatement createStatement(String sql) {

        PreparedStatement ps = null;

        try {
            Connection conn = createConnection();

            ps = conn.prepareStatement(sql);

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ps;
    }

    public static CallableStatement createCallStatement(String sql) {

        Connection conn = createConnection();

        CallableStatement cs = null;

        try {

            cs = conn.prepareCall(sql);

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cs;
    }

    public static CallableStatement createCallStatement(Connection conn, String sql) {

        CallableStatement cs = null;

        try {
            
            cs = conn.prepareCall(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cs;
    }

    public static boolean execute(PreparedStatement ps) {

        boolean result = false;
        try {

            ps.execute();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static ResultSet executeQuery(PreparedStatement ps) {
        ResultSet result = null;

        try {

            result = ps.executeQuery();

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean execute(CallableStatement cs) {

        boolean result = false;
        try {

            cs.execute();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean commit(PreparedStatement ps) {

        boolean result = false;
        try {

            ps.getConnection().commit();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean commit(CallableStatement cs) {

        boolean result = false;
        try {

            cs.getConnection().commit();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean roolback(PreparedStatement ps) {

        boolean result = false;
        try {

            ps.getConnection().rollback();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean roolback(CallableStatement cs) {

        boolean result = false;
        try {

            cs.getConnection().rollback();

            result = true;

        } catch (SQLException e) {

            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);

        }

        return result;
    }

    public static boolean executeAndCommit(PreparedStatement ps) {

        boolean result = false;
        try {

            ps.execute();
            //confirmar transação
            ps.getConnection().commit();

            result = true;

        } catch (SQLException e) {

            try {

                ps.getConnection().rollback();

            } catch (SQLException ex) {
                Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return result;
    }

    public static boolean executeAndClose(PreparedStatement ps, boolean connection) {

        boolean result = execute(ps);

        if (result) {
            result = commit(ps);
        } else {
            result = roolback(ps);
        }

        closeStatement(ps, connection);

        return result;
    }

    public static boolean executeAndClose(CallableStatement cs, boolean connection) {

        boolean result = execute(cs);

        if (result) {
            commit(cs);
        } else {
            roolback(cs);
        }

        closeCallStatement(cs, connection);

        return result;
    }

    public static boolean closeConnection(Connection conn) {
        boolean result = false;

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static boolean closeStatement(PreparedStatement ps) {

        return closeStatement(ps, false);
    }

    public static boolean closeStatement(PreparedStatement ps, boolean connection) {

        boolean result = false;

        try {
            if (ps != null) {

                Connection conn = ps.getConnection();

                ps.close();

                if (connection) {

                    closeConnection(conn);
                }

            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static boolean closeCallStatement(CallableStatement cs) {

        return closeCallStatement(cs, false);
    }

    public static boolean closeCallStatement(CallableStatement cs, boolean connection) {

        boolean result = false;

        try {
            if (cs != null) {

                Connection conn = cs.getConnection();

                cs.close();

                if (connection) {

                    closeConnection(conn);
                }

            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static void setIntParam(PreparedStatement ps, Integer index, Integer value) {

        try {

            if (value != null) {
                ps.setInt(index, value);
            } else {
                ps.setNull(index, Types.INTEGER);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static void setIntParam(CallableStatement cs, Integer index, Integer value) {

        try {

            if (value != null) {
                cs.setInt(index, value);
            } else {
                cs.setNull(index, Types.INTEGER);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static void setStrParam(CallableStatement cs, Integer index, String value) {

        try {

            if (value != null) {
                cs.setString(index, value);
            } else {
                cs.setNull(index, Types.VARCHAR);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static void setStrParam(PreparedStatement ps, Integer index, String value) {

        try {

            if (value != null) {
                ps.setString(index, value);
            } else {
                ps.setNull(index, Types.VARCHAR);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

     public static void setLongParam(PreparedStatement ps, Integer index, Long value) {

        try {

            if (value != null) {
                ps.setLong(index, value);
            } else {
                ps.setNull(index, Types.DOUBLE);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
    public static void setDoubleParam(CallableStatement cs, Integer index, Double value) {

        try {

            cs.setDouble(index, value);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
      public static void setDoubleParam(PreparedStatement ps, Integer index, Double value) {

        try {

            ps.setDouble(index, value);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static void setSqlTimeStpParam(PreparedStatement ps, Integer index, Date value) {

        try {
            if (value != null) {
                ps.setTimestamp(index, new java.sql.Timestamp(value.getTime()));
            } else {
                ps.setNull(index, Types.DATE);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static void setSqlDateParam(PreparedStatement ps, Integer index, Date value) {

        try {
            if (value != null) {
                ps.setDate(index, new java.sql.Date(value.getTime()));
            } else {
                ps.setNull(index, Types.DATE);
            }

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static String getStrValue(ResultSet rs, String columName) {
        String value = null;

        try {

            value = rs.getString(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }

    public static Integer getIntValue(ResultSet rs, String columName) {
        Integer value = null;

        try {

            value = rs.getInt(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }

    public static Double getDoubleValue(ResultSet rs, String columName) {
        Double value = null;

        try {

            value = rs.getDouble(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }

       public static Long getLongValue(ResultSet rs, String columName) {
        Long value = null;

        try {

            value = rs.getLong(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }
    
    public static Date getDateValue(ResultSet rs, String columName) {
        Date value = null;

        try {

            value = rs.getDate(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }

    public static Date getTimestampValue(ResultSet rs, String columName) {
        Date value = null;

        try {

            value = rs.getTimestamp(columName);

        } catch (SQLException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return value;
    }

    public static boolean next(ResultSet rs) {

        Boolean result = false;

        try {
            if (rs != null) {

                if (rs.next()) {
                    result = true;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static boolean closeResultSet(ResultSet rs) {

        Boolean result = false;

        try {

            if (rs != null) {

                rs.close();

            }

        } catch (SQLException ex) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
