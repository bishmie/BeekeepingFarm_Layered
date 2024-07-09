package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.QueenBeeDAO;
import lk.ijse.entity.BeeQueen;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueenBeeDAOImpl implements QueenBeeDAO {
    @Override
    public boolean save(BeeQueen entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO beequeen(queenId,breedingHistory,bodyFeatures,healthStatus,introducedDate,beehiveId,variety) VALUES (?,?,?,?,?,?,?)",entity.getQueenId(),entity.getBreedingHistory(),entity.getBodyFeatures(),entity.getHealthStatus(),entity.getIntroducedDate(),entity.getBeehiveId(),entity.getVariety());
    }

    @Override
    public BeeQueen search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM beequeen WHERE queenId =?", id);
        if (resultSet.next()) {
            String breedingHistory = resultSet.getString(2);
            String bodyFeatures = resultSet.getString(3);
            String healthStatus = resultSet.getString(4);
            String introducedDate = resultSet.getString(5);
            String beehiveId =resultSet.getString(6);
            String variety = resultSet.getString(7);
            return new BeeQueen(resultSet.getString(1), breedingHistory, bodyFeatures, healthStatus, introducedDate,beehiveId,variety);

        }
        return null;
    }

    @Override
    public boolean update(BeeQueen entity) throws SQLException, ClassNotFoundException {
         return SQLUtil.execute("UPDATE beequeen SET breedingHistory =?, bodyFeatures =?, healthStatus =?, introducedDate =?, beehiveId =?, variety =? WHERE queenId =?",entity.getBreedingHistory(),entity.getBodyFeatures(),entity.getHealthStatus(),entity.getIntroducedDate(),entity.getBeehiveId(),entity.getVariety(),entity.getQueenId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM beequeen WHERE queenId =?",id);
    }

    @Override
    public ArrayList<BeeQueen> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<BeeQueen> allQueen = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM beequeen");
        while (rst.next()) {
            BeeQueen beeQueen  = new BeeQueen(rst.getString("queenId"), rst.getString("breedingHistory"), rst.getString("bodyFeatures"),rst.getString("healthStatus"), rst.getString("introducedDate"),rst.getString("beehiveId"),rst.getString("variety"));
            allQueen.add(beeQueen);
        }
        return allQueen;
    }
}
