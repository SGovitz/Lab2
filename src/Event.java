import java.time.LocalDateTime;

public class Event {
    Event(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }
    String name;
    LocalDateTime dateTime;
    String getName(){
        return name;
    }
    LocalDateTime getDatetime(){
        return dateTime;
    }
    void setDatetime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }
    void setName(String name){
        this.name = name;
    }
    int compareTo(Event e){
        return this.dateTime.compareTo(e.dateTime);
    }


}
