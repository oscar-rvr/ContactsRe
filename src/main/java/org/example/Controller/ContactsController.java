package org.example.Controller;

import org.example.Model.AbstractRecord;
import org.example.Model.Organization;
import org.example.Model.Person;
import org.example.Model.Phonebook;
import org.example.View.ContactsView;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ContactsController
{
    private final ContactsView view;
    private final Phonebook phonebook;
    Scanner scanner = new Scanner(System.in);

    public ContactsController(Phonebook phonebook, ContactsView view)
    {
        this.phonebook = phonebook;
        this.view = view;
    }

    public void run()
    {
        while (true) {
            view.showMenu();
            String action = view.getUserInput().toLowerCase();
            switch (action) {
                case "add" -> handleAddContact();
                case "list" -> handleListContacts();
                case "search" -> handleSearchContacts();
                case "count" -> handleCountContacts();
                case "exit" -> {
                    handleExit();
                    return;
                }
                default -> view.showMessage("Invalid action");
            }
        }
    }

    private void handleExit()
    {
        phonebook.exit();
    }

    private void handleAddContact()
    {
        view.showMessage("Enter the type (person, organization):");
        String type = view.getUserInput();

        if (type.equalsIgnoreCase("person")) {
            view.showMessage("Enter the name:");
            String name = view.getUserInput();

            view.showMessage("Enter the surname:");
            String surname = view.getUserInput();

            view.showMessage("Enter the birth date:");
            LocalDate birthDate = phonebook.verifybirthDate(view.getUserInput());

            view.showMessage("Enter the gender (M, F): ");
            String inputGender = view.getUserInput();
            //String gender = phonebook.verifyGender(inputGender) ? inputGender : "";
            String gender;
            if (!phonebook.verifyGender(inputGender)) {
                System.out.println("Bad gender! ");
                gender = "";
            }
            else {
                gender = inputGender;
            }
            view.showMessage("Enter the number:");
            String phoneNumber = view.getUserInput();

            phonebook.addContact(new Person(name, surname, phoneNumber, gender, birthDate));
        }
        else if (type.equalsIgnoreCase("organization")) {
            view.showMessage("Enter the organization name:");
            String name = view.getUserInput();

            view.showMessage("Enter the address:");
            String address = view.getUserInput();

            view.showMessage("Enter the number:");
            String phoneNumber = view.getUserInput();

            phonebook.addContact(new Organization(name, address, phoneNumber));
        }
        else {
            view.showMessage("No valid action");
        }
    }

    private void handleListContacts()
    {
        if (phonebook.getContactsList().isEmpty()) {
            view.showMessage("No contacts in the phonebook");
            return;
        }

        printContacts(phonebook.getContactsList());
        view.showListMenu();

        String action = view.getUserInput();
        if (!action.equals("back")) {

            phonebook.printRecordInfo(action);

            view.printRecordMenu();

            String recordAction = scanner.nextLine();
            int index = Integer.parseInt(action) - 1;

            recordsActions(recordAction, index);
        }
        else {
            return;
        }
    }

    private void recordsActions(String recordAction, int index)
    {

        switch (recordAction) {
            case "edit" -> {
                editContact(index);
            }
            case "delete" -> {
                phonebook.deleteContact(index);
                view.showMessage("Contact deleted");
            }
            case "menu" -> {
                return;
            }
            default -> {
                System.out.println("Invalid Action controller");
            }
        }

        phonebook.printRecordInfo(String.valueOf(index));
    }

    private void editContact(int index)
    {
        //view.selectAField();

        String field = "";
//view.showMessage("Enter new value:");
        AbstractRecord contact = phonebook.getContact(index);

        String newValue = "";

        if (contact instanceof Person) {
            view.selectAField();
            field = view.getUserInput();
            switch (field) {
                case "name" -> {
                    System.out.println("Enter the name: phone");
                    newValue = view.getUserInput();
                }
                case "surname" -> {
                    System.out.println("Enter the surname: phone");
                    newValue = view.getUserInput();
                }
                case "birth" -> {
                    System.out.println("Enter the birth date: phone");
                    newValue = view.getUserInput();

                    //LocalDate birth = phonebook.verifybirthDate(newValue);
                }
                case "gender" -> {
                    System.out.println("Enter the gender (M, F): phone");
                    newValue = view.getUserInput();
                }
                case "number" -> {
                    System.out.println("Enter the number: phone");
                    newValue = view.getUserInput();
                }
                default -> System.out.println("Invalid field phone");
            }
        }
        else if (contact instanceof Organization) {
            view.showMessage("Select field (address, number):");

            field = view.getUserInput();

            switch (field) {
                case "address" -> {
                    System.out.println("Enter the address: ");

                    newValue = view.getUserInput();
                }
                case "number" -> {
                    System.out.println("Enter the number: ");

                    newValue = view.getUserInput();
                }
                default -> System.out.println("Invalid field");
            }
        }

        phonebook.editContact(index, field, newValue);
        view.showMessage("Contact updated.");
    }

    private void printContacts(List<AbstractRecord> contacts)
    {
        for (int i = 0; i < contacts.size(); i++) {

            view.showMessage((i + 1) + ". " + contacts.get(i).printName());
        }
    }

    private void handleSearchContacts()
    {
        view.showMessage("Enter search query: ");
        String query = view.getUserInput();

        List<AbstractRecord> results = phonebook.searchContacts(query);
        // phonebook.listSearchResults(results);
        listSearchResults(results);

        view.showSearchMenu();
        String action = view.getUserInput();

        switch (action) {
            case "back" -> {
                return;
            }
            case "again" -> handleSearchContacts();
            default -> {
                try {
                    int index = Integer.parseInt(action) - 1;
                    if (index >= 0 && index < results.size()) {
                        results.get(index).printInfo();
                    }
                    else {
                        view.showMessage("Invalid action");
                    }
                }
                catch (NumberFormatException e) {
                    view.showMessage("Invalid action");
                }
            }
        }
    }

    private void listSearchResults(List<AbstractRecord> results)
    {
        if (results.isEmpty()) {
            System.out.println("No results found phone");
            return;
        }

        System.out.println("Found " + results.size() + " result(s): phone");
        for (AbstractRecord resu : results) {

            System.out.println(resu.printName());
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).printName());
        }
    }

    private void handleCountContacts()
    {
        int count = phonebook.countContacts();
        view.showMessage("The Phone Book has " + count + " records.");
    }
}
