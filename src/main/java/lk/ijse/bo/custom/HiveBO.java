package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.BeeHiveDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HiveBO extends SuperBO {
    ArrayList<BeeHiveDTO> getAllHives() throws SQLException, ClassNotFoundException;

    boolean saveHives(BeeHiveDTO beeHiveDTO) throws SQLException, ClassNotFoundException;

    boolean updateHive(BeeHiveDTO beeHiveDTO) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    BeeHiveDTO searchHive(String id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getHiveIds() throws SQLException, ClassNotFoundException;
}
