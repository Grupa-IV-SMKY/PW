package pl.edu.pbs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentID;
    private String equipmentName;
    private LocalDateTime equipmentAdmissionDate;
    private String equipmentClientNotes;
    private Integer clientID;
    private LocalDateTime equipmentIssueDate;
    private boolean equipmentIsFixed;
    private int equipmentRepairCost;
    private String equipmentRepairNotes;
}
