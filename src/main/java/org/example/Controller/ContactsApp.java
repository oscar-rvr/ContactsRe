/*package org.example.Controller;
import org.example.Model.Organization;
import org.example.Model.Person;
import org.example.Model.Phonebook;

import java.time.LocalDate;
import java.util.Scanner;

public class ContactsApp {
    private Scanner scanner = new Scanner(System.in);
    private Phonebook phonebook;

    public ContactsApp(Phonebook phonebook) {
        this.phonebook = phonebook;
    }

    public void run() {
        while (true) {
            showMenu();
            String action = scanner.nextLine().toLowerCase();
            switch (action) {
                case "add" -> addContact();
                case "list" -> listContacts();
                case "search" -> phonebook.searchAction();


                case "count" -> countContacts();
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Invalid action");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n[menu] Enter action (add, list, search, count, exit):");
    }
    public void countContacts(){
        int t = phonebook.count();
        System.out.println("The Phone Book has "+t+" records.");
    }

    public void addContact() {




        System.out.println("Enter the type (person, organization):");
        String type =scanner.nextLine();
        if(type.equals("person")){

            System.out.println("Enter the name:");
            String name=scanner.nextLine();

            System.out.println("Enter the surname of the person:");
            String surname = scanner.nextLine();

            System.out.println("Enter the birth date:");
            String birthDateString = scanner.nextLine();
            LocalDate birthDate = verifybirthDate(birthDateString);


            System.out.println("Enter the gender (M, F):");
            String gender = scanner.nextLine();
            if(!verifyGender(gender)){
                gender=gender;
            }else{
                System.out.println("Bad gender!");
                gender = "";
            }
            System.out.println("Enter the number:");
            String phoneNumber = scanner.nextLine();



            phonebook.addContact(new Person(name,surname, phoneNumber,gender, birthDate));
            System.out.println("The record added.");
        }else{
            System.out.println("Enter the organization name:");
            String name=scanner.nextLine();
            System.out.println("Enter the address:");
            String address = scanner.nextLine();
            System.out.println("Enter the number:");
            String phoneNumber = scanner.nextLine();
            phonebook.addContact(new Organization(name,address,phoneNumber));
        }
    }


    private LocalDate verifybirthDate(String birthDateString) {

        try{

            return LocalDate.parse(birthDateString);
        }catch (Exception e){
            System.out.println("Bad birth date!");
            return null;
        }


    }

    private boolean verifyGender(String gender) {
        if (!gender.equalsIgnoreCase("M") || !gender.equalsIgnoreCase("H")) {
            return false;
        }

        return true;
    }

    public void listContacts() {
        phonebook.listContacts();
        printListMenu();

        String action = scanner.nextLine();
        phonebook.listActions(action);

    }

    private void printListMenu(){
        System.out.println("[list] Enter action ([number], back):");
    }

    private void printRecordMenu() {
        System.out.println("[record] Enter action (edit, delete, menu):");
    }




}//end class




*/