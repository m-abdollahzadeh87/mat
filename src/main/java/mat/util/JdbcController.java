package mat.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class JdbcController {

    private static final Logger logger = Logger.getLogger(JdbcController.class.getName());

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Getting a database connection");
        Class.forName("org.h2.Driver");
//        return DriverManager.getConnection("jdbc:h2:mat.h2db", "sa", "");
        return DriverManager.getConnection("jdbc:h2:../mat/db/mat.db", "sa", "123");
    }

    // make dynamic list for parameter
    // link https://www.baeldung.com/java-arrays-guide
    public static void execute(String sql, Object... parameters) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        logger.info("Execute query\n" + sql);
        PreparedStatement statement = con.prepareStatement(sql);
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
        }
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static void createSchema() throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        logger.info("Creating schema");
        Statement st = con.createStatement();
        logger.info("Creating table Organization");
        st.executeUpdate("create table if not exists Organization(id integer, name varchar(255))");

        logger.info("Creating table Person");
        st.executeUpdate("create table if not exists Person(id integer, " +
                "firstName varchar(255), " +
                "lastName varchar(255)," +
                "positionId integer," +
                "officeId integer)");

        logger.info("Creating table Position");
        st.executeUpdate("create table if not exists Position(id integer, name varchar(255), description varchar(255), officeId integer)");

        logger.info("Creating table User");
        st.executeUpdate("create table if not exists UserAccount(id integer, username varchar(255), password varchar(255), role varchar(255))");
        st.close();
        con.close();
        logger.info("Created schema");
    }

    // get last id from table foe new record insert
    public static int getNextId(String table) throws SQLException, ClassNotFoundException {
        logger.info("Get next Id " + table);
        Connection con = getConnection();
        Statement statement = con.createStatement();
        int result = 0;
        try {
            ResultSet rs = statement.executeQuery("select max(id) M_ID from " + table);
            while (rs.next())
                result = rs.getInt(1);
        } catch (Exception e) {
            logger.log(Level.WARNING, "No Id");
        } finally {
            statement.close();
            con.close();
        }
        return result + 1;
    }

    // get count of all record on table
    public static int getCount(String table) throws SQLException, ClassNotFoundException {
        logger.info("Get next Id " + table);
        Connection con = getConnection();
        Statement statement = con.createStatement();
        int result = 0;
        try {
            ResultSet rs = statement.executeQuery("select count(id) c from " + table);
            while (rs.next())
                result = rs.getInt(1);
        } catch (Exception e) {
            logger.log(Level.WARNING, "No Count");
        } finally {
            statement.close();
            con.close();
        }
        return result;
    }

    // For load all record with id and used on DA layer
    public static List<Integer> getAllId(String table) throws SQLException, ClassNotFoundException {
        logger.info("Get next Id " + table);
        Connection con = getConnection();
        Statement statement = con.createStatement();
        List<Integer> result = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("select id from " + table);
            while (rs.next())
                result.add(rs.getInt(1));
        } catch (Exception e) {
            logger.log(Level.WARNING, "No Count");
        } finally {
            statement.close();
            con.close();
        }
        return result;
    }
}
