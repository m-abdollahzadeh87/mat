package mat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mat.model.Organization;
import mat.model.UserAccount;

import java.io.IOException;

public class BossController {

    @FXML
    private Label messageLBL;

    @FXML
    private Button newBTN;

    @FXML
    private TableView<Organization> officesTV;

    @FXML
    private void initialize() {
        onSearchClick();
    }

    @FXML
    protected void onSearchClick() {

        officesTV.getColumns().clear();

        TableColumn<Organization, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Organization, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        officesTV.getColumns().add(colId);
        officesTV.getColumns().add(colName);

        officesTV.getItems().clear();
        for (Organization item : MatApplication.organizationDA.findAll()) {
            officesTV.getItems().add(item);
        }
    }

    @FXML
    protected void onNewClick() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("new-office-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Office");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (OfficeController.instance != null) {
                MatApplication.organizationDA.save(OfficeController.instance);
            }
            onSearchClick();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
