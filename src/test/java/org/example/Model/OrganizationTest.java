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

    @Test
    void test_constructor_given_existing_contacts_when_loaded_then_phonebook_is_empty() {
        List<String> fields = organization.getFields();
        assertEquals(List.of("organizationName", "address", "number"), fields);
    }

    @Test
    void test_set_field_given_valid_field_when_set_field_then_field_is_updated() {
        organization.setField("organizationName", "New Tech");
        assertEquals("New Tech", organization.getOrganizationName());

        organization.setField("address", "456 Innovation Ave");
        assertEquals("456 Innovation Ave", organization.getAddress());

        organization.setField("number", "1234567890");
        assertEquals("1234567890", organization.getPhoneNumber());
    }

    @Test
    void test_set_field_given_invalid_field_when_set_field_then_no_changes() {
        organization.setField("invalid", "Some Value");
        assertEquals("Tech Corp", organization.getOrganizationName());
    }

    @Test
    void test_get_field_given_valid_field_when_get_field_then_return_correct_value() {
        assertEquals("Tech Corp", organization.getField("organizationName"));
        assertEquals("123 Tech Street", organization.getField("address"));
        assertEquals("9876543210", organization.getField("number"));
    }

    @Test
    void test_get_field_given_invalid_field_when_get_field_then_return_no_data() {
        assertEquals("[no data]", organization.getField("invalid"));
    }

    @Test
    void test_print_name_given_organization_when_print_name_then_return_organization_name() {
        assertEquals("Tech Corp", organization.printName());
    }

    @Test
    void test_set_organization_name_given_valid_name_when_set_then_name_is_updated() {
        Organization organization = new Organization("Old Name", "Address", "123456");
        String newName = "New Company Name";
        organization.setOrganizationName(newName);
        assertEquals(newName, organization.getOrganizationName());
    }

    @Test
    void test_set_organization_name_given_empty_name_when_set_then_name_is_empty() {
        Organization organization = new Organization("Test Org", "Address", "123456");
        String emptyName = "";
        organization.setOrganizationName(emptyName);
        assertEquals(emptyName, organization.getOrganizationName());
    }

    @Test
    void test_print_info_given_organization_when_print_info_then_fields_order_and_format_are_correct() {
        Organization org = new Organization("Test Org", "123 Main St", "+1234567890");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.printInfo();

        String output = outContent.toString();
        String[] lines = output.split(System.lineSeparator());
        assertThat(lines[0]).startsWith("Organization name: ");
        assertThat(lines[1]).startsWith("Address: ");
        assertThat(lines[2]).startsWith("Number: ");
        assertThat(lines[3]).startsWith("Time created: ");
        assertThat(lines[4]).startsWith("Time last edit: ");
    }

    @Test
    void test_print_info_given_organization_when_print_info_then_dates_are_in_expected_format() {
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