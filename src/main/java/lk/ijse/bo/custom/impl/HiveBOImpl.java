package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.HiveBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.HiveDAO;
import lk.ijse.entity.BeeHive;
import lk.ijse.model.BeeHiveDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class HiveBOImpl implements HiveBO {

    HiveDAO hiveDAO = (HiveDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HIVE);

    @Override
    public ArrayList<BeeHiveDTO> getAllHives() throws SQLException, ClassNotFoundException {
        ArrayList<BeeHiveDTO> getAll = new ArrayList<>();
        ArrayList<BeeHive> allHives= hiveDAO.getAll();
        for(BeeHive b: allHives){
            getAll.add(new BeeHiveDTO(b.getBeehiveId(),b.getType(),b.getLocation(),b.getPopulation(),b.getInspectionDate(),b.getInspectionResult()));
        }
        return getAll;
     }
}
