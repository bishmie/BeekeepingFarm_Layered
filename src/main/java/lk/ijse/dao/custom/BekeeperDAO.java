package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Beekeeper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BekeeperDAO extends CrudDao<Beekeeper> {
    ArrayList<String> getBeekeeperIds() throws SQLException, ClassNotFoundException;
}
