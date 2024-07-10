package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.HiveDAO;
import lk.ijse.entity.BeeHive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HiveDAOImpl implements HiveDAO {
    @Override
    public boolean save(BeeHive entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BeeHive search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(BeeHive entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<BeeHive> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM beehive");
        ArrayList<BeeHive> all = new ArrayList<>();
        while (resultSet.next()){
            BeeHive beeHive = new BeeHive(resultSet.getString("beehiveId"),resultSet.getString("type"),resultSet.getString("location"),resultSet.getString("population"),resultSet.getString("inspectionDate"),resultSet.getString("inspectionResult"));
            all.add(beeHive);
        }
        return all;

    }
}
