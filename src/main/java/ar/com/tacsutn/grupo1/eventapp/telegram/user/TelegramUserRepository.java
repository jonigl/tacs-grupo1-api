package ar.com.tacsutn.grupo1.eventapp.telegram.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Integer> {

    Optional<TelegramUser> getByTelegramUserId(Integer telegramUserId);
    Optional<TelegramUser> getByInternalUser_Id(Long internalUserId);
}
