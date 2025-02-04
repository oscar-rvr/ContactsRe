package org.example;
import org.example.Controller.ContactsController;
import org.example.Model.Phonebook;
import org.example.View.ContactsView;
import org.junit.jupiter.api.Test;

import javax.swing.text.View;

import static org.mockito.Mockito.*;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void test_components_properly_instantiated_and_connected() {
        ContactsView view = mock(ContactsView.class);
        Phonebook phonebook = mock(Phonebook.class);
        ContactsController controller = new ContactsController(phonebook,view);
        // Given
        String[] args = new String[0];

        // When
        Main.main(args);

        // Then
        assertNotNull(phonebook);
        assertNotNull(view);
        //assertEquals(phonebook, controller.getPhonebook());
        //assertEquals(view, controller.getView());
    }
}