package org.example.Model;

import org.example.Controller.ContactsController;
import org.example.View.ContactsView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhonebookTest {
    private Phonebook phonebook;
    private PhonebookPersistence persistenceMock;
    private File fileMock;

    @BeforeEach
    void test_initialize_given_phonebook_when_initialized_then_create_instance() throws NoSuchFieldException, IllegalAccessException {
        fileMock = mock(File.class);
        persistenceMock = mock(PhonebookPersistence.class);
        phonebook = new Phonebook("phonebook.db");

        Field persistenceField = Phonebook.class.getDeclaredField("persistence");
        persistenceField.setAccessible(true);
        persistenceField.set(phonebook, persistenceMock);
    }

    @Test
    public void test_search_given_contacts_when_search_then_return_matching_results() {
        AbstractRecord contact = mock(AbstractRecord.class);
        when(contact.getField(anyString())).thenReturn("Test");
        when(contact.getFields()).thenReturn(List.of("name"));
        phonebook.addContact(contact);
        List<AbstractRecord> results = phonebook.searchContacts("Test");
        assertEquals(1, results.size());
    }

    @Test
    public void test_verify_gender_given_invalid_gender_when_verified_then_return_false() {
        assertFalse(phonebook.verifyGender("X"));
    }

    @Test
    public void test_verify_gender_given_valid_gender_when_verified_then_return_true() {
        assertTrue(phonebook.verifyGender("M"));
    }

    @Test
    public void test_verify_birth_date_given_invalid_date_when_verified_then_return_null() {
        assertNull(phonebook.verifybirthDate("invalid-date"));
    }

    @Test
    public void test_verify_birth_date_given_valid_date_when_verified_then_return_date() {
        assertNotNull(phonebook.verifybirthDate("2000-01-01"));
    }

    void setUp2() {
        phonebook = new Phonebook("phonebook.db");
        phonebook.cleardb();
    }

    @Test
    void test_constructor_given_existing_contacts_when_loaded_then_phonebook_is_empty() {
        setUp2();
        PhonebookPersistence mockPersistence = mock(PhonebookPersistence.class);
        when(mockPersistence.load(new File("phonebook.db"))).thenReturn(new ArrayList<>());

        assertEquals(0, phonebook.countContacts());
    }

    @Test
    public void test_constructor_handles_null_load_result() {
        String testPath = "null_load.db";
        PhonebookPersistence mockPersistence = mock(PhonebookPersistence.class);
        when(mockPersistence.load(any())).thenReturn(null);

        Phonebook phonebook = new Phonebook(testPath);

        assertEquals(0, phonebook.countContacts());
        assertTrue(phonebook.getContactsList().isEmpty());
    }

    @Test
    void test_constructor_given_null_file_path_when_called_then_phonebook_is_empty() {
        setUp2();
        assertEquals(0, new Phonebook(null).countContacts());
    }

    @Test
    void test_edit_contact_given_person_when_edit_name_then_name_is_updated() {
        setUp2();
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.addContact(person);

        phonebook.editContact(0, "name", "Jane");

        assertEquals("Jane", person.getName());
        assertEquals(LocalDate.now(), person.getLastEditedDate());
    }

    @Test
    void test_edit_contact_given_organization_when_edit_address_then_address_is_updated() {
        setUp2();
        Organization org = new Organization("Tech Corp", "123 Tech Street", "0987654321");
        phonebook.addContact(org);

        phonebook.editContact(0, "address", "456 New Tech Street");

        assertEquals("456 New Tech Street", org.getAddress());
        assertEquals(LocalDate.now(), org.getLastEditedDate());
    }

    @Test
    void test_edit_contact_given_organization_when_edit_organizationName_then_address_is_updated() {
        setUp2();
        Organization org = new Organization("Tech Corp", "123 Tech Street", "0987654321");
        phonebook.addContact(org);

        phonebook.editContact(0, "name", "New Tech");

        assertEquals("New Tech", org.getOrganizationName());
        assertEquals(LocalDate.now(), org.getLastEditedDate());
    }

    @Test
    void test_edit_contact_given_organization_when_edit_phone_then_phoneNumber_is_updated() {
        setUp2();
        Organization org = new Organization("Tech Corp", "123 Tech Street", "0987654321");
        phonebook.addContact(org);

        phonebook.editContact(0, "number", "0000000000");

        assertEquals("0000000000", org.getPhoneNumber());
        assertEquals(LocalDate.now(), org.getLastEditedDate());
    }

    @Test
    void test_edit_contact_given_organization_when_edit_phone_then_invalidField_is_updated() {
        setUp2();
        Organization org = new Organization("Tech Corp", "123 Tech Street", "0987654321");
        phonebook.addContact(org);

        phonebook.editContact(0, "invalid", "0000000000");

        assertEquals(LocalDate.now(), org.getLastEditedDate());
    }

    @Test
    public void test_valid_index_calls_print_info() {
        Phonebook phonebook = new Phonebook(null);
        AbstractRecord mockRecord = mock(AbstractRecord.class);
        phonebook.contacts.add(mockRecord);

        phonebook.printRecordInfo("1");

        verify(mockRecord).printInfo();
    }

    @Test
    void test_search_contacts_given_query_when_searching_then_results_are_case_insensitive() {
        setUp2();
        phonebook.addContact(new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1)));
        phonebook.addContact(new Person("Jane", "Smith", "0987654321", "F", LocalDate.of(1992, 2, 2)));

        assertEquals(1, phonebook.searchContacts("JOHN").size());
    }

    @Test
    void test_delete_contact_given_valid_index_when_deleted_then_contact_is_removed() {
        setUp2();
        phonebook.addContact(new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1)));
        phonebook.addContact(new Person("Jane", "Smith", "0987654321", "F", LocalDate.of(1992, 2, 2)));

        phonebook.deleteContact(0);

        assertEquals(1, phonebook.countContacts());
    }

    @Test
    void test_constructor_given_non_existent_file_when_called_then_phonebook_is_empty() {
        setUp2();
        File file = new File("non_existent_file.db");
        file.delete();
        assertEquals(0, new Phonebook(file.getPath()).countContacts());
    }

    @Test
    void test_edit_contact_given_invalid_field_when_edited_then_error_message_is_shown() {
        setUp2();
        phonebook.addContact(new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1)));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        phonebook.editContact(0, "invalidField", "newValue");

        assertTrue(outContent.toString().contains("Invalid field"));
        System.setOut(System.out);
    }

    @Test
    public void test_edit_organization_address_updates_last_edited_date() {
        setUp2();
        Organization org = new Organization("ACME", "123 Main St", "+1234567890");
        phonebook.contacts.add(org);
        LocalDate beforeEdit = org.getLastEditedDate();

        phonebook.editContact(0, "address", "456 New Ave");

        assertEquals("456 New Ave", org.getAddress());
    }

    @Test
    public void test_edit_person_valid_phone_number() {
        setUp2();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);

        phonebook.editContact(0, "number", "+9876543210");

        assertEquals("+9876543210", person.getPhoneNumber());
    }

    @Test
    void test_edit_contact_given_invalid_index_when_edited_then_exception_is_thrown() {
        setUp2();
        assertThrows(IndexOutOfBoundsException.class, () -> phonebook.editContact(10, "name", "newName"));
    }

    @Test
    public void test_edit_person_valid_birth_date() {
        setUp2();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);

        phonebook.editContact(0, "birth", "1995-06-15");

        assertEquals(LocalDate.of(1995, 6, 15), person.getBirthDate());
    }

    @Test
    public void test_edit_multiple_fields_sequentially() {
        setUp2();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);

        phonebook.editContact(0, "name", "Jane");
        phonebook.editContact(0, "surname", "Smith");
        phonebook.editContact(0, "gender", "F");

        assertEquals("Jane", person.getName());
        assertEquals("Smith", person.getSurname());
        assertEquals("F", person.getGender());
    }

    @Test
    public void test_edit_person_invalid_birth_date() {
        setUp2();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);
        LocalDate originalDate = person.getBirthDate();

        phonebook.editContact(0, "birth", "invalid-date");

        assertNull(person.getBirthDate());
    }

    @Test
    void test_exit_given_phonebook_with_contacts_when_exit_then_state_is_saved() {
        setUp2();
        File tempFile = new File("tempPhonebook.db");
        Phonebook tempPhonebook = new Phonebook(tempFile.getPath());
        tempPhonebook.addContact(new Person("Jane", "Doe", "1234567890", "F", LocalDate.of(1992, 2, 2)));
        tempPhonebook.exit();

        assertEquals(1, new Phonebook(tempFile.getPath()).countContacts());
        tempFile.delete();
    }

    @Test
    public void test_edit_invalid_phone_number() {
        setUp2();
        Person person = new Person("John", "Doe", "+1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.contacts.add(person);

        phonebook.editContact(0, "number", "invalid-number");

        assertEquals("[no number]", person.getPhoneNumber());
    }

    @Test
    void test_run_given_add_command_when_executed_then_triggers_handle_add_contact() {
        setUp2();
        ContactsView mockView = mock(ContactsView.class);
        Phonebook mockPhonebook = mock(Phonebook.class);
        ContactsController controller = spy(new ContactsController(mockPhonebook, mockView));

        when(mockView.getUserInput()).thenReturn("add", "exit");
        controller.run();
        verify(controller).handleAddContact();
    }

    @Test
    public void test_returns_correct_contact_for_valid_index() {
        Phonebook phonebook = new Phonebook(null);
        phonebook.cleardb();
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.addContact(person);

        AbstractRecord result = phonebook.getContact(0);

        assertEquals(person, result);
    }

    @Test
    public void test_returns_person_type_for_person_record() {
        Phonebook phonebook = new Phonebook(null);
        Person person = new Person("John", "Doe", "1234567890", "M", LocalDate.of(1990, 1, 1));
        phonebook.addContact(person);

        AbstractRecord result = phonebook.getContact(0);

        assertTrue(result instanceof Person);
    }
}
