package lk.ijse.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.HiveBO;
import lk.ijse.bo.custom.InventoryBO;
import lk.ijse.db.DbConnection;
import lk.ijse.model.InventoryDTO;

import lk.ijse.Util.Regex;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryFormController implements Initializable {


    public AnchorPane rootNode;
    public TextField txtInventoryId;
    public TextField txtType;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public ComboBox cmbInventoryId;
    public TextField txtSupplierAddress;
    public ComboBox cmbBeeHiveId;
    public TextField txtContact;
    public TextField txtEmail;

    @FXML
    private PieChart pieChart;
    HiveBO hiveBO = (HiveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HIVE);
    InventoryBO inventoryBO=(InventoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INVENTORY);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Wooden Bars", 20),
                        new PieChart.Data("Boxes", 45),
                        new PieChart.Data("sugar syrup", 60),
                        new PieChart.Data("Queen Catcher", 50),
                        new PieChart.Data("Gloves",39),
                        new PieChart.Data("bee brush",10));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);



        pieChart.setDepthTest(DepthTest.ENABLE);
        pieChart.setStartAngle(90); // Adjust start angle for a 3D-like effect

    }

    public void initialize() {
        getBeeHiveIds();
    }

    private void getBeeHiveIds() {
        ObservableList<String> getHiveIds = FXCollections.observableArrayList();
        try {
            ArrayList<String> hiveIds= hiveBO.getHiveIds();
            for(String i : hiveIds){
                getHiveIds.add(i);
            }
            cmbBeeHiveId.setItems(getHiveIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSetOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtInventoryId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();

        boolean isSaved = false;
        try {
            isSaved = inventoryBO.saveInventory(new InventoryDTO(id,type,description,qty,unitPrice,beehiveId));
            new Alert(Alert.AlertType.INFORMATION,"inventory saved successfully").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"inventory does not saved ").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully").show();
            }
clearFields();
    }

    private void clearFields() {
        txtInventoryId.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        cmbBeeHiveId.setValue("");
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String inventoryId = txtInventoryId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();

        try {
            boolean isUpdate = inventoryBO.updateInventory(new InventoryDTO(inventoryId,type,description,qty,unitPrice,beehiveId));
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"inventory updated successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"inventory does not updated ").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtInventoryId.getText();

        try {
            boolean isDeleted = inventoryBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }



    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtInventoryId.getText();


        InventoryDTO inventoryDTO = null;
        try {
            inventoryDTO = inventoryBO.search(id);
            if (inventoryDTO != null) {
                txtInventoryId.setText(inventoryDTO.getInventoryId());
                txtType.setText(inventoryDTO.getType());
                txtDescription.setText(inventoryDTO.getDescription());
                txtQty.setText(inventoryDTO.getQty());
                txtUnitPrice.setText(inventoryDTO.getUnitPrice());
                cmbBeeHiveId.setValue(inventoryDTO.getBeeHiveId());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public  void inventoryIdOnKeyReleased( ) {
        Regex.setTextColor(lk.ijse.Util.TextField.IID, txtInventoryId);
    }

    public void typeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtType);
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtDescription);
    }

    public void qtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY, txtQty);
    }

    public void unitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE, txtUnitPrice);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.IID,txtInventoryId)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtType)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtDescription)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtUnitPrice)) return false;

        return true;
    }
}

