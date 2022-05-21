package pl.edu.pbs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pbs.service.ClientService;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentID;
    private String equipmentName;
    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    private LocalDateTime equipmentAdmissionDate;
    private String equipmentClientNotes;
    private Integer clientID;
    private LocalDateTime equipmentIssueDate;
    private boolean equipmentIsFixed;
    private int equipmentRepairCost;
    private String equipmentRepairNotes;

    public String getClientNameFromEquipment(ClientService service){
        return service.getClientById(this.clientID).map(Client::getClientName).orElse(null);
    }
}
