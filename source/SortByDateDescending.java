// SortByDateDescending.java
import java.util.Collections;
import java.util.List;
public class SortByDateDescending implements SortStrategy {
    public void sort(List<Event> events) {
        Collections.sort(events, Collections.reverseOrder());
    }
}
