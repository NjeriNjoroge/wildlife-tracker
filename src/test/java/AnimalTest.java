import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class AnimalTest{

  //telling the tests to use only this dedicated database and clear after
  @Rule
  public DatabaseRule database = new DatabaseRule();

  //instance of animal
@Test
public void animal_createsInstanceOfAnimal_true(){
  Animal testAnimal = new Animal("Elephant");
  assertEquals(true, testAnimal instanceof Animal);
}

//return attributes of Animal class
@Test
public void name_returnsNameOfCreatedAnimal_string(){
  Animal testAnimal = new Animal("Elephant");
  assertEquals("Elephant", testAnimal.getName());
}

//returns all new instances of Animal
@Test
public void all_displaysAllInstancesOfAnimal_true(){
  Animal testAnimal = new Animal("Elephant");
  testAnimal.save();
  Animal firstAnimal = new Animal("Lion");
  firstAnimal.save();
  assertEquals(true, Animal.all().get(0).equals(testAnimal));
  assertEquals(true, Animal.all().get(1).equals(firstAnimal));
}

//accessing animal IDs
@Test
public void getId_animalIsCreatedWithId_1(){
  Animal testAnimal = new Animal("Elephant");
  testAnimal.save();
  assertTrue(testAnimal.getId() > 0);
}

//locating specific animals using their IDs
@Test
public void find_locatesAnimalWithId_testAnimal(){
  Animal testAnimal = new Animal("Elephant");
  testAnimal.save();
  Animal firstAnimal = new Animal("Lion");
  firstAnimal.save();
  assertEquals(Animal.find(firstAnimal.getId()), firstAnimal);
}

//comparing objects we retrieve from database by overriding equals()
@Test
public void equals_returnsTrueIfNamesAretheSame(){
  Animal testAnimal = new Animal("Lion");
  Animal firstAnimal = new Animal("Lion");
  assertTrue(testAnimal.equals(firstAnimal));
}

//saves objects into database
@Test
public void save_returnsTrueIfNamesAretheSame(){
  Animal firstAnimal = new Animal("Lion");
  firstAnimal.save();
  assertTrue(Animal.all().get(0).equals(firstAnimal));
}
//assigns unique ids when saved to DB
@Test
public void save_assignsIdToAnimalObject(){
  Animal testAnimal = new Animal("Lion");
  testAnimal.save();
  Animal savedAnimal = Animal.all().get(0);
  assertEquals(testAnimal.getId(), savedAnimal.getId());
}

@Test
public void save_savesIntoDatabase_true(){
  Animal testAnimal = new Animal("Lion");
  testAnimal.save();
  assertTrue(Animal.all().get(0).equals(testAnimal));
}

//deleting objects from DB
@Test
public void delete_deletesAnimal_true() {
  Animal testAnimal = new Animal("Lion");
  testAnimal.save();
  int myAnimalId = testAnimal.getId();
  testAnimal.delete();
  assertEquals(null, Animal.find(myAnimalId));
}

//updating animal details
@Test
public void update_updatesAnimalObjects_true(){
  Animal testAnimal = new Animal("Lion");
  testAnimal.save();
  testAnimal.update("Ostrich");
  assertEquals("Ostrich", Animal.find(testAnimal.getId()).getName());
}

}
