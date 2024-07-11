package lk.ijse.dao.custom.impl;

import lk.ijse.bo.custom.HarvestBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.HarvestDAO;
import lk.ijse.entity.Harvest;
import net.sf.jasperreports.engine.type.BreakTypeEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HarvestDAOImpl implements HarvestDAO {
    @Override
    public boolean save(Harvest entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO harvest ( harvestId,productionDate,amountOfLitres,qualityNotes,beehiveId,collectorId,harvestType,grade) VALUES (?,?,?,?,?,?,?,?)",entity.getHarvestId(),entity.getProductionDate(),entity.getAmountOfLiters(),entity.getQualityNotes(),entity.getBeehiveId(),entity.getCollectorId(),entity.getHarvestType(),entity.getGrade());
    }

    @Override
    public Harvest search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Harvest entity) throws SQLException, ClassNotFoundException {
        System.out.println("DAO EKE INNWA");
        return SQLUtil.execute("UPDATE harvest SET productionDate=?,  amountOfLitres=?, qualityNotes=?,  beehiveId=?, collectorId=?, harvestType=?, grade=? WHERE harvestId=?  ",entity.getProductionDate(),entity.getAmountOfLiters(),entity.getQualityNotes(),entity.getBeehiveId(),entity.getCollectorId(),entity.getHarvestType(),entity.getGrade(),entity.getHarvestId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM harvest WHERE harvestId=?");
    }

    @Override
    public ArrayList<Harvest> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Harvest> allHarvest = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT* FROM harvest");
        while (resultSet.next()){
           Harvest harvest = new Harvest(resultSet.getString("harvestId"),resultSet.getString("productionDate"),resultSet.getString("amountOfLitres"),resultSet.getString("qualityNotes"),resultSet.getString("beehiveId"),resultSet.getString("collectorId"),resultSet.getString("harvestType"),resultSet.getString("grade"));
           allHarvest.add(harvest);
        }
        return allHarvest;

    }

    @Override
    public ArrayList<String> getHarvestIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT harvestId FROM harvest");
        while (resultSet.next()) {
            String id = resultSet.getString("harvestId");
            all.add(id);
        }
        return all;
    }

}
