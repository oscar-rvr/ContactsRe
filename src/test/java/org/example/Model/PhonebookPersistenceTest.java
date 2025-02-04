package org.example.Model;

import org.example.Model.AbstractRecord;
import org.example.Model.PhonebookPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PhonebookPersistenceTest {

    private PhonebookPersistence persistence;
    private File tempFile;

    @BeforeEach
    void setup() throws IOException {
        // Use a temporary file for testing
        tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit(); // Ensure it is deleted after tests
        persistence = new PhonebookPersistence(tempFile);
    }

    @Test
    void givenContactsWhenSaveContactsThenFileIsCreated() {
        // Arrange
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));

        // Act
        persistence.save(contacts, tempFile);

        // Assert
        assertTrue(tempFile.exists()); // Check if the file is saved
    }

    @Test
    void givenSavedContactsWhenLoadContactsThenContactsAreLoadedSuccessfully() {
        // Arrange
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));
        persistence.save(contacts, tempFile);

        // Act
        List<AbstractRecord> loadedContacts = persistence.load(tempFile);

        // Assert
        assertNotNull(loadedContacts);
    }
}
