package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@ApiModel
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(example = "12345678")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(example = "sample user name")
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    private List<EventList> lists;

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.lists = new ArrayList<EventList>();
    }

    public User(Long id, String name, String password, Role role, List<EventList> lists) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.lists = lists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<EventList> getLists() { return lists; }

    public void setLists(List<EventList> lists) { this.lists = lists; }

    public void addList(EventList eventList) { this.lists.add(eventList); }

    public void deleteList(Long eventListId) {
        EventList eventList = findEventListById(eventListId);

        this.lists.remove(eventList);
    }

    public void changeListName(Long eventListId, String newName) {
        EventList eventList = findEventListById(eventListId);

        eventList.setName(newName);
    }

    private EventList findEventListById(Long eventListId) {
        return this.lists.stream().filter(l -> l.getId().equals(eventListId)).collect(Collectors.toList()).get(0);
    }
}
