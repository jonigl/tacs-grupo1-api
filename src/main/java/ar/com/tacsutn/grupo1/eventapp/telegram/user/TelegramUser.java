package ar.com.tacsutn.grupo1.eventapp.telegram.user;

import ar.com.tacsutn.grupo1.eventapp.models.User;

import javax.persistence.*;

@Entity
public class TelegramUser {

    @Id
    @Column(nullable = false, unique = true)
    private Integer telegramUserId;

    @OneToOne(mappedBy = "telegramUser", optional = false)
    private User internalUser;

    public Integer getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(User internalUser) {
        this.internalUser = internalUser;
    }
}
