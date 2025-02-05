package org.example;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void test_default_database_file_used_when_no_args() {
        // Given
        String[] args = new String[0];

        // When
        Main.main(args);

        // Then
        File defaultDb = new File("phonebook.db");
        assertTrue(defaultDb.exists());
    }
}