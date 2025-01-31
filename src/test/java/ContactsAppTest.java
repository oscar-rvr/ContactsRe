import static org.mockito.Mockito.*;

import org.example.Controller.ContactsApp;
import org.example.Model.Phonebook;
import org.junit.jupiter.api.*;
import java.util.Scanner;

public class ContactsAppTest {

    private ContactsApp app;
    private Phonebook phonebook;
    private Scanner scanner;

    @BeforeEach
    public void setup() {
        phonebook = mock(Phonebook.class);
        scanner = mock(Scanner.class);
        app = new ContactsApp(phonebook);
    }



    @Test
    public void testCountContacts() {
        when(phonebook.count()).thenReturn(5);

        app.countContacts();
        verify(phonebook, times(1)).count();
    }


}
