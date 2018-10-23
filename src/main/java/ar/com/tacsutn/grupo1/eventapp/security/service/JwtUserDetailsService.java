package ar.com.tacsutn.grupo1.eventapp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.security.JwtUserFactory;
import ar.com.tacsutn.grupo1.eventapp.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(JwtUserFactory::create)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
    }
}
