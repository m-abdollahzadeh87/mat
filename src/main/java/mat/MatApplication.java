package mat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mat.da.OrganizationDA;
import mat.da.PersonDA;
import mat.da.PositionDA;
import mat.da.UserDA;
import mat.model.UserAccount;
import mat.util.JdbcController;

import java.io.IOException;
import java.sql.SQLException;

// MAT meaning : Mohammad Abdollahzadeh Trazkouhi
public class MatApplication extends Application {

    static OrganizationDA organizationDA = new OrganizationDA();
    static PersonDA personDA = new PersonDA();
    static PositionDA positionDA = new PositionDA();
    static UserDA userDA = new UserDA();

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {

        //check table in database, creat if not exist
        JdbcController.createSchema();

        // make user boss and assistant
        initUser();

        FXMLLoader fxmlLoader = new FXMLLoader(MatApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("MAT Application");
        stage.setScene(scene);
        stage.show();
    }

    private void initUser() {
        UserAccount boss = userDA.get("boss");
        if (boss == null) {
            boss = new UserAccount();
            boss.setUsername("boss");
            boss.setPassword("123456");
            boss.setRole("BOSS");
            userDA.save(boss);
        }
        UserAccount assistant = userDA.get("assistant");
        if (assistant == null) {
            assistant = new UserAccount();
            assistant.setUsername("assistant");
            assistant.setPassword("654321");
            assistant.setRole("ASSIST");
            userDA.save(assistant);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}