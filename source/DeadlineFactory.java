import java.time.LocalDateTime;

public class DeadlineFactory implements EventFactory {
    @Override
    public Event createEvent(String name,
                             LocalDateTime start,
                             LocalDateTime end,
                             String location) {
        return new Deadline(name, start);
    }
}
