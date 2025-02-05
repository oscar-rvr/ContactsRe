package org.example.Model;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void givenPersonFields_whenInitialized_thenFieldsAreCorrectlySet() {
        String name = "John";
        String surname = "Doe";
        String phone = "1234567890";
        String gender = "M";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        Person person = new Person(name, surname, phone, gender, birthDate);

        assertEquals(name, person.getName());
        assertEquals(surname, person.getSurname());
        assertEquals(phone, person.getPhoneNumber());
        assertEquals(gender, person.getGender());
        assertEquals(birthDate, person.getBirthDate());
    }

    @Test
    public void givenPerson_whenGetFields_thenCorrectFieldsReturned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());
        List<String> expectedFields = List.of("name", "surname", "birth", "gender", "number");

        List<String> actualFields = person.getFields();

        assertEquals(expectedFields, actualFields);
    }

    @Test
    public void givenPerson_whenValidFieldSet_thenFieldIsUpdated() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());

        person.setField("name", "Jane");
        person.setField("surname", "Smith");
        person.setField("gender", "F");
        person.setField("birth","1998-01-01");
        person.setField("number", "1234567890");

        assertEquals("Jane", person.getName());
        assertEquals("Smith", person.getSurname());
        assertEquals("F", person.getGender());
    }

    @Test
    public void givenPerson_whenValidBirthDate_thenBirthDateIsCorrectlyFormatted() {
        LocalDate birthDate = LocalDate.of(1995, 12, 31);
        Person person = new Person("Jane", "Smith", "+1234567890", "", birthDate);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        person.printInfo();

        assertTrue(outContent.toString().contains("Birth date: 1995-12-31"));
        assertTrue(outContent.toString().contains("Gender: [no data]"));
    }

    @Test
    public void givenPerson_whenValidFieldGet_thenCorrectValueReturned() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Person person = new Person("John", "Doe", "1234567890", "M", birthDate);

        assertEquals("John", person.getField("name"));
        assertEquals("Doe", person.getField("surname"));
        assertEquals(birthDate.toString(), person.getField("birth"));
        assertEquals("M", person.getField("gender"));
        assertEquals("1234567890", person.getField("number"));
    }

    @Test
    public void givenPerson_whenPrintName_thenConcatenatedNameReturned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());

        String fullName = person.printName();

        assertEquals("John Doe", fullName);
    }

    @Test
    public void givenPerson_whenInvalidFieldSet_thenNoChange() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());
        String originalName = person.getName();

        person.setField("invalid_field", "new value");

        assertEquals(originalName, person.getName());
    }

    @Test
    public void givenPerson_whenNullBirthDate_thenNoDataReturned() {
        Person person = new Person("John", "Doe", "1234567890", "M", null);

        String birthDateField = person.getField("birth");

        assertEquals("[no data]", birthDateField);
    }

    @Test
    public void givenPerson_whenEmptyGender_thenNoDataReturned() {
        Person person = new Person("John", "Doe", "1234567890", "", LocalDate.now());

        String genderField = person.getField("gender");

        assertEquals("[no data]", genderField);
    }

    @Test
    public void givenPerson_whenInvalidFieldGet_thenNoDataReturned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());

        String invalidField = person.getField("invalid_field");

        assertEquals("[no data]", invalidField);
    }

    @Test
    public void givenPerson_whenNullBirthDate_thenDisplayNoData() {
        Person person = new Person("John", "Doe", "1234567890", "M", null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        person.printInfo();

        String output = outputStream.toString();
        assertTrue(output.contains("Birth date: [no data]"));
    }
}
