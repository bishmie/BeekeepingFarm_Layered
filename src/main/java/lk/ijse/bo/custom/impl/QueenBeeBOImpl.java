package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.QueenBeeBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.QueenBeeDAO;
import lk.ijse.entity.BeeQueen;
import lk.ijse.model.BeeQueenDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueenBeeBOImpl implements QueenBeeBO {

    QueenBeeDAO queenBeeDAO = (QueenBeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUEENBEE);

    @Override
    public boolean saveQueenBee(BeeQueenDTO beeQueenDTO) throws SQLException, ClassNotFoundException {
        return queenBeeDAO.save(new BeeQueen(beeQueenDTO.getQueenId(),beeQueenDTO.getBreedingHistory(),beeQueenDTO.getBodyFeatures(),beeQueenDTO.getHealthStatus(),beeQueenDTO.getIntroducedDate(),beeQueenDTO.getBeehiveId(),beeQueenDTO.getVariety()));
    }

    @Override
    public boolean updateQueen(BeeQueenDTO beeQueenDTO) throws SQLException, ClassNotFoundException {
        return queenBeeDAO.update(new BeeQueen(beeQueenDTO.getQueenId(),beeQueenDTO.getBreedingHistory(),beeQueenDTO.getBodyFeatures(),beeQueenDTO.getHealthStatus(),beeQueenDTO.getIntroducedDate(),beeQueenDTO.getBeehiveId(),beeQueenDTO.getVariety()));
    }

    @Override
    public boolean deleteQueen(String id) throws SQLException, ClassNotFoundException {
        return queenBeeDAO.delete(id);
    }

    @Override
    public BeeQueenDTO searchQueen(String id) throws SQLException, ClassNotFoundException {
        BeeQueen b = queenBeeDAO.search(id);
        return new BeeQueenDTO(b.getQueenId(),b.getBreedingHistory(),b.getBodyFeatures(),b.getHealthStatus(),b.getIntroducedDate(),b.getBeehiveId(),b.getVariety());

    }

    @Override
    public ArrayList<BeeQueenDTO> getAllQueens() throws SQLException, ClassNotFoundException {
        ArrayList<BeeQueenDTO> allQueen = new ArrayList<>();
        ArrayList<BeeQueen> all = queenBeeDAO.getAll();
        for(BeeQueen b : all){
            allQueen.add(new BeeQueenDTO(b.getQueenId(),b.getBreedingHistory(),b.getBodyFeatures(),b.getHealthStatus(),b.getIntroducedDate(),b.getBeehiveId(),b.getVariety()));
        }
        return allQueen;
    }
}
