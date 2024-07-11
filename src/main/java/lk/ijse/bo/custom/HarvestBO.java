package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.HarvestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HarvestBO extends SuperBO {
    ArrayList<String> getHarvestIds() throws SQLException, ClassNotFoundException;

    ArrayList<HarvestDTO> loadAll() throws SQLException, ClassNotFoundException;

    boolean deleteHarvest(String id) throws SQLException, ClassNotFoundException;

    boolean saveHarvest(HarvestDTO harvestDTO) throws SQLException, ClassNotFoundException;

    boolean updateHarvest(HarvestDTO harvestDTO) throws SQLException, ClassNotFoundException;
}
