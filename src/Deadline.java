import java.time.LocalDate;

public class Deadline extends Event implements Completable{
    Deadline(String name, LocalDate deadline){
        this.name = name;
        deadline = LocalDate.now();
    }
    boolean complete = false;
    void complete() {
        complete = true;
    }
    boolean isComplete() {
        return complete;
    }

}
