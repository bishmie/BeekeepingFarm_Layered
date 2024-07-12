package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.TaskDTO;

import java.sql.SQLException;

public interface TaskBO extends SuperBO {
    boolean saveTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException;

    boolean updateTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException;

    boolean deleteTask(String id) throws SQLException, ClassNotFoundException;

    TaskDTO searchTask(String id) throws SQLException, ClassNotFoundException;
}
