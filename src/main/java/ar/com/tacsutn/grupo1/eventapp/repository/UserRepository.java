package ar.com.tacsutn.grupo1.eventapp.repository;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
