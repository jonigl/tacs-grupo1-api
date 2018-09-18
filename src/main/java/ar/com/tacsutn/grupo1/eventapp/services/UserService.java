package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.Authority;
import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.AuthorityRepository;
import ar.com.tacsutn.grupo1.eventapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            AuthorityRepository authorityRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(User user) {
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        user.setAuthorities(authorityRepository.findByName(AuthorityName.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User createAdmin(User user) {
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        List<Authority> lists = Arrays.asList(authorityRepository.findFirstByName(AuthorityName.ROLE_USER), authorityRepository.findFirstByName(AuthorityName.ROLE_ADMIN));
        user.setAuthorities(lists);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(long id) {
        return userRepository.getById(id);
    }
}
