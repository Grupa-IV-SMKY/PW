package pl.edu.pbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.model.Equipment;
import pl.edu.pbs.repository.EquipmentRepository;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {return equipmentRepository.findAll();}

    public void saveEquipment(Equipment equipment){equipmentRepository.save(equipment);}

    public void deleteEquipment(Equipment equipment){equipmentRepository.delete(equipment);}
}
