package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TaskBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.TaskDAO;
import lk.ijse.entity.Task;
import lk.ijse.model.TaskDTO;

import java.sql.SQLException;

public class TaskBOImpl implements TaskBO {
TaskDAO taskDAO = (TaskDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TASK);
    @Override
    public boolean saveTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        return taskDAO.save(new Task(taskDTO.getTaskId(),taskDTO.getTaskName(),taskDTO.getDescription(),taskDTO.getDueDate(),taskDTO.getBeekeeperId()));
    }

    @Override
    public boolean updateTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        return taskDAO.update(new Task(taskDTO.getTaskId(),taskDTO.getTaskName(),taskDTO.getDescription(),taskDTO.getDueDate(),taskDTO.getBeekeeperId()));
    }

    @Override
    public boolean deleteTask(String id) throws SQLException, ClassNotFoundException {
        return taskDAO.delete(id);
    }

    @Override
    public TaskDTO searchTask(String id) throws SQLException, ClassNotFoundException {
    Task task = taskDAO.search(id);
    return new TaskDTO(task.getTaskId(),task.getTaskName(),task.getDescription(),task.getDueDate(),task.getBeekeeperId());
    }
}
