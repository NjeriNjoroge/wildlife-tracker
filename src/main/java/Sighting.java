import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


//class
public class Sighting{
  private int id;
  private String location;
  private String rangerName;

  //Constructor
  public Sighting (String rangerName, String location){
    this.rangerName = rangerName;
    this.location = location;
  }

  //gets name attributes
  public String getName(){
    return rangerName;
  }
//overrides the equals method
  @Override
  public boolean equals(Object otherSighting){
    if (!(otherSighting instanceof Sighting)){
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getName().equals(newSighting.getName());
    }
  }

  //returns all database instances of sightings created
  public static List<Sighting> all(){
    String sql = "SELECT id, rangerName, location FROM sightings";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }
  //gets sighting id
  public int getId(){
    return id;
  }

//locating sighting with their assigned id
  public static Sighting find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM sightings where id=:id";
      Sighting sighting = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Sighting.class);
      return sighting;
    }
  }


//saving new instance of sighting to database
public void save() {
  try(Connection con = DB.sql2o.open()){
    String sql = "INSERT INTO sightings (rangerName, location) VALUES (:rangerName, :location)";
    this.id = (int) con.createQuery(sql, true)
    .addParameter("rangerName", this.rangerName)
    .addParameter("location", this.rangerName)
    .executeUpdate()
    .getKey();
  }
}

//deleting sighting objects
public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM sightings WHERE id = :id";
    con.createQuery(sql)
    .addParameter("id", id)
    .executeUpdate();
  }
}

//updating sighting details
public void update(String rangerName, String location) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE sightings SET rangerName = :rangerName, location = :location WHERE id = :id";
    con.createQuery(sql)
    .addParameter("rangerName", rangerName)
    .addParameter("location", location)
    .addParameter("id", id)
    .executeUpdate();
  }
}

}
