


package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CollectorBO;
import lk.ijse.model.CollectorDTO;
import lk.ijse.tm.collectorTM;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class collectorManageController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCollectorId;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<collectorTM> tblCollector;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCollectorId;

    @FXML
    private TextField txtCollectorName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSlry;


    CollectorBO collectorBO =(CollectorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COLLECTOR);
    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colCollectorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadAllCustomers() {
        tblCollector.getItems().clear();

        try {
            ArrayList<CollectorDTO> getAllCollectors = collectorBO.getAllCollectrs();
            for(CollectorDTO c : getAllCollectors){
                tblCollector.getItems().add(new collectorTM(c.getCollectorId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail(),c.getSalary()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
         String id = txtCollectorId.getText();

        try {
            boolean isdelete= collectorBO.deleteCollector(id);
            if (isdelete){
                new Alert(Alert.AlertType.INFORMATION,"collector delete successfully").show();
                this.loadAllCustomers();
            }
        } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"collector doesnot deleted").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSendEmailsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSetOnAction(ActionEvent event) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtCollectorId.getText();
        String name = txtCollectorName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSlry.getText());

        try {
            boolean isSaved = collectorBO.saveCollectors(new CollectorDTO(id,name,address,contact,email,salary));
       if(isSaved){
           tblCollector.getItems().add(new collectorTM(id,name,address,contact,email,salary));
           new Alert(Alert.AlertType.INFORMATION, "Collector saved Successfully ").show();
       }

        } catch (SQLException e) {
         new Alert(Alert.AlertType.ERROR,"Collector doesnot saved" + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();


    }
    private void clearFields() {
        txtCollectorId.setText("");
        txtCollectorName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtSlry.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtCollectorId.getText();
        String name = txtCollectorName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSlry.getText());


        try {
            boolean isUpdate = collectorBO.updateCollector(new CollectorDTO(id,name,address,contact,email,salary));
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Collector Updated successfully");
            this.loadAllCustomers();
        }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Collector does not updated" + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }





    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtCollectorId.getText();
        CollectorDTO collectorDTO = null;
        try {
            collectorDTO = collectorBO.searchCollector(id);
            if(collectorDTO!= null){
                txtCollectorName.setText(collectorDTO.getName());
                txtAddress.setText(collectorDTO.getAddress());
                txtContact.setText(collectorDTO.getContact());
                txtEmail.setText(collectorDTO.getEmail());
                txtSlry.setText(String.valueOf(collectorDTO.getSalary()));
            }
            else{
                new Alert(Alert.AlertType.ERROR,"collector doesnot exists").show();

            }
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"CollectorId does not Found" + e.getMessage()).show();
        }





    }

    public void collectorIdOnKeyReleased(KeyEvent keyEvent) {
    }

    public void customerNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {

    }

    public void salaryOnKeyReleased(KeyEvent keyEvent)
    {
    }
   public void contactOnKeyReleased(KeyEvent event) {

    }
    public boolean isValid(){

        return true;
    }
}
