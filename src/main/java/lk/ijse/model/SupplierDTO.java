package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO {
    private String supplierId;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String inventoryId;

}
