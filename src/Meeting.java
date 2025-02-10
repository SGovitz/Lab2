import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
    }
    String location;
    void complete(){
        complete = true;
    }
    boolean isComplete() {
        return complete;
    }
    LocalDateTime getEndTime(){
        return endDateTime;
    }
    Duration getDuration(){
        LocalDateTime duration;
        double endDateTime = getEndTime();
        duration = dateTime - endDateTime;
    }
    String getLocation(){
        return location;
    }
    void setEndTime(Date end){
        endDateTime = end;
    }
    void setLocation(String location){
        this.location = location;
    }


}
