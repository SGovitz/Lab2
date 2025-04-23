import java.time.LocalDateTime;

public class MeetingFactory implements EventFactory {
    @Override
    public Event createEvent(String name,
                             LocalDateTime start,
                             LocalDateTime end,
                             String location) {
        return new Meeting(name, start, end, location);
    }
}
