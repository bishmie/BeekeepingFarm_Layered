package lk.ijse.controller;

import com.beust.ah.A;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BeekeeperBO;
import lk.ijse.bo.custom.TaskBO;
import lk.ijse.db.DbConnection;

import lk.ijse.model.BeekeeperDTO;
import lk.ijse.model.TaskDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BeeKeeperManageController {
    public AnchorPane rootNode;
    public TextField txtBeeKeeperId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtSalary;
    public TextField txtTaskId;
    public TextField txtTaskName;
    public DatePicker dpDueDate;
    public TextArea txtAreaDescription;
    public ComboBox cmbBeeKeeperId;
    public TextField txtDescription;

    BeekeeperBO beekeeperBO = (BeekeeperBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BEEKEEPER);
    TaskBO taskBO = (TaskBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TASK);
    public void initialize(){
        getBeeKeeperId();

    }

    private void getBeeKeeperId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            ArrayList<String> idList = beekeeperBO.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbBeeKeeperId.setItems(obList);

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
        String id = txtBeeKeeperId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String salary = txtSalary.getText();

        String sql = "INSERT INTO beekeeper Values(?,?,?,?,?,?)";

        try {
            boolean isSaved = beekeeperBO.saveBeekeeper(new BeekeeperDTO(id,name,address,contact,email,salary));


            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Beekeeper is Saved Successfully").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }

    private void clearFields() {
        txtBeeKeeperId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String beekeeperId = txtBeeKeeperId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtContact.getText();
        String Email = txtEmail.getText();
        String Salary = txtSalary.getText();


        try {
            boolean isUpdate = beekeeperBO.updateBeekeeper(new BeekeeperDTO(beekeeperId,Name, Address,Contact,Email,Salary));
        if(isUpdate ){
            new Alert(Alert.AlertType.INFORMATION,"beekeeper updated successfully").show();
        }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"beekeeper  does not updated ").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
clearTaskFields();
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtBeeKeeperId.getText();
        try {
            boolean isDelete = beekeeperBO.deleteBeekeeper(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"beekeeper deleted successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"beekeeper does not deleted ").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
clearFields();

    }



    public void btnAssignTaskOnAction(ActionEvent actionEvent) {
        if (!isValidTask()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtTaskId.getText();
        String name = txtTaskName.getText();
        String description = txtAreaDescription.getText();
        String date = String.valueOf(dpDueDate.getValue());
        String beekeeper = (String) cmbBeeKeeperId.getValue();



        try {
            boolean isSaved = taskBO.saveTask(new TaskDTO(id,name,description,date,beekeeper));



            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "task Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearTaskFields();
    }



    public void btnUpdateTaskOnAction(ActionEvent actionEvent) {
        if (!isValidTask()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String taskId = txtTaskId.getText();
        String Name = txtTaskName.getText();
        String description = txtAreaDescription.getText();
        String date = String.valueOf(dpDueDate.getValue());
        String beekeeperId = (String) cmbBeeKeeperId.getValue();


        try {
            boolean isUpdate = taskBO.updateTask(new TaskDTO(taskId,Name,description,date,beekeeperId));
            if(isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Task updated successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Task does not updated ").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnDeleteTaskOnAction(ActionEvent actionEvent) {
        if (!isValidTask()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtTaskId.getText();

        try {
            boolean isDelete = taskBO.deleteTask(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Task deleted successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Task does not deleted ").show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



    private void clearTaskFields() {
        txtTaskId.setText("");
        txtTaskName.setText("");
        txtAreaDescription.setText("");
        dpDueDate.setValue(null);
        cmbBeeKeeperId.setValue(null);

    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtBeeKeeperId.getText();

        String sql = "SELECT * FROM beekeeper WHERE beekeeperId =?";

        try{
            BeekeeperDTO beekeeperDTO = beekeeperBO.searchBeekeeper(id);
           if(beekeeperDTO!=null){


                txtName.setText(beekeeperDTO.getName());
                txtAddress.setText(beekeeperDTO.getAddress());
                txtContact.setText(beekeeperDTO.getContact());
                txtEmail.setText(beekeeperDTO.getEmail());
                txtSalary.setText(beekeeperDTO.getSalary());


            } else {
                new Alert(Alert.AlertType.ERROR, "Beekeeper Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Beekeeper ID Not Found!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnTaskSearchOnAction(ActionEvent actionEvent) {
        String id = txtTaskId.getText();


        try{
            TaskDTO taskDTO = taskBO.searchTask(id);

            if(taskDTO!=null){
                txtTaskName.setText(taskDTO.getTaskName());
                txtAreaDescription.setText(taskDTO.getDescription());
                dpDueDate.setValue(LocalDate.parse(taskDTO.getDueDate()));
                cmbBeeKeeperId.setValue(taskDTO.getBeekeeperId());

            }



            else {
                new Alert(Alert.AlertType.ERROR, "task Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"task ID Not Found!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.CONTACT, txtContact);
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.EMAIL, txtEmail);
    }

    public void taskIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.TID, txtTaskId);
    }

    public void beekeeperIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.BID, txtBeeKeeperId);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.BID,txtBeeKeeperId)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.CONTACT,txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.EMAIL,txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtSalary)) return false;

        return true;
    }
    public boolean isValidTask(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.TID,txtTaskId)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField. DESCRIPTION,txtTaskName)) return false;
        if (!Regex.setTextColorTxtArea(lk.ijse.Util.TextField.DESCRIPTION,txtAreaDescription)) return false;

        return true;
    }

    public void taskNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtTaskName);

    }

    public void salaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE, txtSalary);
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DESCRIPTION, txtAddress);
    }

    public void beekeeperNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtName );
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColorTxtArea(lk.ijse.Util.TextField.DESCRIPTION,txtAreaDescription );

    }


    }



