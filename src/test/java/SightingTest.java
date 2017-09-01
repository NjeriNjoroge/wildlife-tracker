import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class SightingTest{

  //telling the tests to use only this dedicated database and clear after
  @Rule
  public DatabaseRule database = new DatabaseRule();

  //instance of Sighting
@Test
public void Sighting_createsInstanceOfSighting_true(){
  Sighting testSighting = new Sighting("Ned Stark", "North-east of River");
  assertEquals(true, testSighting instanceof Sighting);
}

//return attributes of Sighting class
@Test
public void name_returnsNameOfCreatedSighting_string(){
  Sighting testSighting = new Sighting("Ned Stark", "North-east of River");
  assertEquals("Ned Stark", testSighting.getName());
}

//returns all new instances of Sighting
@Test
public void all_displaysAllInstancesOfSighting_true(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  Sighting firstSighting = new Sighting("Brandon Stark", "South-east of mountain");
  firstSighting.save();
  assertEquals(true, Sighting.all().get(0).equals(testSighting));
  assertEquals(true, Sighting.all().get(1).equals(firstSighting));
}

//accessing Sighting IDs
@Test
public void getId_SightingIsCreatedWithId_1(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  assertTrue(testSighting.getId() > 0);
}

//locating specific Sightings using their IDs
@Test
public void find_locatesSightingWithId_testSighting(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  Sighting firstSighting = new Sighting("Arya Stark", "West of Iron islands");
  firstSighting.save();
  assertEquals(Sighting.find(firstSighting.getId()), firstSighting);
}

//comparing objects we retrieve from database by overriding equals()
@Test
public void equals_returnsTrueIfNamesAretheSame(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  Sighting firstSighting = new Sighting("Jon Snow", "West of riverbank");
  assertTrue(testSighting.equals(firstSighting));
}

//saves objects into database
@Test
public void save_returnsTrueIfNamesAretheSame(){
  Sighting firstSighting = new Sighting("Jon Snow", "West of riverbank");
  firstSighting.save();
  assertTrue(Sighting.all().get(0).equals(firstSighting));
}
//assigns unique ids when saved to DB
@Test
public void save_assignsIdToSightingObject(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  Sighting savedSighting = Sighting.all().get(0);
  assertEquals(testSighting.getId(), savedSighting.getId());
}

@Test
public void save_savesIntoDatabase_true(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  assertTrue(Sighting.all().get(0).equals(testSighting));
}

//deleting objects from DB
@Test
public void delete_deletesSighting_true() {
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  int mySightingId = testSighting.getId();
  testSighting.delete();
  assertEquals(null, Sighting.find(mySightingId));
}

//updating Sighting details
@Test
public void update_updatesSightingObjects_true(){
  Sighting testSighting = new Sighting("Jon Snow", "West of riverbank");
  testSighting.save();
  testSighting.update("Jaime", "East of riverbank");
  assertEquals("Jaime", Sighting.find(testSighting.getId()).getName());
}

}
