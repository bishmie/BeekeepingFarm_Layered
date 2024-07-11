package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.CollectorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CollectorBO extends SuperBO {
    ArrayList<CollectorDTO> getAllCollectrs() throws SQLException, ClassNotFoundException;

    boolean saveCollectors(CollectorDTO collectorDTO) throws SQLException, ClassNotFoundException;

    boolean updateCollector(CollectorDTO collectorDTO) throws SQLException, ClassNotFoundException;

    CollectorDTO searchCollector(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCollector(String id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getCollectorIds() throws SQLException, ClassNotFoundException;
}
