package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.Authority;
import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorityRepository extends MongoRepository<Authority, Long> {
    List<Authority> findByName(AuthorityName name);
    Authority findFirstByName(AuthorityName name);
}
