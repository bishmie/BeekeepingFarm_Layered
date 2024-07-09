package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProductBO;
import lk.ijse.db.DbConnection;

import lk.ijse.model.ProductDTO;
import lk.ijse.tm.ProductTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductFormController {
    public AnchorPane rootNode;
    public TableView<ProductTM> tblHive;
    public TableColumn<?,?> colProductId;
    public TableColumn<?,?> colProductName;
    public TableColumn<?,?> colQtyOnHand;
    public TextField txtProductId;
    public TextField txtNetWeight;
    public TextField txtProductName;
    public TextField txtQty;
    public TextField txtSellingPrice;
    public TableView<ProductTM> tblProduct;
    public ComboBox cmbHarvestId;


   // ProductBOImpl productBO = new ProductBOImpl();
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        getHarvestIds();

    }

    private void getHarvestIds() {

    }


    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    private void loadAllCustomers() {
        tblProduct.getItems().clear();
        ArrayList<ProductDTO> getAllProducts = null;
        try {
            getAllProducts = productBO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(ProductDTO p : getAllProducts){
         tblProduct.getItems().add(new ProductTM(p.getProductId(),p.getProductName(),p.getQty()));
     }

    }



    public void btnSetOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();
        String harvestId = (String) cmbHarvestId.getValue();

        try {
            boolean isSaved = productBO.saveProduct(new ProductDTO(ProductId,ProductName,SellingPrice,NetWeight,Qty,harvestId));

       if(isSaved){
           new Alert(Alert.AlertType.INFORMATION, "Product saved Successfully ").show();

       }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the product " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // tblProduct.getItems().add(new ProductTM(ProductId, ProductName, SellingPrice,NetWeight,Qty,harvestId));

        clearFields();
    }

    private void clearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtSellingPrice.setText("");
        txtNetWeight.setText("");
        txtQty.setText("");
        cmbHarvestId.setValue(null);
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();
        String harvestId = (String) cmbHarvestId.getValue();


        boolean isUpdate = productBO.updateProduct(new ProductDTO(ProductId, ProductName, SellingPrice, NetWeight, Qty, harvestId));
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Product is Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Product is Not Updated").show();

            clearFields();

        }
    }
        public void btnDeleteOnAction (ActionEvent actionEvent){


            if (!isValid()) {
                // If validation fails, show an error alert and return early
                new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
                return;
            }

            String id = txtProductId.getText();

            try {
                boolean isDeleted = productBO.delete(id);
                if(isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "product is successfully deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            clearFields();
        }


        public void btnSearchOnAction (ActionEvent actionEvent){
            String id = txtProductId.getText();



            try {
              ProductDTO productDTO =  productBO.searchProduct(id);
                if (productDTO != null){
                    txtProductName.setText(productDTO.getProductName());
                    txtSellingPrice.setText(productDTO.getSellingPrice());
                    txtNetWeight.setText(productDTO.getNetWeight());
                    txtQty.setText(productDTO.getQty());
                    cmbHarvestId.setValue(productDTO.getHarvestId());

                }

                else {
                    new Alert(Alert.AlertType.ERROR, "Product Not Found").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.INFORMATION, "Product ID Not Found!");
            }
        }

    public void txtProductIdOnKeyReleased(KeyEvent keyEvent) {

    }

    public void netWeightOnKeyReleased(KeyEvent keyEvent) {

    }

    public void productNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void qtyOnKeyReleased(KeyEvent keyEvent) {
    }

    public void sellingPriceOnKeyReleased(KeyEvent keyEvent) {
    }
    public boolean isValid(){
        return true;
    }


    public void btnHarvestIdOnAction(ActionEvent actionEvent) {

    }

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/ProductReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}


