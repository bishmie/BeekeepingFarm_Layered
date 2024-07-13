package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.HiveBO;
import lk.ijse.bo.custom.QueenBeeBO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.BeeQueen;
import lk.ijse.model.BeeQueenDTO;

import lk.ijse.tm.QueenBeeTM;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueenBeeManageController {
    public AnchorPane rootNode;
    public TextField txtQueenId;
    public TextField txtBreedingHistory;
    public TextField txtBodyFeatures;
    public TextField txtHealthStatus;
    public TextField txtIntroDate;
    public TextField txtVariety;

    public TableColumn<?,?> colQueenBeeId;
    public TableColumn<?,?> colBeeHiveId;
    public TableColumn<?,?> colLocation;

    public TableColumn<?,?> colAvailableQueenId;
    public TableColumn<?,?> colAvailableVariety;
    public TableColumn<?,?> colAvailableBreedHis;
    public ComboBox cmbBeeHiveId;
    public TableView<QueenBeeTM> tblAssignedQueenBees;
    public TableView<QueenBeeTM> tblAvailableQueenBees;

    HiveBO hiveBO = (HiveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HIVE);
    QueenBeeBO queenBeeBO =(QueenBeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUEENBEE);

    public void initialize() {
        getHiveIds();
        setCellValueFactory2();
        loadAllAvailabledQueenBees();

    }


    private void getHiveIds() {
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



    private void setCellValueFactory2() {
        colAvailableQueenId.setCellValueFactory(new PropertyValueFactory<>("queenId"));
        colAvailableVariety.setCellValueFactory(new PropertyValueFactory<>("variety"));
        colAvailableBreedHis.setCellValueFactory(new PropertyValueFactory<>("breedingHistory"));

    }
     private void loadAllAvailabledQueenBees() {
         tblAvailableQueenBees.getItems().clear();


         ArrayList<BeeQueenDTO> allQueenBees = null;
         try {
             allQueenBees = queenBeeBO.getAllQueens();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         } catch (ClassNotFoundException e) {
             throw new RuntimeException(e);
         }
         for (BeeQueenDTO b : allQueenBees) {
                 tblAvailableQueenBees.getItems().add(new QueenBeeTM(b.getQueenId(), b.getVariety(), b.getBreedingHistory()));
             }

     }

    public void btnSetOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();
        String variety = txtVariety.getText();


        try {
          boolean isSaved =  queenBeeBO.saveQueenBee(new BeeQueenDTO(id,breedingHistory,bodyFeatures,healthStatus,introducedDate,beehiveId,variety));
          tblAvailableQueenBees.getItems().add(new QueenBeeTM(id,variety,breedingHistory));

          if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Queen bee is Saved Successfully").show();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Queen bee is Not Saved ").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }

    private void clearFields() {
        txtQueenId.setText("");
        txtBreedingHistory.setText("");
        txtBodyFeatures.setText("");
        txtHealthStatus.setText("");
        txtIntroDate.setText("");
        cmbBeeHiveId.setValue("");
        txtVariety.setText("");

    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String queenbeeId = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();
        String variety = txtVariety.getText();



        try {
           boolean isUpdate = queenBeeBO.updateQueen(new BeeQueenDTO(queenbeeId,breedingHistory,bodyFeatures,healthStatus,introducedDate,beehiveId,variety));
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Queen Bee Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Queen Bee Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtQueenId.getText();

        try {
            boolean isDeleted = queenBeeBO.deleteQueen(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "queen bee is successfully deleted!").show();
                loadAllAvailabledQueenBees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }



    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtQueenId.getText();

        String sql = "SELECT * FROM beequeen WHERE queenId =?";

        BeeQueenDTO beeQueenDTO= null;
        try {
            beeQueenDTO = queenBeeBO.searchQueen(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        if(beeQueenDTO!= null){
                txtBreedingHistory.setText(beeQueenDTO.getBreedingHistory());
                txtBodyFeatures.setText(beeQueenDTO.getBodyFeatures());
                txtHealthStatus.setText(beeQueenDTO.getHealthStatus());
                txtIntroDate.setText(beeQueenDTO.getIntroducedDate());
                cmbBeeHiveId.setValue(beeQueenDTO.getBeehiveId());
                txtVariety.setText(beeQueenDTO.getVariety());


            } else {
                new Alert(Alert.AlertType.ERROR, "queen bee Not Found").show();
            }
    }

    public void cmbBeeHiveIdOnAction(ActionEvent actionEvent) {

    }

    public  void QueenIdOnKeyReleased(KeyEvent keyEvent ) {
        Regex.setTextColor(lk.ijse.Util.TextField.QID, txtQueenId);

    }

    public void breedingHistoryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtBreedingHistory);

    }

    public void bodyfeaturesOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtBodyFeatures);

    }

    public void healthStatusOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtHealthStatus);

    }

    public void introDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DATE, txtIntroDate);

    }

    public void varietyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtVariety);


    }
    public boolean isValid(){


        if (!Regex.setTextColor(lk.ijse.Util.TextField.QID,txtQueenId)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtBreedingHistory)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtBodyFeatures)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtHealthStatus)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtIntroDate)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtVariety)) return false;
        return true;
    }
}
