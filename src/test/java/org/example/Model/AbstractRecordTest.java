package org.example.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AbstractRecordTest {

    private Person person;
    private Organization organization;
    @BeforeEach
    void setUp() {
        person = new Person("Juan", "Perez", "3214569870", "M", LocalDate.parse("1998-01-01"));
        organization = new Organization("pizza","av juarez 1","1234567890");
    }

    @Test
    void test_isValidNumber_givenValidPhoneNumber_returnsTrue() {
        assertTrue(person.isValidNumber("+1234567890"));
    }

    @Test
    void test_isValidNumber_givenInvalidPhoneNumber_returnsFalse() {
        assertFalse(person.isValidNumber("123 456 7890"));
        assertFalse(person.isValidNumber("abc1234567"));
    }

    @Test
    void test_setPhoneNumber_givenValidNumber_updatesPhoneNumber() {
        person.setPhoneNumber("+1234567890");
        assertEquals("+1234567890", person.getPhoneNumber());
    }

    @Test
    void test_setPhoneNumber_givenInvalidNumber_setsDefaultValue() {
        person.setPhoneNumber("123 456 7890");
        assertEquals("[no number]", person.getPhoneNumber());
    }

    @Test
    void test_getCreatedDate_whenPersonCreated_returnsNonNullDate() {
        assertNotNull(person.getCreatedDate());
    }

    @Test
    void test_setLastEditedDate_whenUpdated_changesDate() {
        LocalDate initialDate = person.getLastEditedDate();
        person.setLastEditedDate(LocalDate.now().plusDays(1));
        assertNotEquals(initialDate, person.getLastEditedDate());
    }
    @Test
    void test_isValidNumber_withValidValues_returnFalse() {

        assertFalse(organization.isValidNumber("123456asd"));
    }
@Test
    void test_isValidNumber_withValidValues_returnFalseByParentheses() {

        assertFalse(organization.isValidNumber("(123)(456)7890"));
    assertFalse(organization.isValidNumber("(123 456)7890"));
    assertFalse(organization.isValidNumber("+123 456 7890"));
    assertFalse(organization.isValidNumber("+123A567890"));
    assertTrue(organization.isValidNumber("+1234567890"));
    assertFalse(organization.isValidNumber("(123 456) 7890"));
    assertFalse(organization.isValidNumber("(123) 456) 7890"));
    }


}
