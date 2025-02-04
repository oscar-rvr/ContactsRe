package org.example.Model.Model2;
import org.example.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AbstractRecordTest {

    private Person person;

    @BeforeEach
    void setUp() {
        LocalDate birth = LocalDate.parse("1998-01-01");
        person = new Person("Juan", "Perez", "3214569870","M", birth);
    }

    @Test
    void testPhoneNumberValidationValid() {
        assertTrue(person.isValidNumber("+1234567890"));
    }

    @Test
    void testPhoneNumberValidationInvalid() {
        assertFalse(person.isValidNumber("123 456 7890"));
        assertFalse(person.isValidNumber("abc1234567"));
    }

    @Test
    void testSetPhoneNumberValid() {
        person.setPhoneNumber("+1234567890");
        assertEquals("+1234567890", person.getPhoneNumber());
    }

    @Test
    void testSetPhoneNumberInvalid() {
        person.setPhoneNumber("123 456 7890");
        assertEquals("[no number]", person.getPhoneNumber());
    }

    @Test
    void testCreatedDate() {
        assertNotNull(person.getCreatedDate());
    }

    @Test
    void testLastEditedDate() {
        LocalDate initialDate = person.getLastEditedDate();
        person.setLastEditedDate(LocalDate.now().plusDays(1));
        assertNotEquals(initialDate, person.getLastEditedDate());
    }
}
