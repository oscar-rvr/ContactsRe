package org.example.Model.Model2;
import org.example.Model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
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
}
