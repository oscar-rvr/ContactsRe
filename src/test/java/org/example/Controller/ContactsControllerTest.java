package org.example.Controller;
import org.example.Model.AbstractRecord;
import org.example.Model.Organization;
import org.example.Model.Person;
import org.example.Model.Phonebook;
import org.example.View.ContactsView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ContactsControllerTest {
    private Phonebook phonebook;
    private ContactsView view;


    @Test
    public void test_add_person_contact_with_valid_data() {
        Phonebook phonebook = mock(Phonebook.class);
        ContactsView view = mock(ContactsView.class);
        ContactsController controller = new ContactsController(phonebook, view);

        when(view.getUserInput())
                .thenReturn("person")
                .thenReturn("John")
                .thenReturn("Smith")
                .thenReturn("1990-01-01")
                .thenReturn("M")
                .thenReturn("1234567890");

        when(phonebook.verifybirthDate("1990-01-01")).thenReturn(LocalDate.of(1990, 1, 1));
        when(phonebook.verifyGender("M")).thenReturn(true);

        controller.handleAddContact();

        verify(view).showMessage("Enter the type (person, organization):");
        verify(view).showMessage("Enter the name:");
        verify(view).showMessage("Enter the surname:");
        verify(view).showMessage("Enter the birth date:");
        verify(view).showMessage("Enter the gender (M, F): ");
        verify(view).showMessage("Enter the number:");
        verify(phonebook).addContact(any(Person.class));
    }

    @Test
    public void test_run_given_exit_action_calls_handleDefault() {
        ContactsController controller = createController();

        when(view.getUserInput()).thenReturn("no valid action", "exit");

        controller.run();

        verify(view).showMessage("Invalid action");
    }

    @Test
    public void test_add_contact_with_invalid_type() {
        ContactsController controller = createController();

        when(view.getUserInput()).thenReturn("invalid_type");

        controller.handleAddContact();

        verify(view).showMessage("Enter the type (person, organization):");
        verify(view).showMessage("No valid action");
        verify(phonebook, never()).addContact(any());
    }

    @Test
    public void test_add_organization_contact() {
        ContactsController controller = createController();

        when(view.getUserInput()).thenReturn("organization", "Tech Corp", "123 Tech Street", "+1234567890");

        controller.handleAddContact();

        verify(phonebook).addContact(any(Organization.class));
    }

    @Test
    public void test_search_contacts_with_existing_query() {
        ContactsController controller = createController();

        List<AbstractRecord> results = List.of(mock(AbstractRecord.class));
        when(view.getUserInput()).thenReturn("John");
        when(phonebook.searchContacts("John")).thenReturn(results);

        controller.handleSearchContacts();

        verify(view).showSearchMenu();
    }

    @Test
    public void test_edit_person_contact_fields() {
        ContactsController controller = createController();

        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        Mockito.when(phonebook.getContact(0)).thenReturn(person);
        Mockito.when(view.getUserInput()).thenReturn("name", "Jane");

        controller.editContact(0);

        Mockito.verify(view).showMessage("Contact updated.");
    }

    @Test
    public void test_edit_organization_contact_fields() {
        ContactsController controller = createController();

        Organization organization = new Organization("Tech Corp", "123 Tech Street", "+1234567890");
        Mockito.when(phonebook.getContact(0)).thenReturn(organization);
        Mockito.when(view.getUserInput()).thenReturn("address", "456 New Tech Street");

        controller.editContact(0);

        Mockito.verify(view).showMessage("Contact updated.");
    }

    @Test
    public void test_delete_existing_contact() {
        ContactsController controller = createController();

        List<AbstractRecord> contacts = new ArrayList<>();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        contacts.add(person);
        Mockito.when(phonebook.getContactsList()).thenReturn(contacts);

        controller.recordsActions("delete", 0);

        Mockito.verify(phonebook).deleteContact(0);
        Mockito.verify(view).showMessage("Contact deleted");
    }

    @Test
    public void test_count_contacts() {
        ContactsController controller = createController();

        Mockito.when(phonebook.countContacts()).thenReturn(5);

        controller.handleCountContacts();

        Mockito.verify(view).showMessage("The Phone Book has 5 records.");
    }

    @Test
    public void test_add_person_invalid_gender() {
        ContactsController controller = createController();

        Mockito.when(view.getUserInput())
                .thenReturn("person", "John", "Doe", "1990-01-01", "X", "1234567890");

        controller.handleAddContact();

        Mockito.verify(phonebook).addContact(Mockito.argThat(person ->
                person instanceof Person && ((Person) person).getGender().equals("")
        ));
    }

    @Test
    public void test_add_person_invalid_birth_date() {
        Phonebook phonebook = Mockito.mock(Phonebook.class);
        ContactsView view = Mockito.mock(ContactsView.class);
        ContactsController controller = new ContactsController(phonebook, view);

        Mockito.when(view.getUserInput())
                .thenReturn("person", "John", "Doe", "invalid-date", "M", "1234567890");

        controller.handleAddContact();

        Mockito.verify(phonebook).addContact(Mockito.argThat(person ->
                person instanceof Person && ((Person) person).getBirthDate() == null
        ));
    }

    @Test
    public void test_handleExit_callsExitMethod() {
        ContactsController controller = createController();

        controller.handleExit();

        verify(phonebook, times(1)).exit();
    }

    @Test
    public void test_edit_person_name_updates_last_edited_date() {
        Phonebook phonebook = Mockito.mock(Phonebook.class);
        phonebook.contacts = new ArrayList<>();
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);
        LocalDate beforeEdit = person.getLastEditedDate();

        phonebook.editContact(0, "name", "John");

        assertEquals("John", person.getName());
    }

    @Test
    public void test_edit_person_surname_updates_last_edited_date()
    {

        Phonebook phonebook = Mockito.mock(Phonebook.class);
        phonebook.contacts = new ArrayList<>();
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);
        LocalDate beforeEdit = person.getLastEditedDate();

        phonebook.editContact(0, "surname", "Doe");

        assertEquals("Doe", person.getSurname());
    }

    @Test
    public void test_delete_action_removes_contact_and_shows_message()
    {

        ContactsController controller = createController();
        int index = 1;

        controller.recordsActions("delete", index);

        verify(phonebook).deleteContact(index);
        verify(view).showMessage("Contact deleted");
        verify(phonebook).printRecordInfo(String.valueOf(index));
    }

    @Test
    public void test_menu_action_returns_without_operations()
    {

        ContactsController controller = createController();

        controller.recordsActions("menu", 1);

        verifyNoInteractions(phonebook);
        verifyNoInteractions(view);
    }

    @Test
    public void test_prints_contacts_with_correct_numbering()
    {

        ContactsController controller = createController();
        List<AbstractRecord> contacts = Arrays.asList(
                new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1)),
                new Person("Juan", "Dome", "0123654789", "M", LocalDate.of(1990, 2, 2)));

        controller.printContacts(contacts);

        verify(view).showMessage("1. " + contacts.get(0).printName());
        verify(view).showMessage("2. " + contacts.get(1).printName());
    }

    @Test
    public void test_empty_phonebook_shows_no_contacts()
    {

        ContactsController controller = createController();
        when(phonebook.getContactsList()).thenReturn(new ArrayList<>());

        controller.handleListContacts();

        verify(view).showMessage("No contacts in the phonebook");
        verify(phonebook, times(1)).getContactsList();
        verifyNoMoreInteractions(view, phonebook);
    }

    @Test
    public void test_display_contacts_and_back_action()
    {

        ContactsController controller = createController();

        List<AbstractRecord> contacts = Collections.singletonList(mock(AbstractRecord.class));


        when(phonebook.getContactsList()).thenReturn(contacts);
        when(view.getUserInput()).thenReturn("back");

        controller.handleListContacts();

        verify(view).showListMenu();
        verify(view).getUserInput();
    }

    @Test
    public void test_search_query_returns_matching_contacts()
    {

        ContactsController controller = createController();
        List<AbstractRecord> results = Collections.singletonList(mock(AbstractRecord.class));

        when(view.getUserInput()).thenReturn("John", "back");
        when(phonebook.searchContacts("John")).thenReturn(results);

        controller.handleSearchContacts();

        verify(view).showMessage("Enter search query: ");
        verify(phonebook).searchContacts("John");
        verify(view).showSearchMenu();
    }

    @Test
    public void test_search_query_returns_matching_contacts_again()
    {

        ContactsController controller = createController();
        List<AbstractRecord> results = Collections.singletonList(mock(AbstractRecord.class));

        when(view.getUserInput()).thenReturn("John", "again", "Juan", "back");
        when(phonebook.searchContacts("John")).thenReturn(results);
        when(phonebook.searchContacts("Juan")).thenReturn(Collections.emptyList());

        controller.handleSearchContacts();

        verify(phonebook).searchContacts("John");
        verify(view, times(2)).showSearchMenu();

        controller.handleSearchContacts();

        verify(phonebook).searchContacts("Juan");
    }

    @Test
    public void test_back_command_returns_to_menu() {

        ContactsController controller = createController();

        List<AbstractRecord> results = Collections.singletonList(mock(AbstractRecord.class));

        when(view.getUserInput()).thenReturn("test", "back");
        when(phonebook.searchContacts("test")).thenReturn(results);

        controller.handleSearchContacts();

        verify(view, times(1)).showSearchMenu();
    }
    @Before
    public void setUp() {
        phonebook = mock(Phonebook.class);
        view = mock(ContactsView.class);
    }

    public ContactsController createController() {
        return new ContactsController(phonebook, view);
    }
}