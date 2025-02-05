package org.example.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class PhonebookPersistenceTest {

    private PhonebookPersistence persistence;
    private File tempFile;

    @BeforeEach
    void setup() throws IOException {
        tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        persistence = new PhonebookPersistence(tempFile);
    }

    @Test
    public void givenContacts_whenSaved_thenFileIsCreated() {
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));

        persistence.save(contacts, tempFile);

        assertTrue(tempFile.exists());
    }

    @Test
    public void givenSavedContacts_whenLoaded_thenContactsAreLoadedSuccessfully() {
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));
        persistence.save(contacts, tempFile);

        List<AbstractRecord> loadedContacts = persistence.load(tempFile);

        assertNotNull(loadedContacts);
    }

    @Test
    public void givenNonExistentDirectory_whenSaved_thenFileIsNotCreated() {
        List<AbstractRecord> contacts = Arrays.asList(new Person("John", "Cena", "123456987", "m", null));
        File file = new File("nonexistent/test.dat");

        persistence.save(contacts, file);

        assertFalse(file.exists());
    }

    @Test
    public void givenNonExistentFile_whenLoaded_thenNullIsReturned() {
        File nonExistentFile = new File("nonexistent.dat");

        List<AbstractRecord> result = persistence.load(nonExistentFile);

        assertNull(result);
    }
}
