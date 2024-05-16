package mat.da;

import mat.model.Organization;
import mat.model.Position;
import mat.util.JdbcController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrganizationDA {

    private static final Logger logger = Logger.getLogger(OrganizationDA.class.getName());

    PositionDA positionDA = new PositionDA();

    public Organization save(Organization organization) {
        try {
            String sql = "insert into Organization(id,name)values(?,?)";
            organization.setId(JdbcController.getNextId("Organization"));
            JdbcController.execute(sql,
                    organization.getId(),
                    organization.getName());
            if (organization.getChart() != null) {
                for (Position position : organization.getChart()) {
                    positionDA.save(position, organization.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return organization;
    }

    public Organization update(Organization organization) {
        try {
            String sql = "update Organization set name=? where id=?)";
            JdbcController.execute(sql,
                    organization.getId(),
                    organization.getName());
            if (organization.getChart() != null) {
                for (Position position : organization.getChart()) {
                    if (position.getId() == null)
                        positionDA.save(position, organization.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.info(e.getMessage());
        }
        return organization;
    }

    public Organization get(int id) {
        Organization organization = null;
        try {
            Connection con = JdbcController.getConnection();
            PreparedStatement statement = con.prepareStatement("select id,name from Organization where id = ?");
            statement.setInt(1, id);
            try (con; statement) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    organization = new Organization();
                    organization.setId(rs.getInt(1));
                    organization.setName(rs.getString(2));
                    organization.setChart(positionDA.getByOffice(organization.getId()));
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Empty data");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Empty data");
        }
        return organization;
    }

    public List<Organization> findAll() {
        List<Organization> list = new ArrayList<>();
        try {
            for (Integer id : JdbcController.getAllId("Organization")) {
                list.add(get(id));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return list;
    }
}
