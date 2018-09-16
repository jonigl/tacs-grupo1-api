package ar.com.tacsutn.grupo1.eventapp.models;

public class EqualNameCriteria implements Criteria {
    private String nameToEqual;

    public EqualNameCriteria(String nameToEqual) {
        this.nameToEqual = nameToEqual;
    }

    @Override
    public boolean satisfies(Event event) {
        return event.getName().equals(nameToEqual);
    }
}
