import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class TaskTest {

//connecting to the database
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", "gnjoroge", "1234");
  }


  @Test
 public void Task_instantiatesCorrectly_true() {
   Task myTask = new Task("Mow the lawn", 1);
   assertEquals(true, myTask instanceof Task);
 }

 @Test
 public void Task_instantiatesWithDescription_String() {
   Task myTask = new Task("Mow the lawn", 1);
   assertEquals("Mow the lawn", myTask.getDescription());
 }

@Test
public void isCompleted_isFalseAfterInstantiation_false() {
  Task myTask = new Task("Mow the lawn", 1);
  assertEquals(false, myTask.isCompleted());
}

@Test
public void getCreatedAt_instantiatesWithCurrentTime_today() {
  Task myTask = new Task("Mow the lawn", 1);
  assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
}


@Test
public void all_returnsAllInstancesOfTask_true() {
  Task firstTask = new Task("Mow the lawn", 1);
  firstTask.save();
  Task secondTask = new Task("Buy groceries", 1);
  secondTask.save();
  assertEquals(true, Task.all().get(0).equals(firstTask));
  assertEquals(true, Task.all().get(1).equals(secondTask));
}

@Test
public void clear_emptiesAllTasksFromArrayList_0() {
  Task myTask = new Task("Mow the lawn", 1);
  //Task.clear();
  assertEquals(Task.all().size(), 0);
}

//assign and access IDs
@Test
public void getId_tasksInstantiateWithAnID() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  assertTrue(myTask.getId() > 0);
}

//locating specific Tasks using their unique ID
@Test
public void find_returnsTaskWithSameId_secondTask() {
  Task firstTask = new Task("Mow the lawn", 1);
  firstTask.save();
  Task secondTask = new Task("Buy groceries", 1);
  secondTask.save();
  assertEquals(Task.find(secondTask.getId()), secondTask);
}

//can recognize similar objects
@Test
public void equals_returnsTrueIfDescriptionsAreTheSame(){
   Task firstTask = new Task("Mow the lawn", 1);
    Task secondTask = new Task("Mow the lawn", 1);
    assertTrue(firstTask.equals(secondTask));
}

//assigning unique IDs
@Test
public void save_assignsIdToObject() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  Task savedTask = Task.all().get(0);
  assertEquals(myTask.getId(), savedTask.getId());
}

// ensures we can save a categoryId into our tasks table, thereby associating a Task with its Category
@Test
public void save_savesCategoryIdIntoDB_true() {
  Category myCategory = new Category("Household chores");
  myCategory.save();
  Task myTask = new Task("Mow the lawn", myCategory.getId());
  myTask.save();
  Task savedTask = Task.find(myTask.getId());
  assertEquals(savedTask.getCategoryId(), myCategory.getId());
}

//updating a specific task in database
@Test
public void update_updatesTaskDescription_true() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  myTask.update("Take a nap");
  assertEquals("Take a nap", Task.find(myTask.getId()).getDescription());
}

//locating and deleting task from database
@Test
public void delete_deletesTask_true() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  int myTaskId = myTask.getId();
  myTask.delete();
  assertEquals(null, Task.find(myTaskId));
}


//clearing database i.e the tasks and categories created
  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

}
