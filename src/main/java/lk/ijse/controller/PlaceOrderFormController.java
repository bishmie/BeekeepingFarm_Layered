package lk.ijse.controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.bo.custom.ProductBO;
import lk.ijse.db.DbConnection;

import lk.ijse.entity.Customer;
import lk.ijse.model.*;
import lk.ijse.tm.CartTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController {


    public AnchorPane rootNode;
    public TableView<CartTM> tblPlaceOrder;
    public TableColumn<?, ?> colProductId;
    public TableColumn<?, ?> colProductName;
    public TableColumn<?, ?> colQty;
    public TableColumn<?, ?> colSellingPrice;
    public TableColumn<?, ?> colTotal;
    public TableColumn<?, ?> colAction;
    public TextField txtCashReceived;
    public Label lblOrderId;
    public Label lblOderDate;
    public Label lblSellingPrice;
    public Label lblQtyOnHand;
    public Label lblCustomerName;
    public Label lblProductName;
    @FXML
    private ComboBox<String> cmbCustomerId;

    public TextField txtQty;

    public ComboBox<String> cmbProductId;



    public JFXButton btnAddToCart;
    public Label lblSubTotal;
    public Label lblCashReceived;
    public Label lblBalance;

    CustomerBO customerBO =(CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    PlaceOrderBO placeOrderBO =(PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() {
          setDate();
         getCurrentOrderId();
         getCustomerIds();
        getProductIds();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getProductIds() {
        ObservableList<String> productIds = FXCollections.observableArrayList();
        try {
            ArrayList<String> codeList = productBO.getProductCodes();

            for (String code : codeList) {
                productIds.add(code);
            }
            cmbProductId.setItems( productIds);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomerIds() {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        try {
            ArrayList<String> allIds = customerBO.getAllCusIds();
            for(String i : allIds){
                customerIds.add(i);
            }
            cmbCustomerId.setItems(customerIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void getCurrentOrderId() {
        try {
            String currentId = placeOrderBO.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private String generateNextOrderId(String currentId) {
        try {
            return placeOrderBO.generateNewOrderId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "OID-001";

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOderDate.setText(String.valueOf(now));
    }

        public void btnAddToCartOnAction (ActionEvent actionEvent) {


                String code = cmbProductId.getValue();
                String description = lblProductName.getText();
                int qty = Integer.parseInt(txtQty.getText());
                double unitPrice = Double.parseDouble(lblSellingPrice.getText());
                double total = qty * unitPrice;
                JFXButton btnRemove = new JFXButton("remove");
                btnRemove.setCursor(Cursor.HAND);

                btnRemove.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                        obList.remove(selectedIndex);

                        tblPlaceOrder.refresh();
                        calculateNetTotal();
                    }
                });

                for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                    if(code.equals(colProductId.getCellData(i))) {

                        CartTM tm = obList.get(i);
                        qty += tm.getQty();
                        total = qty * unitPrice;

                        tm.setQty(qty);
                        tm.setTotal(total);

                        tblPlaceOrder.refresh();

                        calculateNetTotal();
                        return;
                    }
                }

                CartTM tm = new CartTM(code, description, qty, unitPrice, total, btnRemove);
                obList.add(tm);

                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                txtQty.setText("");
            }

    private int calculateNetTotal() {
        int SubTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            SubTotal += (double) colTotal.getCellData(i);
        }
        lblSubTotal.setText(String.valueOf(SubTotal));
        //btnMoneyReceiving();
        return SubTotal;
    }



    /*private void calculateBalance() {
        int netTotal = calculateNetTotal();
        int receivedAmount = Integer.parseInt(txtCashReceived.getText());
        int balance = receivedAmount - netTotal;
        lblBalance.setText(String.valueOf(balance));

    }*/


        public void btnConfirmOrderOnAction (ActionEvent actionEvent){

            String orderId = lblOrderId.getText();
            String cusId = cmbCustomerId.getValue();
            Date date = Date.valueOf(LocalDate.now());

            var order = new OrderDTO(orderId, cusId, date);

            List<OrderProductDTO> odList = new ArrayList<>();

            for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                CartTM tm = obList.get(i);

                OrderProductDTO od = new OrderProductDTO(
                        orderId,
                        tm.getProductId(),
                        tm.getQty(),
                        tm.getSellingPrice()
                );

                odList.add(od);
            }
            // code //////////////////////////

            PlaceOrderDTO po = new PlaceOrderDTO(order, odList);
            try {
                boolean isPlaced = placeOrderBO.placeOrder(po);
                if(isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                    tblPlaceOrder.refresh();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


            }



    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String id = cmbCustomerId.getValue();
       // System.out.println("combo Values : "+ id);
        try {
            CustomerDTO customer = customerBO.searchCustomer(id);
            if(customer != null) {
                lblCustomerName.setText(customer.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void cmbProductOnAction(ActionEvent actionEvent) {
        String id = cmbProductId.getValue();
        try {
            ProductDTO productDTO = productBO.searchProduct(id);
            if(productDTO != null) {
                lblProductName.setText(productDTO.getProductName());
                lblSellingPrice.setText(productDTO.getSellingPrice());
                lblQtyOnHand.setText(productDTO.getQty());
            }
            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtQtyOnAction(ActionEvent event) {
            btnAddToCartOnAction(event);

    }

    public void txtCashReceivedOnAction(ActionEvent actionEvent) {
    }


    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/orderPlaceReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", lblOrderId.getText());

        JasperPrint jasperPrint =
                null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void btnMoneyReceiving(ActionEvent actionEvent) {

    }
}

