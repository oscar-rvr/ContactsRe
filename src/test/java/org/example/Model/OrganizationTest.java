package org.example.Model;
import org.example.Model.Organization;
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

    @Test
    void givenOrganization_whenGetFields_thenReturnCorrectFields() {
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
    void givenValidField_whenGetField_thenReturnCorrectValue() {
        assertEquals("Tech Corp", organization.getField("organizationName"));
        assertEquals("123 Tech Street", organization.getField("address"));
        assertEquals("9876543210", organization.getField("number"));
    }

    @Test
    void givenInvalidField_whenGetField_thenReturnNoData() {
        assertEquals("[no data]", organization.getField("invalid"));
    }

    @Test
    void givenOrganization_whenPrintName_thenReturnOrganizationName() {
        assertEquals("Tech Corp", organization.printName());
    }

    @Test
    public void test_set_valid_organization_name() {
        // Given
        Organization organization = new Organization("Old Name", "Address", "123456");
        String newName = "New Company Name";

        // When
        organization.setOrganizationName(newName);

        // Then
        assertEquals(newName, organization.getOrganizationName());
    }

    @Test
    public void test_set_empty_organization_name() {
        // Given
        Organization organization = new Organization("Test Org", "Address", "123456");
        String emptyName = "";

        // When
        organization.setOrganizationName(emptyName);

        // Then
        assertEquals(emptyName, organization.getOrganizationName());
    }
    @Test
    public void test_print_info_fields_order_and_format() {
        // Given
        Organization org = new Organization("Test Org", "123 Main St", "+1234567890");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // When
        org.printInfo();

        // Then
        String output = outContent.toString();
        String[] lines = output.split(System.lineSeparator());
        assertThat(lines[0]).startsWith("Organization name: ");
        assertThat(lines[1]).startsWith("Address: ");
        assertThat(lines[2]).startsWith("Number: ");
        assertThat(lines[3]).startsWith("Time created: ");
        assertThat(lines[4]).startsWith("Time last edit: ");
    }

    // Validate dates are printed in expected format
    @Test
    public void test_print_info_date_format() {
        // Given
        Organization org = new Organization("Test Org", "123 Main St", "+1234567890");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // When
        org.printInfo();

        // Then
        String output = outContent.toString();
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        assertTrue(output.contains("Time created: " + org.getCreatedDate().toString()));
        assertTrue(output.contains("Time last edit: " + org.getLastEditedDate().toString()));
        assertTrue(org.getCreatedDate().toString().matches(datePattern));
        assertTrue(org.getLastEditedDate().toString().matches(datePattern));
    }
}
