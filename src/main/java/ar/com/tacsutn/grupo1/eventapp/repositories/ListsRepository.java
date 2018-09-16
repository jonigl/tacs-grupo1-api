package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.models.exceptions.NotListOwnerException;

import java.util.List;
import java.util.stream.Collectors;

public class ListsRepository {
    private List<EventList> lists;

    private long nextId;

    public ListsRepository(List<EventList> lists, long nextId) {
        this.lists = lists;
        this.nextId = nextId;
    }

    public List<EventList> getLists() {
        return lists;
    }

    public void setLists(List<EventList> lists) {
        this.lists = lists;
    }

    public long getNextId() {
        return nextId;
    }

    public void setNextId(long nextId) {
        this.nextId = nextId;
    }

    public void create(User user, String eventListName) {
        this.lists.add(new EventList(this.nextId(), eventListName, user));
    }

    public void delete(User user, long id) {
        EventList eventList = this.getById(id);

        if(this.isOwner(user, eventList)) {
            this.lists.remove(eventList);
        }
        else {
            throw new NotListOwnerException();
        }
    }

    public void changeName(User user, long id, String newName) {
        EventList eventList = this.getById(id);

        if(this.isOwner(user, eventList)) {
            eventList.setName(newName);
        }
        else {
            throw new NotListOwnerException();
        }
    }

    public EventList getById(long id) {
        return this.lists.stream().filter(l -> l.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    public String getListName(long id) {
        return this.getById(id).getName();
    }

    public int getListsCount() {
        return this.lists.size();
    }

    private long nextId() {
        return nextId++;
    }

    private boolean isOwner(User user, EventList eventList) {
        return eventList.getUser().equals(user);
    }
}
