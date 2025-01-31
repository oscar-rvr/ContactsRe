import static org.mockito.Mockito.*;

import org.example.Model.AbstractRecord;
import org.example.Model.Phonebook;
import org.junit.jupiter.api.*;
import java.util.*;

public class PhonebookTest {

    private Phonebook phonebook;
    private AbstractRecord contact;

    @BeforeEach
    public void setup() {
        phonebook = new Phonebook(null);  // Usando el constructor sin archivo
        contact = mock(AbstractRecord.class);  // Usando Mockito para simular un contacto
    }

    @Test
    public void testAddContact() {
        phonebook.addContact(contact);
        Assertions.assertEquals(1, phonebook.count());
    }

    @Test
    public void testCountContacts() {
        phonebook.addContact(contact);
        Assertions.assertEquals(1, phonebook.count());
    }

    @Test
    public void testListContacts() {
        phonebook.addContact(contact);
        phonebook.listContacts();
        // Verifica si se imprime el nombre del contacto
        verify(contact, times(1)).printName();
    }

    @Test
    public void testDeleteContact() {
        phonebook.addContact(contact);
        phonebook.deleteContact(0);
        Assertions.assertEquals(0, phonebook.count());
    }

    @Test
    public void testSearchContacts() {
        phonebook.addContact(contact);
        when(contact.getFields()).thenReturn(Arrays.asList("name", "phoneNumber"));
        when(contact.getField("name")).thenReturn("John");

        List<AbstractRecord> results = phonebook.searchContacts("John");
        Assertions.assertEquals(1, results.size());
    }
}
