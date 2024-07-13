package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Inventory {
    private String inventoryId;
    private String type;
    private String description;
    private String qty;
    private String unitPrice;
    private String beeHiveId;



}
