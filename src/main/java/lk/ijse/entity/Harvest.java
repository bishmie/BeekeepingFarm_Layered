package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Harvest {
    private String harvestId;
    private String productionDate;
    private String amountOfLiters;
    private String qualityNotes;
    private String beehiveId;
    private String collectorId;
    private String harvestType;
    private String grade;

    public Harvest(String id, String productionDate, String amount, String quality, String harvestType, String grade) {
    }
}
