package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TaskTM {
    private String taskId;
    private String taskName;
    private String Description;
    private String dueDate;
    private String beekeeperId;
}
