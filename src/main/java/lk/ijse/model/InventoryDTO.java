package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryDTO {
    private String inventoryId;
    private String type;
    private String description;
    private String qty;
    private String unitPrice;
    private String beeHiveId;



}
