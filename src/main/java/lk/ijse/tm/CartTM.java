package lk.ijse.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CartTM {
    private String productId;
    private String productName;
    private int qty;
    private double sellingPrice;
    private double total;
    private JFXButton btnRemove;
}
