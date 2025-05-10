package org.example.Model;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void test_constructor_given_person_fields_when_initialized_then_fields_are_correctly_set() {
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
    public void test_get_fields_given_person_when_get_fields_then_correct_fields_returned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());
        List<String> expectedFields = List.of("name", "surname", "birth", "gender", "number");

        List<String> actualFields = person.getFields();

        assertEquals(expectedFields, actualFields);
    }

    @Test
    public void test_set_field_given_person_when_valid_field_then_field_is_updated() {
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
    public void test_print_info_with_valid_birth_date_format() {
        LocalDate birthDate = LocalDate.of(1995, 12, 31);
        Person person = new Person("Jane", "Smith", "+1234567890", "", birthDate);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        person.printInfo();

        assertTrue(outContent.toString().contains("Birth date: 1995-12-31"));
        assertTrue(outContent.toString().contains("Gender: [no data]"));

    }

    @Test
    public void test_get_field_given_person_when_valid_field_then_correct_value_returned() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Person person = new Person("John", "Doe", "1234567890", "M", birthDate);

        assertEquals("John", person.getField("name"));
        assertEquals("Doe", person.getField("surname"));
        assertEquals(birthDate.toString(), person.getField("birth"));
        assertEquals("M", person.getField("gender"));
        assertEquals("1234567890", person.getField("number"));
    }

    @Test
    public void test_print_name_given_person_when_print_name_then_concatenated_name_returned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());

        String fullName = person.printName();

        assertEquals("John Doe", fullName);
    }

    @Test
    public void test_set_field_given_person_when_invalid_field_then_no_change() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());
        String originalName = person.getName();

        person.setField("invalid_field", "new value");

        assertEquals(originalName, person.getName());
    }

    @Test
    public void test_get_field_given_person_when_null_birth_date_then_no_data_returned() {
        Person person = new Person("John", "Doe", "1234567890", "M", null);

        String birthDateField = person.getField("birth");

        assertEquals("[no data]", birthDateField);
    }

    @Test
    public void test_get_field_given_person_when_empty_gender_then_no_data_returned() {
        Person person = new Person("John", "Doe", "1234567890", "", LocalDate.now());

        String genderField = person.getField("gender");

        assertEquals("[no data]", genderField);
    }

    @Test
    public void test_get_field_given_person_when_invalid_field_then_no_data_returned() {
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.now());

        String invalidField = person.getField("invalid_field");

        assertEquals("[no data]", invalidField);
    }

    @Test
    public void test_print_info_given_person_when_null_birth_date_then_display_no_data() {
        Person person = new Person("John", "Doe", "1234567890", "M", null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        person.printInfo();

        String output = outputStream.toString();
        assertTrue(output.contains("Birth date: [no data]"));
    }
}