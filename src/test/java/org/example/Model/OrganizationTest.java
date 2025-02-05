package org.example.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {
    private Organization organization;

    @BeforeEach
    void setUp() {
        organization = new Organization("Tech Corp", "123 Tech Street", "9876543210");
    }

    private void assertPrintInfoFieldsOrderAndFormat(String output) {
        String[] lines = output.split(System.lineSeparator());
        assertThat(lines[0]).startsWith("Organization name: ");
        assertThat(lines[1]).startsWith("Address: ");
        assertThat(lines[2]).startsWith("Number: ");
        assertThat(lines[3]).startsWith("Time created: ");
        assertThat(lines[4]).startsWith("Time last edit: ");
    }

    @Test
    void givenExistingContacts_whenLoaded_thenPhonebookIsEmpty() {
        List<String> fields = organization.getFields();
        assertEquals(List.of("organizationName", "address", "number"), fields);
    }

    @Test
    void givenValidField_whenSetField_thenFieldIsUpdated() {
        organization.setField("organizationName", "New Tech");
        assertEquals("New Tech", organization.getOrganizationName());

        organization.setField("address", "456 Innovation Ave");
        assertEquals("456 Innovation Ave", organization.getAddress());

        organization.setField("number", "1234567890");
        assertEquals("1234567890", organization.getPhoneNumber());
    }

    @Test
    void givenInvalidField_whenSetField_thenNoChanges() {
        organization.setField("invalid", "Some Value");
        assertEquals("Tech Corp", organization.getOrganizationName());
    }

    @Test
    void givenValidField_whenGetField_thenReturnsCorrectValue() {
        assertEquals("Tech Corp", organization.getField("organizationName"));
        assertEquals("123 Tech Street", organization.getField("address"));
        assertEquals("9876543210", organization.getField("number"));
    }

    @Test
    void givenInvalidField_whenGetField_thenReturnsNoData() {
        assertEquals("[no data]", organization.getField("invalid"));
    }

    @Test
    void givenOrganization_whenPrintName_thenReturnsOrganizationName() {
        assertEquals("Tech Corp", organization.printName());
    }

    @Test
    void givenValidOrganizationName_whenSet_thenNameIsUpdated() {
        Organization organization = new Organization("Old Name", "Address", "123456");
        String newName = "New Company Name";
        organization.setOrganizationName(newName);
        assertEquals(newName, organization.getOrganizationName());
    }

    @Test
    void givenEmptyOrganizationName_whenSet_thenNameIsEmpty() {
        Organization organization = new Organization("Test Org", "Address", "123456");
        String emptyName = "";
        organization.setOrganizationName(emptyName);
        assertEquals(emptyName, organization.getOrganizationName());
    }

    @Test
    void givenOrganization_whenPrintInfo_thenFieldsOrderAndFormatAreCorrect() {
        Organization org = new Organization("Test Org", "123 Main St", "+1234567890");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.printInfo();

        String output = outContent.toString();
        assertPrintInfoFieldsOrderAndFormat(output);
    }

    @Test
    void givenOrganization_whenPrintInfo_thenDatesAreInExpectedFormat() {
        Organization org = new Organization("Test Org", "123 Main St", "+1234567890");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.printInfo();

        String output = outContent.toString();
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        assertTrue(output.contains("Time created: " + org.getCreatedDate().toString()));
        assertTrue(output.contains("Time last edit: " + org.getLastEditedDate().toString()));
        assertTrue(org.getCreatedDate().toString().matches(datePattern));
        assertTrue(org.getLastEditedDate().toString().matches(datePattern));
    }
}
