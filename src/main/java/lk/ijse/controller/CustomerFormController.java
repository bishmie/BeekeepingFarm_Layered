package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DbConnection;

import lk.ijse.model.CustomerDTO;

import lk.ijse.tm.CustomerTM;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
   @FXML
    private TextField txtCustomerId;
   @FXML
    private TextField txtCustomerName;
   @FXML
    private TextField txtCustomerAddress;
   @FXML
    private TextField txtCustomerContact;
   @FXML
    private TextField txtCustomerEmail;
   @FXML
    private TableView<CustomerTM> tblCustomerTable;
  @FXML
    private AnchorPane rootNode;
  @FXML
    private TableColumn<?,?> colCustomerId;
  @FXML
    private TableColumn<?,?> colName;
  @FXML
    private TableColumn<?,?> colAddress;
  @FXML
    private TableColumn<?,?> colContact;
  @FXML
    private TableColumn<?,?> colEmail;

  CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }
    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllCustomers() {
        tblCustomerTable.getItems().clear();


        try {
            ArrayList<CustomerDTO> loadAllCustomers = customerBO.getAllCustomers();
            for(CustomerDTO c : loadAllCustomers){
                tblCustomerTable.getItems().add(new CustomerTM(c.getCustomerId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }



  @FXML
     void btnSetOnAction(ActionEvent actionEvent) {

      String customerId = txtCustomerId.getText();
      String name = txtCustomerName.getText();
      String address = txtCustomerAddress.getText();
      String contact = txtCustomerContact.getText();
      String email = txtCustomerEmail.getText();


        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        try{
            customerBO.saveCustomers(new CustomerDTO(customerId,name,address,contact,email));
            tblCustomerTable.getItems().add(new CustomerTM(customerId, name, address,contact,email));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
      clearFields();
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();
        txtCustomerEmail.clear();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtCustomerId.getText();

        String sql = "SELECT * FROM customer WHERE customerId =?";

        try{
            CustomerDTO customerDTO = customerBO.searchCustomer(id);
            if(customerDTO != null){

                txtCustomerName.setText(customerDTO.getName());
                txtCustomerAddress.setText(customerDTO.getAddress());
                txtCustomerContact.setText(customerDTO.getContact());
                txtCustomerEmail.setText(customerDTO.getEmail());
            }
             else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer ID Not Found!");
        }





    }
    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String CustomerId = txtCustomerId.getText();
        String Name = txtCustomerName.getText();
        String Address = txtCustomerAddress.getText();
        String Contact = txtCustomerContact.getText();
        String Email = txtCustomerEmail.getText();


        boolean isUpdate = false;
        try {
            isUpdate = customerBO.updateCustomer(new CustomerDTO(CustomerId,Name,Address,Contact,Email));
            tblCustomerTable.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
        }
        clearFields();
    }

    @FXML
     void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        try {
            boolean isdelete = customerBO.delete(id);
            if(isdelete){
                new Alert(Alert.AlertType.CONFIRMATION, "customer is successfully deleted!").show();

            }

            tblCustomerTable.getItems().remove(tblCustomerTable.getSelectionModel().getSelectedItem());
            tblCustomerTable.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        clearFields();

    }






    public void btnSendEmailsOnAction(ActionEvent actionEvent) throws IOException {
        navigateToEmailForm();
    }

    private void navigateToEmailForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/emailForm.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        rootNode.getChildren().clear();
        rootNode.getChildren().add(PerenetRootNode);


    }

    public void emailAddressOnKeyReleased(KeyEvent keyEvent) {

    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {

    }

    public void customerIdOnKeyReleased(KeyEvent keyEvent) {

    }
    public boolean isValid(){
       return true;
    }

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {


    }
}
