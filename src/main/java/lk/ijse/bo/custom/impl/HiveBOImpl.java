package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.HiveBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
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

    @Override
    public boolean saveHives(BeeHiveDTO beeHiveDTO) throws SQLException, ClassNotFoundException {
        System.out.println("methanata waren");

        return hiveDAO.save( new BeeHive(beeHiveDTO.getBeehiveId(),beeHiveDTO.getType(),beeHiveDTO.getLocation(),beeHiveDTO.getPopulation(),beeHiveDTO.getInspectionDate(),beeHiveDTO.getInspectionResult()));
    }

    @Override
    public boolean updateHive(BeeHiveDTO beeHiveDTO) throws SQLException, ClassNotFoundException {
        return hiveDAO.update(new BeeHive(beeHiveDTO.getBeehiveId(),beeHiveDTO.getType(),beeHiveDTO.getLocation(),beeHiveDTO.getPopulation(),beeHiveDTO.getInspectionDate(),beeHiveDTO.getInspectionResult()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return hiveDAO.delete(id);
    }

    @Override
    public BeeHiveDTO searchHive(String id) throws SQLException, ClassNotFoundException {
        BeeHive beeHive = hiveDAO.search(id);
        return new BeeHiveDTO(beeHive.getBeehiveId(),beeHive.getType(),beeHive.getLocation(),beeHive.getPopulation(),beeHive.getInspectionDate(),beeHive.getInspectionResult());
    }

    @Override
    public ArrayList<String> getHiveIds() throws SQLException, ClassNotFoundException {
        ArrayList<String > all = new ArrayList<>();
        ArrayList<String> allIds = hiveDAO.getIds();
        for(String i : allIds){
            all.add(i);
        }
        return all;
    }
}
