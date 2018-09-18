package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public Optional<Alarm> get(Long id) {
        return alarmRepository.findById(id);
    }

    @Transactional
    public Alarm save(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public Optional<Alarm> getById(long id){
        return alarmRepository.findById(id);
    }


    public Optional<Alarm> getById(User user, Long id) {
        return getById(id).filter(alarm -> alarm.getUser().equals(user));
    }

    public void remove(Alarm alarm) {
        alarmRepository.delete(alarm);
    }
}
