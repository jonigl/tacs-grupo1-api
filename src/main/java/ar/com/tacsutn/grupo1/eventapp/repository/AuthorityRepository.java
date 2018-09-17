package ar.com.tacsutn.grupo1.eventapp.repository;

import ar.com.tacsutn.grupo1.eventapp.models.Authority;
import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    List<Authority> findByName(AuthorityName name);
}
