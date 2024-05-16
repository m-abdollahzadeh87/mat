package mat.da;

import mat.model.UserAccount;
import mat.util.JdbcController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDA {

    private static final Logger logger = Logger.getLogger(UserDA.class.getName());

    public UserAccount save(UserAccount userAccount) {
        try {
            String sql = "insert into UserAccount(id,username,password,role)values(?,?,?,?)";
            userAccount.setId(JdbcController.getNextId("UserAccount"));
            JdbcController.execute(sql,
                    userAccount.getId(),
                    userAccount.getUsername(),
                    userAccount.getPassword(),
                    userAccount.getRole());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return userAccount;
    }

    public UserAccount get(String username) {
        UserAccount userAccount = null;
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id,username,password,role from UserAccount where username = ?");
            statement.setString(1, username);
            try {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    userAccount = new UserAccount();
                    userAccount.setId(rs.getInt(1));
                    userAccount.setUsername(rs.getString(2));
                    userAccount.setPassword(rs.getString(3));
                    userAccount.setRole(rs.getString(4));
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Empty data");
            } finally {
                statement.close();
                con.close();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Empty data");
        }
        return userAccount;
    }
}
