package mat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mat.da.PersonDA;
import mat.model.Organization;
import mat.model.Person;
import mat.model.Position;
import mat.model.UserAccount;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label messageLBL;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    protected void onLoginClick() throws IOException {
        UserAccount userAccount = MatApplication.userDA.get(usernameTF.getText());
        if (userAccount == null) {
            messageLBL.setText("User not found");
            return;
        }
        if (!userAccount.getPassword().equals(passwordPF.getText())) {
            messageLBL.setText("Invalid password");
            return;
        }

        Stage stage = (Stage) messageLBL.getScene().getWindow();
        stage.close();

        stage = new Stage();
        FXMLLoader fxmlLoader;
        if (userAccount.getRole().equalsIgnoreCase("BOSS"))
            fxmlLoader = new FXMLLoader(MatApplication.class.getResource("boss-view.fxml"));
        else
            fxmlLoader = new FXMLLoader(MatApplication.class.getResource("assistant-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        stage.setTitle(userAccount.getUsername());
        stage.setScene(scene);
        stage.show();
    }
}
