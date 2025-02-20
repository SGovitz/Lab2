import java.time.LocalDateTime;

//Creates an event with a given date and time
public abstract class Event implements Comparable<Event> {
    protected String name;
    protected LocalDateTime dateTime;

    public Event(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    // Each subclass decides how to return the name.
    public abstract String getName();

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Compare events based on their starting time.
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.dateTime);
    }
}
