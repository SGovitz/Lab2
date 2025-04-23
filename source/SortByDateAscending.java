// SortByDateAscending.java
import java.util.Collections;
import java.util.List;
public class SortByDateAscending implements SortStrategy {
    public void sort(List<Event> events) {
        Collections.sort(events);
    }
}
