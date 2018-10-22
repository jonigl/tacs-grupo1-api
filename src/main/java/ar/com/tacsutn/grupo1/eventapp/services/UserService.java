package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.models.UserRequest;
import ar.com.tacsutn.grupo1.eventapp.repositories.AuthorityRepository;
import ar.com.tacsutn.grupo1.eventapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public User update(User user, UserRequest userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());

        Optional.ofNullable(userRequest.getPassword())
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(userRequest.getTelegramUserId())
                .ifPresent(user::setTelegramUserId);

        return userRepository.save(user);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createAdmin(User user) {
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        //List<Authority> lists = Arrays.asList(authorityRepository.findFirstByName(AuthorityName.ROLE_USER), authorityRepository.findFirstByName(AuthorityName.ROLE_ADMIN));
        //user.setAuthorities(lists);
        user.setAuthorities(authorityRepository.findByName(AuthorityName.ROLE_ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(String id) {
        return userRepository.getById(id);
    }

    public Optional<User> getByTelegramUserId(int telegramUserId) {
        return userRepository.getUserByTelegramUserId(telegramUserId);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
