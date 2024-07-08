package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlaceOrderDTO {
    private OrderDTO order;
    private List<OrderProductDTO> odList;
}
