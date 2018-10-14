package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> getById(long id);

    Optional<User> getUserByTelegramUserId(Integer telegramUserId);

    Page<User> findAll(Pageable pageable);
}
