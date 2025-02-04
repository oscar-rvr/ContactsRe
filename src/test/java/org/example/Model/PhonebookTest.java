package org.example.Model;

import org.example.Model.AbstractRecord;
import org.example.Model.Person;
import org.example.Model.Phonebook;
import org.example.Model.PhonebookPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhonebookTest {
    private Phonebook phonebook;
    private PhonebookPersistence persistenceMock;
    private File fileMock;

    @BeforeEach
    void givenPhonebookWhenInitializedThenCreateInstance() throws NoSuchFieldException, IllegalAccessException {
        // Mock the necessary objects
        fileMock = mock(File.class);
        persistenceMock = mock(PhonebookPersistence.class);

        // Initialize Phonebook with filepath
        phonebook = new Phonebook("phonebook.db");

        // Use reflection to inject the persistenceMock into the Phonebook instance
        Field persistenceField = Phonebook.class.getDeclaredField("persistence");
        persistenceField.setAccessible(true);  // Allow access to private field
        persistenceField.set(phonebook, persistenceMock);
    }


    @Test
    void givenContactsWhenSearchThenReturnMatchingResults() {
        AbstractRecord contact = mock(AbstractRecord.class);
        when(contact.getField(anyString())).thenReturn("Test");
        when(contact.getFields()).thenReturn(List.of("name"));
        phonebook.addContact(contact);
        List<AbstractRecord> results = phonebook.searchContacts("Test");
        assertEquals(1, results.size());
    }



    @Test
    void givenInvalidGenderWhenVerifyThenReturnFalse() {
        assertFalse(phonebook.verifyGender("X"));
    }

    @Test
    void givenValidGenderWhenVerifyThenReturnTrue() {
        assertTrue(phonebook.verifyGender("M"));
    }

    @Test
    void givenInvalidBirthDateWhenVerifyThenReturnNull() {
        assertNull(phonebook.verifybirthDate("invalid-date"));
    }

    @Test
    void givenValidBirthDateWhenVerifyThenReturnDate() {
        assertNotNull(phonebook.verifybirthDate("2000-01-01"));
    }
}
