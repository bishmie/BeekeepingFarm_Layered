package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class BeekeeperDTO {
    private String beekeeperId;
    private String Name;
    private String Address;
    private String contact;
    private String email;
    private String salary;

}
