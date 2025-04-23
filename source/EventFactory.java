import java.time.LocalDateTime;
public interface EventFactory {
    Event createEvent(String name,
                      LocalDateTime start,
                      LocalDateTime end,
                      String location);
}
