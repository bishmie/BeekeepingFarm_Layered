package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.BeekeeperDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BeekeeperBO extends SuperBO {

    ArrayList<String> getIds() throws SQLException, ClassNotFoundException;

    boolean saveBeekeeper(BeekeeperDTO beekeeperDTO) throws SQLException, ClassNotFoundException;

    boolean updateBeekeeper(BeekeeperDTO beekeeperDTO) throws SQLException, ClassNotFoundException;

    boolean deleteBeekeeper(String id) throws SQLException, ClassNotFoundException;

    BeekeeperDTO searchBeekeeper(String id) throws SQLException, ClassNotFoundException;
}
