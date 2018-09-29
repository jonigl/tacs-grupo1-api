package ar.com.tacsutn.grupo1.eventapp.telegram.user;

import ar.com.tacsutn.grupo1.eventapp.models.User;

import javax.persistence.*;

@Entity
public class TelegramUser {

    @Id
    @Column(nullable = false, unique = true)
    private Integer telegramUser;

    @Column(nullable = false)
    private Long telegramChat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User internalUser;

    public Integer getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(Integer telegramUser) {
        this.telegramUser = telegramUser;
    }

    public Long getTelegramChat() {
        return telegramChat;
    }

    public void setTelegramChat(Long telegramChat) {
        this.telegramChat = telegramChat;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(User internalUser) {
        this.internalUser = internalUser;
    }
}
