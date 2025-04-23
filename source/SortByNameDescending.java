// SortByNameDescending.java
import java.util.List;
public class SortByNameDescending implements SortStrategy {
    public void sort(List<Event> events) {
        events.sort((a,b) -> b.getName().compareTo(a.getName()));
    }
}
