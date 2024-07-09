package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Supplier {
    private String supplierId;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String inventoryId;


}
