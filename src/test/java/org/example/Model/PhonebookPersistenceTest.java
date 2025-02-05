package org.example.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void test_save_given_contacts_when_saved_then_file_is_created() {
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));

        persistence.save(contacts, tempFile);

        assertTrue(tempFile.exists());
    }

    @Test
    public void test_load_given_saved_contacts_when_loaded_then_contacts_are_loaded_successfully() {
        List<AbstractRecord> contacts = Arrays.asList(mock(AbstractRecord.class));
        persistence.save(contacts, tempFile);

        List<AbstractRecord> loadedContacts = persistence.load(tempFile);

        assertNotNull(loadedContacts);
    }

    @Test
    public void test_handles_nonexistent_directory() {

        List<AbstractRecord> contacts = Arrays.asList(new Person("John","cena","123456987","m",null));
        File file = new File("nonexistent/test.dat");

        persistence.save(contacts, file);

        assertFalse(file.exists());
    }

    @Test
    public void test_load_nonexistent_file() {

        File nonExistentFile = new File("nonexistent.dat");

        List<AbstractRecord> result = persistence.load(nonExistentFile);

        assertNull(result);
    }

}