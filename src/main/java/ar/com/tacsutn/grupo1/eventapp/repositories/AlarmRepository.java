package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlarmRepository extends CrudRepository<Alarm, Long> {

    Integer countAllByUserId(long user_id);

    List<Alarm> getAlarmByUserId(long user_id);

}
