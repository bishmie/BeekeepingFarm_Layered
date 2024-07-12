package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BeekeeperBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BekeeperDAO;
import lk.ijse.entity.Beekeeper;
import lk.ijse.model.BeekeeperDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class BeekeeperBOImpl implements BeekeeperBO {

    BekeeperDAO bekeeperDAO = (BekeeperDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BEEKEEPER);


    @Override
    public ArrayList<String> getIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> allIds = bekeeperDAO.getBeekeeperIds();
        ArrayList<String> beekeperIds = new ArrayList<>();
        for(String i : allIds){
            beekeperIds.add(i);
        }
        return beekeperIds;


    }

    @Override
    public boolean saveBeekeeper(BeekeeperDTO beekeeperDTO) throws SQLException, ClassNotFoundException {
        return bekeeperDAO.save( new Beekeeper(beekeeperDTO.getBeekeeperId(),beekeeperDTO.getName(),beekeeperDTO.getAddress(),beekeeperDTO.getContact(),beekeeperDTO.getEmail(),beekeeperDTO.getSalary()));
    }

    @Override
    public boolean updateBeekeeper(BeekeeperDTO beekeeperDTO) throws SQLException, ClassNotFoundException {
        return bekeeperDAO.update(new Beekeeper(beekeeperDTO.getBeekeeperId(),beekeeperDTO.getName(),beekeeperDTO.getAddress(),beekeeperDTO.getContact(),beekeeperDTO.getEmail(),beekeeperDTO.getSalary()));
    }

    @Override
    public boolean deleteBeekeeper(String id) throws SQLException, ClassNotFoundException {
        return bekeeperDAO.delete(id);
    }

    @Override
    public BeekeeperDTO searchBeekeeper(String id) throws SQLException, ClassNotFoundException {

        Beekeeper beekeeper = bekeeperDAO.search(id);
        return new BeekeeperDTO(beekeeper.getBeekeeperId(),beekeeper.getName(),beekeeper.getAddress(),beekeeper.getContact(),beekeeper.getEmail(),beekeeper.getSalary());
    }
}
