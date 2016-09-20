import java.util.List;
import java.util.ArrayList;

public class Category {
  private String name;
  private int ID;

  public Category(String name) {
    this.name = name;
  }

  public static List<Category> all () {
    String sql = "SELECT id, name FROM categories";
    try(Connection cin = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory) {
    if(!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategpry = (Category) otherCategory;
      return this.getName().equals(newCategory.getName()) &&
             this.getID() == newCategory.getID();
// we use == because int is a primitive, and therefore does not have access to .equals()
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
//pass the argument true. This tells Sql2o to add the id, saved as the key, to Query
        .addParameter("name", this.name)
        .executeUpdate();
        .getKey();
//add getKey(). This is saved as an Object with a numerical value. We can use type casting to save this object as an int, which we then save to our instance variable with this.id = (int)

    }
  }

  public String getName() {
    return name;
  }

  public int getID() {
    return ID;
  }

  public List<Task> getTasks() {

  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories WHERE id=:id";
      Category category = con.createQuery(sql);
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return category;
    }
  }

  public List<Task> getTasks() {
    //note that it is not static because we will call it on individual Category instances in order to return that specific Category's corresponding Tasks
    try(Connection con DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where categoryId=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Task.class);
    }
  }

}
