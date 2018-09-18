package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AlarmService {

    private final AlarmRepository repository;

    @Autowired
    public AlarmService(AlarmRepository repository) {
        this.repository = repository;
    }

    public Optional<Alarm> get(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Alarm create(User user, String name, EventFilter filter, LocalDate activationDate) {
        Alarm alarm = new Alarm(user, name, filter, activationDate);
        return repository.save(alarm);
    }
}
