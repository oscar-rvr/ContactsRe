package org.example.Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phonebook {
    private List<AbstractRecord> contacts = new ArrayList<>();
    private File file;
    private PhonebookPersistence persistence;
    public Phonebook(String filepath) {
        persistence = new PhonebookPersistence(file);

        if (filepath == null) {
            file = new File("phonebook.db");
            persistence.load(file);
        } else {
            file = new File(filepath);
            persistence.load(file);
        }
    }




    public void addContact(AbstractRecord record) {
        contacts.add(record);
        persistence.save(contacts,file);
    }



    public int count(){
        return contacts.size();
    }

    public void listContacts() {
        if (count()<1) {
            System.out.println("No records to list! phone");
            return;
        } else {

            for (int i = 0; i < contacts.size(); i++) {

                System.out.println((i + 1) + ". " + contacts.get(i).printName());
            }

        }
    }

    public void listActions(String action) {

        if (action.equals("back")){
            return;
        }

        int index = Integer.valueOf(action);
        if(index >0 && index <= contacts.size() ){

            contacts.get(index-1).printInfo();


            recordsActions(index -1);


        }


    }

    public void recordsActions(int index) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n[record] Enter action (edit, delete, menu): phone");
        String recordAction = scanner.nextLine();
        switch (recordAction){
            case "edit" ->{editContact(index);}
            case "delete" ->{deleteContact(index);}
            case "menu" ->{
                return;
            }
            default -> {
                System.out.println("Invalid Action phone");
            }


        }
    }
    public List<AbstractRecord> getContactsList(){

        //ads
        return contacts;
    }

    public void deleteContact(int index){
        contacts.remove(index);

    }


    private void editContact(int index) {
        if (contacts.isEmpty()) {
            System.out.println("No records to edit! phone");
        }

        Scanner scanner = new Scanner(System.in);
        if (contacts.get(index) instanceof Person) {
            Person personObj = (Person) contacts.get(index);
            System.out.println("Select a field (name, surname, birth, gender, number): phone");
            String field = scanner.nextLine();
            switch (field) {
                case "name" -> {
                    System.out.println("Enter the name: phone");
                    personObj.setName(scanner.nextLine());
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "surname" -> {
                    System.out.println("Enter the surname: phone");
                    personObj.setSurname(scanner.nextLine());
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "birth" -> {
                    System.out.println("Enter the birth date: phone");
                    personObj.setBirthDate(verifybirthDate(scanner.nextLine()));
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "gender" -> {
                    System.out.println("Enter the gender (M, F): phone");
                    personObj.setGender(scanner.nextLine());
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "number" -> {
                    System.out.println("Enter the number: phone");
                    personObj.setPhoneNumber(scanner.nextLine());

                    personObj.setLastEditedDate(LocalDate.now());
                }
                default -> System.out.println("Invalid field phone");


            }
        } else {
            Organization organizationObj = (Organization) contacts.get(index);
            System.out.println("Select a field (address, number): phone");
            String field = scanner.nextLine();
            switch (field) {
                case "address" -> {
                    System.out.println("Enter the address: phone");
                    organizationObj.setAddress(scanner.nextLine());
                    organizationObj.setLastEditedDate(LocalDate.now());
                }
                case "number" -> {
                    System.out.println("Enter the number: phone");
                    organizationObj.setPhoneNumber(scanner.nextLine());
                    organizationObj.setLastEditedDate(LocalDate.now());
                }
                default -> System.out.println("Invalid field phone");


            }
        }


    }

    private LocalDate verifybirthDate(String birthDateString) {

        try{

            return LocalDate.parse(birthDateString);
        }catch (Exception e){
            System.out.println("Bad birth date! phone");
            return null;
        }


    }

    public List<AbstractRecord> searchContacts(String query) {
        List<AbstractRecord> results = new ArrayList<>();
        results.clear();

        for (int i = 0; i < contacts.size(); i++) {
            AbstractRecord contact = contacts.get(i);


            String contactData = "";


            List<String> fields = contact.getFields();


            for (int j = 0; j < fields.size(); j++) {
                String fieldName = fields.get(j);

                String fieldValue = contact.getField(fieldName);

                contactData += fieldValue + " ";
            }

            contactData = contactData.trim();


            if (contactData.toLowerCase().contains(query.toLowerCase())) {
                results.add(contact);
            }
        }

        return results;
    }



    public void listSearchResults(List<AbstractRecord> results){
        if(results.isEmpty()){
            System.out.println("No results found phone");
            return;
        }

        System.out.println("Found " + results.size() + " result(s): phone");
        for (AbstractRecord resu : results) {
            System.out.println(resu);
            System.out.println(resu.printName());
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". phone" + results.get(i).printName());
        }
    }

    public void searchAction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter search query: phone");
        String query = scanner.nextLine();

        List<AbstractRecord> searchResults = searchContacts(query);
        listSearchResults(searchResults);


        System.out.println("[search] Enter action ([number], back, again): phone");
        String action = scanner.nextLine();
        switch (action) {
            case "back" -> {
                return;
            }
            case "again" -> {
                searchAction();
            }
            default -> {
                try {
                    int index = Integer.parseInt(action) - 1;
                    if (index >= 0 && index < searchResults.size()) {
                        searchResults.get(index).printInfo();
                        recordsActions(index);
                    } else {
                        System.out.println("Invalid action phone");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid action phone");
                }
            }
        }



    }

}//end class
