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
        System.out.println("aiooooo");
        boolean save = false;
        try {

            save = SQLUtil.execute("INSERT INTO beehive (beehiveId,type,location,population,inspectionDate,inspectionResult) VALUES (?,?,?,?,?,?)",entity.getBeehiveId(),entity.getType(),entity.getLocation(),entity.getPopulation(),entity.getInspectionDate(),entity.getInspectionResult());



        }catch (SQLException e){
            System.out.println(e);
        }
        return save;
    }

    @Override
    public BeeHive search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM beehive WHERE beehiveId=?",id);
    if(resultSet.next()){
        String type = resultSet.getString(2);
        String location = resultSet.getString(3);
        String population = resultSet.getString(4);
        String inspectionDate = resultSet.getString(5);
        String inspectionResult = resultSet.getString(6);
        return new BeeHive(resultSet.getString(1),type,location,population,inspectionDate,inspectionResult);
    }
    return null;
    }

    @Override
    public boolean update(BeeHive entity) throws SQLException, ClassNotFoundException {

        boolean update = false;

        try{

            update = SQLUtil.execute("UPDATE beehive SET type =?, location =? , population =? ,inspectionDate =? , inspectionResult=? WHERE beehiveId =?", entity.getType(), entity.getLocation(), entity.getPopulation(), entity.getInspectionDate(), entity.getInspectionResult(),entity.getBeehiveId());
            System.out.println("update : "+ update);
        }catch (SQLException e){
            System.out.println(e);
        }

        return update;


    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM beehive WHERE beehiveId = ?", id);
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
