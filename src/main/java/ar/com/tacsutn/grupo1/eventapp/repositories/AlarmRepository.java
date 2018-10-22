package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmRepository extends MongoRepository<Alarm, String> {

    long countAllByUserId(String user_id);

    Page<Alarm> getAlarmsByUserId(String user_id, Pageable pageable);
}
