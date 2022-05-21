package pl.edu.pbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pbs.model.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findClientsByClientNameContains(String clientName);
    List<Client> findByClientNameContainsIgnoreCase(String clientName);
}
