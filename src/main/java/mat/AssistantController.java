package mat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mat.model.Organization;
import mat.model.Person;
import mat.model.Position;

import java.io.IOException;
import java.util.ArrayList;

public class AssistantController {

    @FXML
    private Label messageLBL;

    @FXML
    private ComboBox<Organization> officesCB;

    @FXML
    private ComboBox<Position> chartsCB;

    @FXML
    private TableView<Person> employeeTV;

    Organization selectedOffice;
    Position selectedChart;

    @FXML
    private void initialize() {
        officesCB.getItems().setAll(MatApplication.organizationDA.findAll());
        officesCB.setOnAction((event) -> {
            selectedOffice = officesCB.getSelectionModel().getSelectedItem();
            chartsCB.getItems().clear();
            for (Position item : selectedOffice.getChart()) {
                chartsCB.getItems().add(item);
            }
        });

        chartsCB.setOnAction((event) -> {
            selectedChart = chartsCB.getSelectionModel().getSelectedItem();
            fillEmployee(selectedOffice, selectedChart);
        });
        if (!officesCB.getSelectionModel().isEmpty())
            officesCB.getSelectionModel().select(0);

        if (!chartsCB.getSelectionModel().isEmpty())
            chartsCB.getSelectionModel().select(0);
    }

    @FXML
    protected void onNewClick() {
        if (selectedChart == null) {
            messageLBL.setText("Please select Office and Position");
            return;
        }
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("new-person-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Person");
            stage.setScene(new Scene(root, 300, 300));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (PersonController.instance != null) {
                Person person = PersonController.instance;
                person.setOffice(selectedOffice);
                person.setPosition(selectedChart);
                MatApplication.personDA.save(person);
            }
            fillEmployee(selectedOffice, selectedChart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillEmployee(Organization office, Position chart) {
        employeeTV.getColumns().clear();

        TableColumn<Person, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        employeeTV.getColumns().add(colId);
        employeeTV.getColumns().add(colFirstName);
        employeeTV.getColumns().add(colLastName);

        employeeTV.getItems().clear();
        if (office != null && chart != null) {
            for (Person item : MatApplication.personDA.findFiltered(office.getId(), chart.getId())) {
                employeeTV.getItems().add(item);
            }
        }
    }
}
