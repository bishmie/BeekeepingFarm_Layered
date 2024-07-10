package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.HiveBO;
import lk.ijse.db.DbConnection;

import lk.ijse.entity.BeeHive;
import lk.ijse.model.BeeHiveDTO;
import lk.ijse.tm.HiveTM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HiveManageController {


    public TableView<HiveTM> tblHive;

    public AnchorPane rootNode;
    public TableColumn<?,?> colHiveId;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colLocation;
    public TableColumn<?,?> colInspectionDate;
    public TableColumn<?,?> colResult;

    public TableColumn<?,?> colPopulation;

    public TextField txtTYpe;
    public TextField txtLocation;
    public TextField txtPOpulation;
    public TextField txtInspectiondate;
    public TextField txtResults;
    public TextField txtHiveid;
    public ComboBox cmbQueenid;


    HiveBO hiveBO =(HiveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HIVE);
    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }
    private void setCellValueFactory() {
        colHiveId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
        colInspectionDate.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    private void loadAllCustomers() {
        tblHive.getItems().clear();
        try {
            ArrayList<BeeHiveDTO> getAll = hiveBO.getAllHives();
            for(BeeHiveDTO b : getAll){
                tblHive.getItems().add(new HiveTM(b.getBeehiveId(),b.getType(),b.getLocation(),b.getPopulation(),b.getInspectionDate(),b.getInspectionResult()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }





    public void btnSetOnAction(ActionEvent actionEvent) {

        String id = txtHiveid.getText();
        String type = txtTYpe.getText();
        String location = txtLocation.getText();
        String population = txtPOpulation.getText();
        String inspectionDate = txtInspectiondate.getText();
        String inspectionResult = txtResults.getText();

        try {
            boolean isSaved = hiveBO.saveHives(new BeeHiveDTO(id,type,location,population,inspectionDate,inspectionResult));
            System.out.println("weda karapan yakooooooo");
            tblHive.getItems().add(new HiveTM(id,type,location,population,inspectionDate,inspectionResult));

            if(isSaved){
               new Alert(Alert.AlertType.INFORMATION,"hive successfully saved").show();
            }

        } catch (SQLException e) {
            System.out.println("Waradi1");
            new Alert(Alert.AlertType.ERROR,"hive does not save").show();
        } catch (ClassNotFoundException e) {
            System.out.println("Waradi2 ");
            throw new RuntimeException(e);

        }
        clearFields();


    }

    private void clearFields() {
        txtHiveid.setText("");
        txtTYpe.setText("");
        txtLocation.setText("");
        txtPOpulation.setText("");
        txtInspectiondate.setText("");
        txtResults.setText("");

        //
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtHiveid.getText();
        String type = txtTYpe.getText();
        String location = txtLocation.getText();
        String population = txtPOpulation.getText();
        String inspectionDate = txtInspectiondate.getText();
        String inspectionResult = txtResults.getText();

        try {
            boolean isUpdate = hiveBO.updateHive(new BeeHiveDTO(id,type,location,population,inspectionDate,inspectionResult));
            if(isUpdate) {
                this.loadAllCustomers();
                new Alert(Alert.AlertType.INFORMATION, "Hive successfully updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"hive does not updated.").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtHiveid.getText();

        try {
            boolean isDelete = hiveBO.delete(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"hive deleted successfully").show();
                loadAllCustomers();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"hive does not deleted").show();;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtHiveid.getText();

        try {
            BeeHiveDTO beeHiveDTO = hiveBO.searchHive(id);

            if(beeHiveDTO != null){
                txtTYpe.setText(beeHiveDTO.getType());
                txtLocation.setText(beeHiveDTO.getLocation());
                txtPOpulation.setText(beeHiveDTO.getPopulation());
                txtInspectiondate.setText(beeHiveDTO.getInspectionDate());
                txtResults.setText(beeHiveDTO.getInspectionResult());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"hive id not found");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    public  void inspectionDateOnKeyReleased( ) {

    }

    public void hiveIdOnKeyReleased(KeyEvent keyEvent) {

    }
    public boolean isValid(){

        return true;
    }

    public void typeOnKeyReleased(KeyEvent keyEvent) {

    }

    public void locationOnKeyReleased(KeyEvent keyEvent) {
    }

    public void populationOnKeyReleased(KeyEvent keyEvent) {
    }

    public void resultOnKeyReleased(KeyEvent keyEvent) {
    }
}

