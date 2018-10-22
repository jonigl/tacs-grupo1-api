package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public Alarm save(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public Optional<Alarm> getById(String id){
        return alarmRepository.findById(id);
    }


    public Optional<Alarm> getById(User user, String id) {
        return getById(id).filter(alarm -> alarm.getUser().equals(user));
    }

    public void remove(Alarm alarm) {
        alarmRepository.delete(alarm);
    }

    public long getTotalAlarmsByUserId(String userId) {
        return alarmRepository.countAllByUserId(userId);
    }

    public Page<Alarm> getAllAlarmsByUserId(String userId, Pageable pageable){
        return alarmRepository.getAlarmsByUserId(userId, pageable);
    }
}
