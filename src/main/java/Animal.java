import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


//class
public class Animal{
  private int id;
  private String name;

  //Constructor
  public Animal (String name){
    this.name = name;
  }

  //gets name attributes
  public String getName(){
    return name;
  }
//overrides the equals method
  @Override
  public boolean equals(Object otherAnimal){
    if (!(otherAnimal instanceof Animal)){
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName());
    }
  }

  //returns all database instances of Animals created
  public static List<Animal> all(){
    String sql = "SELECT id, name FROM animals";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Animal.class);
    }
  }
  //gets animal id
  public int getId(){
    return id;
  }

//locating animal with their assigned id
  public static Animal find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM animals where id=:id";
      Animal animal = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Animal.class);
      return animal;
    }
  }


//saving new instance of animal to database
public void save() {
  try(Connection con = DB.sql2o.open()){
    String sql = "INSERT INTO animals (name) VALUES (:name)";
    this.id = (int) con.createQuery(sql, true)
    .addParameter("name", this.name)
    .executeUpdate()
    .getKey();
  }
}

//deleting animal objects
public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM animals WHERE id = :id";
    con.createQuery(sql)
    .addParameter("id", id)
    .executeUpdate();
  }
}

//updating animal details
public void update(String name) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE animals SET name = :name WHERE id = :id";
    con.createQuery(sql)
    .addParameter("name", name)
    .addParameter("id", id)
    .executeUpdate();
  }
}

}
