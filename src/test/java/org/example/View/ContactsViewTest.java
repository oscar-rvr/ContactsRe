package org.example.View;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactsViewTest {

    @Test
    public void test_show_menu_given_menu_when_displayed_then_correct_options() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showMenu();

        String expectedOutput = "\n[menu] Enter action (add, list, search, count, exit):\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_show_record_menu_given_menu_when_displayed_then_correct_options() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showRecordMenu();

        String expectedOutput = "[record] Enter action (edit, delete, menu):\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_show_search_menu_given_menu_when_displayed_then_correct_options() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showSearchMenu();

        String expectedOutput = "[search] Enter action ([number], back, again):\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_get_user_input_given_input_when_received_then_correct_output() {
        String input = "test input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ContactsView contactsView = new ContactsView();
        String userInput = contactsView.getUserInput();

        assertEquals(input, userInput);

        System.setIn(System.in);
    }

    @Test
    public void test_show_message_given_message_when_displayed_then_correct_output() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        String message = "Test message";
        contactsView.showMessage(message);

        assertEquals("Test message\n", outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_get_user_input_when_scanner_closed_then_throw_exception() throws NoSuchFieldException, IllegalAccessException {
        Scanner mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenThrow(new IllegalStateException("Scanner closed"));

        ContactsView contactsView = new ContactsView();
        Field scannerField = ContactsView.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(contactsView, mockScanner);

        assertThrows(IllegalStateException.class, () -> {
            contactsView.getUserInput();
        });
    }

    @Test
    public void test_show_message_given_null_when_message_displayed_then_correct_output() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showMessage(null);

        String expectedOutput = "null\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_show_list_menu_given_menu_when_displayed_then_correct_options() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showListMenu();

        String expectedOutput = "[list] Enter action ([number], back):\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_multiple_rapid_menu_navigation_calls_given_multiple_menu_calls_then_correct_order() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();
        contactsView.showMenu();
        contactsView.showListMenu();
        contactsView.showRecordMenu();
        contactsView.showSearchMenu();

        String expectedOutput = "\n[menu] Enter action (add, list, search, count, exit):\n"
                + "[list] Enter action ([number], back):\n"
                + "[record] Enter action (edit, delete, menu):\n"
                + "[search] Enter action ([number], back, again):\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void test_menu_state_transitions_given_menu_calls_then_correct_output() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ContactsView contactsView = new ContactsView();

        contactsView.showMenu();
        String expectedMenuOutput = "\n[menu] Enter action (add, list, search, count, exit):\n";
        assertEquals(expectedMenuOutput, outContent.toString());

        outContent.reset();
        contactsView.showListMenu();
        String expectedListMenuOutput = "[list] Enter action ([number], back):\n";
        assertEquals(expectedListMenuOutput, outContent.toString());

        outContent.reset();
        contactsView.showRecordMenu();
        String expectedRecordMenuOutput = "[record] Enter action (edit, delete, menu):\n";
        assertEquals(expectedRecordMenuOutput, outContent.toString());

        outContent.reset();
        contactsView.showSearchMenu();
        String expectedSearchMenuOutput = "[search] Enter action ([number], back, again):\n";
        assertEquals(expectedSearchMenuOutput, outContent.toString());

        outContent.reset();
        contactsView.printRecordMenu();
        String expectedSearchMenuOutputPrint = "\n[record] Enter action (edit, delete, menu): \n";
        assertEquals(expectedSearchMenuOutputPrint, outContent.toString());

        outContent.reset();
        contactsView.selectAField();
        String expectedSearchMenuOutputPrintField = "Select a field (name, surname, birth, gender, number): \n";
        assertEquals(expectedSearchMenuOutputPrintField, outContent.toString());

        System.setOut(System.out);
    }
}
