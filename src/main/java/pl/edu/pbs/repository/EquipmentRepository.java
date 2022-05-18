package pl.edu.pbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pbs.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
