package pl.edu.pbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pbs.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
