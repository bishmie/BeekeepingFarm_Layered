package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderProductDTO {

    private String orderId;
    private String productId;
    private int qty;
    private double sellingPrice;

}
