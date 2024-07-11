package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Harvest;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HarvestDAO extends CrudDao<Harvest> {
    ArrayList<String> getHarvestIds() throws SQLException, ClassNotFoundException;
}
