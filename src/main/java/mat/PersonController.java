package mat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mat.model.Person;

public class PersonController {

    public static Person instance;

    @FXML
    private Label messageLBL;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private Button okBTN;

    @FXML
    protected void onOkClick(){
        String firstName = firstNameTF.getText();
        if (firstName.trim().isEmpty()) {
            messageLBL.setText("First name can not be empty");
            return;
        }
        String lastName = lastNameTF.getText();
        if (lastName.trim().isEmpty()) {
            messageLBL.setText("Last name can not be empty");
            return;
        }
        instance = new Person();
        instance.setFirstName(firstName);
        instance.setLastName(lastName);
        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCloseClick(){
        instance = null;
        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }
}
