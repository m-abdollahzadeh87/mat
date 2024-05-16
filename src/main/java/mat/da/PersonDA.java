package mat.da;

import mat.model.Person;
import mat.util.JdbcController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDA {

    private static final Logger logger = Logger.getLogger(PersonDA.class.getName());

    private PositionDA positionDA = new PositionDA();
    private OrganizationDA organizationDA = new OrganizationDA();

    public Person save(Person person) {
        try {
            String sql = "insert into Person(id,firstname,lastname,officeId,positionId)values(?,?,?,?,?)";
            person.setId(JdbcController.getNextId("Person"));
            JdbcController.execute(sql,
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getOffice().getId(),
                    person.getPosition().getId());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return person;
    }

    public Person update(Person person) {
        try {
            String sql = "update Person set=?,firstname=?,lastname=?,officeId=?,positionId=? where id=?";
            JdbcController.execute(sql,
                    person.getFirstName(),
                    person.getLastName(),
                    person.getOffice().getId(),
                    person.getPosition().getId(),
                    person.getId());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return person;
    }

    public Person get(int id) {
        Person person = null;
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id,firstname,lastname,officeId,positionId from person where id = ?");
            statement.setInt(1, id);
            try (con; statement) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    person = new Person();
                    person.setId(rs.getInt(1));
                    person.setFirstName(rs.getString(2));
                    person.setLastName(rs.getString(3));
                    person.setPosition(positionDA.get(rs.getInt(4)));
                    person.setOffice(organizationDA.get(rs.getInt(5)));
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Empty data");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Empty data");
        }
        return person;
    }

    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        try {
            for (Integer id : JdbcController.getAllId("Person")) {
                list.add(get(id));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    public int count() {
        try {
            return JdbcController.getCount("Person");
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return 0;
        }
    }

    public List<Person> findFiltered(int officeId, int positionId) {
        List<Person> list = new ArrayList<>();
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id from Person where officeId=? and positionId=?");
            statement.setInt(1, officeId);
            statement.setInt(2, positionId);
            try (con; statement) {
                ResultSet rs = statement.executeQuery();
                while (rs.next())
                    list.add(get(rs.getInt(1)));
            } catch (Exception e) {
                logger.log(Level.WARNING, "No Count");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return list;
    }
}
