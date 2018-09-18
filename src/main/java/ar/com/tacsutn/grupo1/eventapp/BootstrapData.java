package ar.com.tacsutn.grupo1.eventapp;

import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.repositories.AuthorityRepository;
import ar.com.tacsutn.grupo1.eventapp.repositories.EventRepository;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class BootstrapData implements InitializingBean {

    private final UserService userService;
    private final EventListService listService;
    private final EventRepository eventRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    private BootstrapData(
            UserService userService,
            EventListService listService,
            EventRepository eventRepository,
            AuthorityRepository authorityRepository) {

        this.userService = userService;
        this.listService = listService;
        this.eventRepository = eventRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthorityName.ROLE_ADMIN);
        authorityRepository.save(adminAuthority);

        List<Authority> adminAuthorities = Collections.singletonList(adminAuthority);

        Authority userAuthority = new Authority();
        userAuthority.setName(AuthorityName.ROLE_USER);
        authorityRepository.save(userAuthority);

        List<Authority> userAuthorities = Collections.singletonList(userAuthority);

        User admin = new User(
            "admin",
            "admin",
            "admin",
            "admin",
            "admin@admin.com",
            true,
            new Date(),
            adminAuthorities
        );

        User user = new User(
            "user",
            "user",
            "user",
            "user",
            "user@user.com",
            true,
            new Date(),
            userAuthorities
        );

        userService.create(admin);
        userService.create(user);

        EventList list1 = listService.save(admin, "list name1");
        EventList list2 = listService.save(admin, "list name2");

        listService.save(user, "list name1");
        listService.save(user, "list name2");

        eventRepository.save(new EventId("name", Arrays.asList(list1, list2)));
    }
}
