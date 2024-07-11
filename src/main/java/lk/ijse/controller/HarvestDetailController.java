package lk.ijse.controller;

import com.beust.ah.A;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CollectorBO;
import lk.ijse.bo.custom.HarvestBO;
import lk.ijse.bo.custom.HiveBO;
import lk.ijse.dao.custom.HarvestDAO;

import lk.ijse.model.HarvestDTO;
import lk.ijse.tm.harvestTM;

//import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HarvestDetailController {



    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colGrade;

    @FXML
    private TableColumn<?, ?> colHarvestId;

    @FXML
    private TableColumn<?, ?> colHarvestType;

    @FXML
    private TableColumn<?, ?> colProductionDate;

    @FXML
    private TableColumn<?, ?> colQualityNote;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<harvestTM> tblHarvest;

    @FXML
    private TextField txtAmountOfLiters;

    @FXML
    private TextField txtGrade;

    @FXML
    private TextField txtHarvestId;

    @FXML
    private TextField txtHarvestType;

    @FXML
    private TextField txtProductionDate;

    @FXML
    private TextField txtQualityNotes;

    @FXML
    private ComboBox<String> cmbBeehiveId;

    @FXML
    private ComboBox<String> cmbCollectorId;
    HiveBO hiveBO = (HiveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HIVE);
    HarvestBO harvestBO=(HarvestBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HARVEST);
    CollectorBO collectorBO=(CollectorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COLLECTOR);
    public void initialize() {
        getBeehiveIds();
        getCollectorIds();
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colHarvestId.setCellValueFactory(new PropertyValueFactory<>("harvestId"));
        colProductionDate.setCellValueFactory(new PropertyValueFactory<>("productionDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amountOfLiters"));
        colQualityNote.setCellValueFactory(new PropertyValueFactory<>("QualityNotes"));
        colHarvestType.setCellValueFactory(new PropertyValueFactory<>("HarvestType"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    private void loadAllCustomers() {
        tblHarvest.getItems().clear();
        try {
            ArrayList<HarvestDTO> allHarvest = harvestBO.loadAll();
            for(HarvestDTO h : allHarvest){
                tblHarvest.getItems().add(new harvestTM(h.getHarvestId(),h.getProductionDate(),h.getAmountOfLiters(),h.getQualityNotes(),h.getBeehiveId(),h.getCollectorId(),h.getHarvestType(),h.getGrade()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCollectorIds() {
        ObservableList<String> collectorIds = FXCollections.observableArrayList();

        try {
            ArrayList<String> allIds =collectorBO.getCollectorIds();
            for(String i : allIds){
                collectorIds.add(i);
            }
            cmbCollectorId.setItems(collectorIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void getBeehiveIds() {
        ObservableList<String> getHiveIds = FXCollections.observableArrayList();
        try {
            ArrayList<String> hiveIds= hiveBO.getHiveIds();
            for(String i : hiveIds){
                getHiveIds.add(i);
            }
            cmbBeehiveId.setItems(getHiveIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtHarvestId.getText();
        try {
            boolean isDelete = harvestBO.deleteHarvest(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"harvest deleted").show();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"harvest does not deleted").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSetOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtHarvestId.getText();
        String date = txtProductionDate.getText();
        String amount = txtAmountOfLiters.getText();
        String qualityNotes = txtQualityNotes.getText();
        String beehiveId = (String) cmbBeehiveId.getValue();
        String collectorId = (String) cmbCollectorId.getValue();
        String harvestType = txtHarvestType.getText();
        String grade = txtGrade.getText();

        try {
            boolean isSaved= harvestBO.saveHarvest(new HarvestDTO(id,date,amount,qualityNotes,beehiveId,collectorId,harvestType,grade));
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"harvest details saved").show();
               this.loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"harvest details not saved").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void clearFields() {

            txtHarvestId.setText("");
            txtProductionDate.setText("");
            txtAmountOfLiters.setText("");
            txtQualityNotes.setText("");
        cmbBeehiveId.setValue(null);
        cmbCollectorId.setValue(null);
        txtHarvestType.setText("");
            txtGrade.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtHarvestId.getText();
        String date = txtProductionDate.getText();
        String amount = txtAmountOfLiters.getText();
        String qualityNotes = txtQualityNotes.getText();
        String beehiveId = (String) cmbBeehiveId.getValue();
        String collectorId = (String) cmbCollectorId.getValue();
        String harvestType = txtHarvestType.getText();
        String grade = txtGrade.getText();

        try {
            boolean isUpdate = harvestBO.updateHarvest(new HarvestDTO(id,date,amount,qualityNotes,beehiveId,collectorId,harvestType,grade));
        if(isUpdate){
            System.out.println("hi");
            new Alert(Alert.AlertType.INFORMATION,"harvest updated").show();
            loadAllCustomers();
        }
        } catch (SQLException e) {
            System.out.println("hello");
            new Alert(Alert.AlertType.ERROR,"harvest not updated").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtHarvestId.getText();



    }

    public void productionDateOnKeyReleased(KeyEvent keyEvent) {
       // Regex.setTextColor(lk.ijse.util.TextField.DATE, txtProductionDate);
    }

    public void harvestIdOnKeyReleased(KeyEvent keyEvent) {
       // Regex.setTextColor(lk.ijse.util.TextField.HAID, txtHarvestId);
    }

    public void amountOfqtyOnKeyReleased(KeyEvent keyEvent) {
       // Regex.setTextColor(lk.ijse.util.TextField.AMOUNT, txtAmountOfLiters);
    }

    public void harvestTypeOnKeyReleased(KeyEvent keyEvent) {
        //Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtHarvestType);
    }

    public void qualityNotesOnKeyRelleased(KeyEvent keyEvent) {
       // Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtQualityNotes);
    }

    public void gradeOnKeyReleased(KeyEvent keyEvent) {
        //Regex.setTextColor(lk.ijse.util.TextField.GRADE, txtGrade);
    }
    public boolean isValid(){
       /* if (!Regex.setTextColor(lk.ijse.util.TextField.HAID,txtHarvestId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtProductionDate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtAmountOfLiters)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtQualityNotes)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtHarvestType)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.GRADE,txtGrade)) return false;*/
        return true;
    }

    public void btnInsightsOnAction(ActionEvent actionEvent) {
        navigateToTheInsightPage();

    }

    private void navigateToTheInsightPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/harvestVisualization.fxml"));
        Parent PerenetRootNode = null;

        try {
            PerenetRootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        rootNode.getChildren().clear();
        rootNode.getChildren().add(PerenetRootNode);
    }
}
