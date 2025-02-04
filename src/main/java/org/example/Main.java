package org.example;
import org.example.Controller.ContactsController;
import org.example.Model.Phonebook;
import org.example.View.ContactsView;

public class Main {
    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : null;
        Phonebook phonebook = new Phonebook(filePath);
        ContactsView view = new ContactsView();
        ContactsController controller = new ContactsController(phonebook, view);
        controller.run();
    }
}
