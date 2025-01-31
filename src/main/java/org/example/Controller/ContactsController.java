package org.example.Controller;

import org.example.Model.AbstractRecord;
import org.example.Model.Organization;
import org.example.Model.Person;
import org.example.Model.Phonebook;
import org.example.View.ContactsView;

import java.time.LocalDate;
import java.util.List;

public class ContactsController {
    private final ContactsView view;
    private final Phonebook phonebook;

    public ContactsController(Phonebook phonebook, ContactsView view){
        this.phonebook=phonebook;
        this.view = view;
    }

    public void run(){
        while(true){
            view.showMenu();
            String action = view.getUserInput().toLowerCase();
            switch(action) {
                case "add" -> addContact();
                case "list" -> listContacts2();
                case "search" -> searchContacts();
                case "count" -> countContacts();
                case "exit" -> {
                    return;
                }
                default -> view.showMessage("Invalid action");
            }
        }
    }

    private void addContact(){
        view.showMessage("Enter the type (person, organization):");
        String type = view.getUserInput();

        if(type.equalsIgnoreCase("person")){
            view.showMessage("Enter the name:");
            String name = view.getUserInput();

            view.showMessage("Enter the surname:");
            String surname = view.getUserInput();

            view.showMessage("Enter the birth date:");
            LocalDate birthDate = verifyBirthDate(view.getUserInput());

            view.showMessage("Enter the gender (M, F):controller");
            String inputGender = view.getUserInput();
                  String gender =  verifyGender(inputGender) ? inputGender : "";

            view.showMessage("Enter the number:");
            String phoneNumber = view.getUserInput();

            phonebook.addContact(new Person(name,surname,phoneNumber,gender,birthDate));
        }else {
            view.showMessage("Enter the organization name:");
            String name = view.getUserInput();

            view.showMessage("Enter the address:");
            String address = view.getUserInput();

            view.showMessage("Enter the number:");
            String phoneNumber = view.getUserInput();

            phonebook.addContact(new Organization(name, address, phoneNumber));
        }


        }
    private void listContacts() {
        //asdas





        view.showListMenu();

        String action = view.getUserInput();
        if (!action.equals("back")) {
            phonebook.listActions(action);
        }

        printContacts(phonebook.getContactsList());





    }
    private void listContacts2(){
        printContacts(phonebook.getContactsList());

    }

    private void printContacts(List<AbstractRecord> contacts){
        for (int i = 0; i < contacts.size(); i++) {

            view.showMessage((i + 1) + ". " + contacts.get(i).printName());
        }
    }

    private void listActions2(){

    }

    private void listActions(String action){

    }


    private void searchContacts() {
        view.showMessage("Enter search query: ");
        String query = view.getUserInput();

        List<AbstractRecord> results = phonebook.searchContacts(query);
        phonebook.listSearchResults(results);

        view.showSearchMenu();
        String action = view.getUserInput();

        switch (action) {
            case "back" -> { return; }
            case "again" -> searchContacts();
            default -> {
                try {
                    int index = Integer.parseInt(action) - 1;
                    if (index >= 0 && index < results.size()) {
                        results.get(index).printInfo();
                        phonebook.recordsActions(index);
                    } else {
                        view.showMessage("Invalid action");
                    }
                } catch (NumberFormatException e) {
                    view.showMessage("Invalid action");
                }
            }
        }
    }






    private void countContacts() {
        int count = phonebook.count();
        view.showMessage("The Phone Book has " + count + " records.");
    }

    private LocalDate verifyBirthDate(String birthDateString) {
        try {
            return LocalDate.parse(birthDateString);
        } catch (Exception e) {
            view.showMessage("Bad birth date!");
            return null;
        }
    }

    private boolean verifyGender(String gender) {
        return gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F");
    }

}
