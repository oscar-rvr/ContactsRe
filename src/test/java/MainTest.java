import static org.mockito.Mockito.*;


import org.example.Controller.ContactsController;
import org.example.Model.Phonebook;
import org.junit.jupiter.api.*;

import java.util.Scanner;

public class MainTest {

    private ContactsController app;
    private Phonebook phonebook;
    private Scanner scanner;

    @BeforeEach
    public void setup() {
        phonebook = mock(Phonebook.class);
        scanner = mock(Scanner.class);
        //app = new (phonebook);
    }




    @Test
    public void testCountAction() {
        when(phonebook.count()).thenReturn(5); // Simulando que hay 5 contactos
        //app.countContacts();

        // Verifica que se haya llamado a count
        verify(phonebook, times(1)).count();
    }


}
