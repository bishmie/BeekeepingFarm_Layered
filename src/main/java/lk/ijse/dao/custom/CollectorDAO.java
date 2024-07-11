package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Collector;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CollectorDAO extends CrudDao<Collector> {
    ArrayList<String> getCollectorIds() throws SQLException, ClassNotFoundException;
}
