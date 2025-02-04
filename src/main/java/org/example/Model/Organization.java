package org.example.Model;

import java.util.List;

public class Organization extends AbstractRecord {
    private String organizationName;
    private String address;

    public Organization(String organizationName, String address, String phoneNumber) {
        super(phoneNumber);

        this.organizationName = organizationName;
        this.address = address;
    }

    @Override
    public List<String> getFields() {
        return List.of("organizationName", "address", "number");
    }

    @Override
    public void setField(String field, String value) {
        switch (field) {
            case "organizationName" -> this.organizationName = value;
            case "address" -> this.address = value;
            case "number" -> super.setPhoneNumber(value);
            default -> System.out.println("Invalid field");
        }
    }

    @Override
    public String getField(String field) {
        return switch (field) {
            case "organizationName" -> organizationName;
            case "address" -> address;
            case "number" -> super.getPhoneNumber();
            default -> "[no data]";
        };
    }

    @Override
    public String printName() {
        return organizationName;
    }

    public void printInfo() {
        System.out.println("Organization name: " + organizationName);
        System.out.println("Address: " + address);
        System.out.println("Number: " + super.getPhoneNumber());
        System.out.println("Time created: " + super.getCreatedDate());
        System.out.println("Time last edit: " + super.getLastEditedDate());
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}