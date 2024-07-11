package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.BeeHive;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HiveDAO extends CrudDao<BeeHive> {
    ArrayList<String> getIds() throws SQLException, ClassNotFoundException;
}
