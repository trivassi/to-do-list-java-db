import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;


public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int ID;
  private int categoryId;

  public Task(String description) {
    this.description = description;
    completed = false;
    createdAt = LocalDateTime.now();
    this.categoryId = categoryId;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId FROM tasks";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
      //create a query by calling createQuery() on this connection, passing in our sql statement
      //We chain the executeAndFetch() method on that, passing Task.class as an argument. This executes the SQL command and instructs Java to transform the information we receive into Task objects. This will create a List<Task> object, which we return
    }
  }

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getID() == newTask.getID() &&
             this.getCategoryId() == newTask.getCategoryId();
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        //establish a connection with our database
        String sql = "INSERT INTO tasks (description) VALUES (:description)";
        //construct an SQL statement that uses the placeholder :description
        //This protects against SQL injection, which is when users attempt to sneak malicious SQL statements
        this.id = (int) con.createQuery(sql, true)
        // true argument = is an option that instructs Sql2o to add the id from the database, saved as a key, to the Query object we are creating.
        .addParameter("description", this.description)
        //replace the :description placeholder with this.description
        .executeUpdate()
        //run the query
        .getKey();
        //  returns an Object with a numerical value. We typecast this object into an int at the very beginning of this chain of methods

        //return value of this chain of methods is int representing the object's ID in our database. By including this.id = before the entire chain of methods, we set this Tasks id property to the return value of this logic
      }
    }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public int getID() {
    return ID;
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      //construct a SQL query stating that we'd like to return the entries from the tasks table whose id property matches the id property we provide
      Task task = con.createQuery(sql)
      .addParameter("id", id)
      // to pass in the id argument to the SQL query
      .executeAndFetchFirst(Task.class);
      //return the first item in the collection returned by our database, cast as a Task object
    return task;
    //returns the Task whose id matches the id we provided as an argument to our find() method
    }
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, categoryId) VALUES (:description, :categoryId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("categoryId", this.categoryId)
        .executeUpdate()
        .getKey();
    }
  }

}
