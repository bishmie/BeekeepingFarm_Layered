package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CollectorBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CollectorDAO;
import lk.ijse.entity.Collector;
import lk.ijse.model.CollectorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CollectorBOImpl implements CollectorBO {

    CollectorDAO collectorDAO =(CollectorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COLLECTOR);
    @Override
    public ArrayList<CollectorDTO> getAllCollectrs() throws SQLException, ClassNotFoundException {
        ArrayList<CollectorDTO> getAll = new ArrayList<>();
        ArrayList <Collector> all= collectorDAO.getAll();
        for(Collector c :all ){
         getAll.add(new CollectorDTO(c.getCollectorId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail(),c.getSalary()));
        }
        return getAll;
    }

    @Override
    public boolean saveCollectors(CollectorDTO collectorDTO) throws SQLException, ClassNotFoundException {
        return collectorDAO.save(new Collector(collectorDTO.getCollectorId(),collectorDTO.getName(),collectorDTO.getAddress(),collectorDTO.getContact(),collectorDTO.getEmail(),collectorDTO.getSalary()));
    }

    @Override
    public boolean updateCollector(CollectorDTO collectorDTO) throws SQLException, ClassNotFoundException {
      return collectorDAO.update(new Collector(collectorDTO.getCollectorId(),collectorDTO.getName(),collectorDTO.getAddress(),collectorDTO.getContact(),collectorDTO.getEmail(),collectorDTO.getSalary()));
    }

    @Override
    public CollectorDTO searchCollector(String id) throws SQLException, ClassNotFoundException {

        Collector c = collectorDAO.search(id);
        return new CollectorDTO(c.getCollectorId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail(),c.getSalary());
    }

    @Override
    public boolean deleteCollector(String id) throws SQLException, ClassNotFoundException {
        return collectorDAO.delete(id);
    }

    @Override
    public ArrayList<String> getCollectorIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = new ArrayList<>();
        ArrayList<String> allIds = collectorDAO.getCollectorIds();
        for(String i : allIds){
            all.add(i);
        }
        return all;
    }
}
