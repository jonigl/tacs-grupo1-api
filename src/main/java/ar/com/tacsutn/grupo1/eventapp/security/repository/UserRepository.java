package ar.com.tacsutn.grupo1.eventapp.security.repository;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
