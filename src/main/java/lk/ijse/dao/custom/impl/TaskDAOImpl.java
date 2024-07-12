package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.TaskDAO;
import lk.ijse.entity.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public boolean save(Task entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO task (taskId,taskName,description,dueDate,beekeeperId) VALUES (?,?,?,?,?)",entity.getTaskId(),entity.getTaskName(),entity.getDescription(),entity.getDueDate(),entity.getBeekeeperId() );
    }

    @Override
    public Task search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM task WHERE taskId =?",id);
        if(resultSet.next()){
            String name= resultSet.getString(2);
            String description = resultSet.getString(3);
            String date=resultSet.getString(4);
            String beehiveid = resultSet.getString(5);

            return (new Task(resultSet.getString(1),name,description,date,beehiveid));


        }
        return null;

    }

    @Override
    public boolean update(Task entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE task SET taskName =?, description =?, dueDate =?, beekeeperId =? WHERE taskId =?",entity.getTaskName(),entity.getDescription(),entity.getDueDate(),entity.getBeekeeperId(),entity.getTaskId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM task WHERE taskId = ?",id);
    }

    @Override
    public ArrayList<Task> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
