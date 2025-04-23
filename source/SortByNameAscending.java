// SortByNameAscending.java
import java.util.List;
public class SortByNameAscending implements SortStrategy {
    public void sort(List<Event> events) {
        events.sort((a,b) -> a.getName().compareTo(b.getName()));
    }
}
