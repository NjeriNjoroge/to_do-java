import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;

  public Task(String description) {
    this.description = description;
    completed = false;
    createdAt = LocalDateTime.now();
    //instances.add(this);
    //mId = instances.size();
  }

  public String getDescription(){
    return mDescription;
  }

  public boolean isCompleted() {
    return mCompleted;
  }

  public LocalDateTime getCreatedAt() {
    return mCreatedAt;
  }

  public static List<Task> all() {
   return instances;
 }

 public static void clear() {
   instances.clear();
 }

 public int getId() {
   return mId;
 }

 public static Task find(int id) {
  return instances.get(id - 1);
}

}
