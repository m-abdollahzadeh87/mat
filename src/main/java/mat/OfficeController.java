package mat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mat.model.Organization;
import mat.model.Position;

import java.io.IOException;
import java.util.ArrayList;

public class OfficeController {

    public static Organization instance;

    @FXML
    private Label messageLBL;

    @FXML
    private TextField nameTF;

    @FXML
    private Button newBTN;

    @FXML
    private TableView<Position> chartTV;

    @FXML
    private Button okBTN;

    @FXML
    protected void onOkClick() {
        if (instance == null)
            instance = new Organization();

        String name = nameTF.getText();
        if (name.trim().isEmpty()) {
            messageLBL.setText("Name can not be empty");
            return;
        }
        if (instance.getChart() == null || instance.getChart().isEmpty()) {
            messageLBL.setText("Please make a position in chart");
            return;
        }
        instance.setName(name);
        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCloseClick() {
        instance = null;
        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onNewClick() {
        String name = nameTF.getText();
        if (name.trim().isEmpty()) {
            messageLBL.setText("Name can not be empty");
            return;
        }
        if (instance == null)
            instance = new Organization();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("new-position-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Position");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (PositionController.instance != null) {
                if (instance.getChart() == null)
                    instance.setChart(new ArrayList<>());

                instance.getChart().add(PositionController.instance);
            }
            fillChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void fillChart() {
        if (instance.getChart() != null) {
            chartTV.getColumns().clear();

            TableColumn<Position, String> colId = new TableColumn<>("Id");
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Position, String> colName = new TableColumn<>("Name");
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Position, String> colDescription = new TableColumn<>("Description");
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            chartTV.getColumns().add(colId);
            chartTV.getColumns().add(colName);
            chartTV.getColumns().add(colDescription);

            chartTV.getItems().clear();
            for (Position item : instance.getChart()) {
                chartTV.getItems().add(item);
            }
        }
    }
}
