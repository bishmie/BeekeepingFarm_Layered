package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.BeeHiveDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HiveBO extends SuperBO {
    ArrayList<BeeHiveDTO> getAllHives() throws SQLException, ClassNotFoundException;
}
