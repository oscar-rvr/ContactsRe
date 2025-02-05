package org.example.Model;
import java.time.LocalDate;
import java.util.List;

public class Person extends AbstractRecord {
    private String name;
    private String surname;
    private String gender;
    private LocalDate birthDate;

    public Person(String name, String surname, String phoneNumber, String gender, LocalDate birthDate) {
        super(phoneNumber);
        //super(isPerson);
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    @Override
    public List<String> getFields() {
        return List.of("name", "surname", "birth", "gender", "number");
    }

    @Override
    public void setField(String field, String value) {
        switch (field) {
            case "name" -> this.name = value;
            case "surname" -> this.surname = value;
            case "birth" -> this.birthDate = LocalDate.parse(value);
            case "gender" -> this.gender = value;
            case "number" -> super.setPhoneNumber(value);
            default -> System.out.println("Invalid field");
        }
    }

    @Override
    public String getField(String field) {
        return switch (field) {
            case "name" -> name;
            case "surname" -> surname;
            case "birth" -> birthDate != null ? birthDate.toString() : "[no data]";
            case "gender" -> gender.isEmpty() ? "[no data]" : gender;
            case "number" -> super.getPhoneNumber();
            default -> "[no data]";
        };
    }

    @Override
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        if (birthDate == null) {
            System.out.println("Birth date: [no data]");
        } else {
            System.out.println("Birth date: " + birthDate);
        }
        if (gender == "") {
            System.out.println("Gender: [no data]");
        } else {
            System.out.println("Gender: " + gender);
        }
        System.out.println("Number: " + super.getPhoneNumber());
        System.out.println("Time created: " + super.getCreatedDate());
        System.out.println("Time last edit: " + super.getLastEditedDate());
    }

    @Override
    public String printName() {
        return name + " " + surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}