package lk.ijse.bo.custom.impl;

import com.beust.ah.A;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.HarvestBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.HarvestDAO;
import lk.ijse.entity.Harvest;
import lk.ijse.model.HarvestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class HarvestBOImpl implements HarvestBO {
    HarvestDAO harvestDAO = (HarvestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HARVEST);

    @Override
    public ArrayList<String> getHarvestIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> allIds = harvestDAO.getHarvestIds();
        ArrayList<String> harvestIds = new ArrayList<>();
        for(String i : allIds){
            harvestIds.add(i);
        }
        return harvestIds;
    }

    @Override
    public ArrayList<HarvestDTO> loadAll() throws SQLException, ClassNotFoundException {
        ArrayList<HarvestDTO> all = new ArrayList<>();
        ArrayList<Harvest> allHarvest = harvestDAO.getAll();
        for(Harvest j : allHarvest){
            all.add(new HarvestDTO(j.getHarvestId(),j.getProductionDate(),j.getAmountOfLiters(),j.getQualityNotes(),j.getBeehiveId(),j.getCollectorId(),j.getHarvestType(),j.getGrade()));
        }
        return all;
    }

    @Override
    public boolean deleteHarvest(String id) throws SQLException, ClassNotFoundException {
        return harvestDAO.delete(id);
    }

    @Override
    public boolean saveHarvest(HarvestDTO harvestDTO) throws SQLException, ClassNotFoundException {
       return harvestDAO.save(new Harvest(harvestDTO.getHarvestId(),harvestDTO.getProductionDate(),harvestDTO.getAmountOfLiters(),harvestDTO.getQualityNotes(),harvestDTO.getBeehiveId(),harvestDTO.getCollectorId(),harvestDTO.getHarvestType(),harvestDTO.getGrade()));
    }

    @Override
    public boolean updateHarvest(HarvestDTO harvestDTO) throws SQLException, ClassNotFoundException {
        System.out.println("bo eke");
        return harvestDAO.update(new Harvest(harvestDTO.getHarvestId(),harvestDTO.getProductionDate(),harvestDTO.getAmountOfLiters(),harvestDTO.getQualityNotes(),harvestDTO.getBeehiveId(),harvestDTO.getCollectorId(),harvestDTO.getHarvestType(),harvestDTO.getGrade()));
    }

    @Override
    public HarvestDTO searchHarvest(String id) throws SQLException, ClassNotFoundException {
        Harvest h = harvestDAO.search(id);
        return new HarvestDTO(h.getHarvestId(),h.getProductionDate(),h.getAmountOfLiters(),h.getQualityNotes(),h.getBeehiveId(),h.getCollectorId(),h.getHarvestType(),h.getGrade());
    }
}
