package mat.da;

import mat.model.Position;
import mat.util.JdbcController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PositionDA {

    private static final Logger logger = Logger.getLogger(PositionDA.class.getName());

    //chone position be vasete Organization(office) ghabel dastresi ast
    // bayad zamane save position moshakhas konim be kodam office vasl mishavad
    public Position save(Position position, int officeId) {
        try {
            String sql = "insert into Position(id,name,description,officeId)values(?,?,?,?)";
            position.setId(JdbcController.getNextId("Position"));
            JdbcController.execute(sql,
                    position.getId(),
                    position.getName(),
                    position.getDescription(),
                    officeId);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return position;
    }

    public Position get(int id) {
        Position position = null;
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id,name,description from Position where id = ?");
            statement.setInt(1, id);
            try (con; statement) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    position = new Position();
                    position.setId(rs.getInt(1));
                    position.setName(rs.getString(2));
                    position.setDescription(rs.getString(3));
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Empty data");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Empty data");
        }
        return position;
    }

    public List<Position> getByOffice(int officeId) {
        List<Position> result = new ArrayList<>();
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id from Position where officeId = ?");
            statement.setInt(1, officeId);
            try (con; statement) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    // baraye tekrar nakardan tabdil record be entity
                    // az method get(id) esteefade mikonim
//                    Position position = new Position();
//                    position.setId(rs.getInt(1));
//                    position.setName(rs.getString(2));
//                    position.setDescription(rs.getString(3));
//                    result.add(position);
                    result.add(get(rs.getInt(1)));
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Empty data");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Empty data");
        }
        return result;
    }
}
