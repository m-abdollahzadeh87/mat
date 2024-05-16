package mat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mat.da.PositionDA;
import mat.model.Organization;
import mat.model.Position;

public class PositionController {

    public static Position instance;

    @FXML
    private Label messageLBL;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private Button okBTN;

    @FXML
    protected void onOkClick(){
        String name = nameTF.getText();
        if (name.trim().isEmpty()) {
            messageLBL.setText("Name can not be empty");
            return;
        }
        instance = new Position();
        instance.setName(name);
        instance.setDescription(descriptionTF.getText());
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
