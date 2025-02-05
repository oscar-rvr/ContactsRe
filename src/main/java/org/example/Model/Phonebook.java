package org.example.Model;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Phonebook {
    public List<AbstractRecord> contacts = new ArrayList<>();
    private File file;
    private final PhonebookPersistence persistence;
    private final String dbName = "phonebook.db";

    public Phonebook(String filepath) {
        persistence = new PhonebookPersistence(file);

        if (filepath == null) {
            file = new File(dbName);
        } else {
            file = new File(filepath);
        }

        if (file.exists()) {
            List<AbstractRecord> loadedContacts = persistence.load(file);
            if (loadedContacts != null) {
                contacts.addAll(loadedContacts);
                System.out.println("Open phonebook.db");
            }
        }
    }

    public void addContact(AbstractRecord record) {
        contacts.add(record);
        persistence.save(contacts, file);
    }

    public int countContacts() {
        return contacts.size();
    }

    public void exit() {
        persistence.save(contacts, file);
    }

    public void printRecordInfo(String action) {

        int index = Integer.valueOf(action);
        if (index > 0 && index <= contacts.size()) {
            //from superclass
            contacts.get(index - 1).printInfo();
        }
    }

    public boolean containsNumber(int index) {
        return index >= 0 && index < contacts.size();
    }


    public List<AbstractRecord> getContactsList() {
        return contacts;
    }

    public void deleteContact(int index) {
        contacts.remove(index);
    }

    public void editContact(int index, String field, String newValue) {
        if (contacts.get(index) instanceof Person personObj) {
            switch (field) {
                case "name" -> {
                    personObj.setName(newValue);
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "surname" -> {
                    personObj.setSurname(newValue);
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "birth" -> {
                    LocalDate birth = verifybirthDate(newValue);
                    personObj.setBirthDate(birth);
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "gender" -> {
                    personObj.setGender(newValue);
                    personObj.setLastEditedDate(LocalDate.now());
                }
                case "number" -> {
                    personObj.setPhoneNumber(newValue);

                    personObj.setLastEditedDate(LocalDate.now());
                }
                default -> System.out.println("Invalid field ");
            }
        } else if (contacts.get(index) instanceof Organization organizationObj) {
            switch (field) {
                case "name" -> {
                    organizationObj.setOrganizationName(newValue);
                    organizationObj.setLastEditedDate(LocalDate.now());
                }
                case "address" -> {
                    organizationObj.setAddress(newValue);
                    organizationObj.setLastEditedDate(LocalDate.now());
                }
                case "number" -> {
                    organizationObj.setPhoneNumber(newValue);
                    organizationObj.setLastEditedDate(LocalDate.now());
                }
                default -> System.out.println("Invalid field");
            }
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

    public AbstractRecord getContact(int index) {
        return contacts.get(index);
    }

    public boolean verifyGender(String gender) {
        return gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F");
    }

    public LocalDate verifybirthDate(String birthDateString) {
        try {

            return LocalDate.parse(birthDateString);
        } catch (Exception e) {
            System.out.println("Bad birth date! ");
            return null;
        }
    }
    //for testing
    public void cleardb(){
        contacts.clear();
        persistence.save(contacts,file);
    }
}