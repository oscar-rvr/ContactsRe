package org.example.Model.Model2;
import org.example.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
    }

    @Test
    void givenPerson_whenGetFields_thenReturnCorrectFields() {
        assertEquals(5, person.getFields().size());
    }

    @Test
    void givenPerson_whenSetField_thenFieldUpdated() {
        person.setField("name", "Jane");
        assertEquals("Jane", person.getName());
    }

    @Test
    void givenPerson_whenGetField_thenReturnCorrectValue() {
        assertEquals("John", person.getField("name"));
    }

    @Test
    void givenPerson_whenPrintName_thenReturnFullName() {
        assertEquals("John Doe", person.printName());
    }

    @Test
    void givenPerson_whenSetPhoneNumber_thenValidateNumber() {
        person.setPhoneNumber("+9876543210");
        assertEquals("+9876543210", person.getPhoneNumber());
    }

    @Test
    void givenPerson_whenSetInvalidPhoneNumber_thenSetNoNumber() {
        person.setPhoneNumber("invalid");
        assertEquals("[no number]", person.getPhoneNumber());
    }

    @Test
    void givenPerson_whenSetBirthDate_thenUpdatedCorrectly() {
        LocalDate newDate = LocalDate.of(2000, 5, 20);
        person.setBirthDate(newDate);
        assertEquals(newDate, person.getBirthDate());
    }
}

