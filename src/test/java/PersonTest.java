import org.example.Model.Person;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void setup() {
        person = new Person("John", "Doe", "123456789", "M", LocalDate.of(1990, 1, 1));
    }

    @Test
    public void testGetField() {
        Assertions.assertEquals("John", person.getField("name"));
        Assertions.assertEquals("Doe", person.getField("surname"));
        Assertions.assertEquals("123456789", person.getField("number"));
    }

    @Test
    public void testSetField() {
        person.setField("name", "Jane");
        Assertions.assertEquals("Jane", person.getField("name"));
    }

    @Test
    public void testPrintInfo() {
        person.printInfo();
        Assertions.assertTrue(true);
    }
}
