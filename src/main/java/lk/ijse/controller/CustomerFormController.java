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
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerContact.setText("");
        txtCustomerEmail.setText("");
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtCustomerId.getText();

        String sql = "SELECT * FROM customer WHERE customerId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String email = resultSet.getString(5);


                txtCustomerName.setText(name);
                txtCustomerAddress.setText(address);
                txtCustomerContact.setText(contact);
                txtCustomerEmail.setText(email);
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer ID Not Found!");
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) {


    }

    @FXML
     void btnDeleteOnAction(ActionEvent actionEvent) {

    }



    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        navigateToDashBoard();

    }

    private void navigateToDashBoard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        navigateToLoginPage();

    }

    private void navigateToLoginPage() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
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
