package pl.edu.pbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.model.Equipment;
import pl.edu.pbs.repository.ClientRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {return clientRepository.findAll();}

    public void saveClient(Client client){clientRepository.save(client);}

    public void deleteClient(Client client){clientRepository.delete(client);}

    public Optional<Client> getClientById(Integer id) {return clientRepository.findById(id);}

    public List<Client> getClientByName(String clientName) {return clientRepository.findByClientNameContainsIgnoreCase(clientName);}
}
