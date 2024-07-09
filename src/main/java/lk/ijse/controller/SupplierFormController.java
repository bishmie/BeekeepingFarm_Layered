package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.db.DbConnection;
import lk.ijse.model.SupplierDTO;
import lk.ijse.tm.CustomerTM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    public AnchorPane rootNode;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public ComboBox cmbInventoryId;
    public TextField txtSupplierAddress;
    public TextField txtContact;
    public TextField txtEmail;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() {
        getInventoryIds();
    }

    private void getInventoryIds() {

    }

    public void btnTaskSearchOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();

        String sql = "SELECT * FROM supplier WHERE supplierId =?";

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
                String inventoryId= resultSet.getString(6);


                txtSupplierId.setText(id);
                txtSupplierName.setText(name);
                txtSupplierAddress.setText(address);
                txtContact.setText(contact);
                txtEmail.setText(email);
                cmbInventoryId.setValue(inventoryId);

            } else {
                new Alert(Alert.AlertType.ERROR, "supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"supplier ID Not Found!");
        }

    }


    public void btnSetSupplierOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String inventoryId = (String) cmbInventoryId.getValue();


        try {
       //    supplierBO.saveSupplier(new SupplierDTO(id,name,address,contact,email,inventoryId));


            boolean isSaved = supplierBO.saveSupplier(new SupplierDTO(id,name,address,contact,email,inventoryId));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearSupplierFields();
    }
        private void clearSupplierFields() {
            txtSupplierId.setText("");
            txtSupplierName.setText("");
            txtSupplierAddress.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            cmbInventoryId.setValue("");
        }


    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String supplierId = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String inventoryId= (String) cmbInventoryId.getValue();


        try {
            boolean isUpdate = supplierBO.updateSupplier(new SupplierDTO(supplierId,name,address,contact,email,inventoryId));
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier does Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearSupplierFields();

    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {

    }



    public void supplierOnKeyReleased(KeyEvent keyEvent) {
    }
    public boolean isValid(){
        return  true;
    }

    public void supNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {

    }
}