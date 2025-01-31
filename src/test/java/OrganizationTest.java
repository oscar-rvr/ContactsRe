import org.example.Model.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrganizationTest {

    private Organization organization;

    @BeforeEach
    public void setup() {
        organization = new Organization("OrgName", "Address", "987654321");
    }

    @Test
    public void testGetField() {
        Assertions.assertEquals("OrgName", organization.getField("organizationName"));
        Assertions.assertEquals("Address", organization.getField("address"));
        Assertions.assertEquals("987654321", organization.getField("number"));
    }

    @Test
    public void testSetField() {
        organization.setField("organizationName", "NewOrgName");
        Assertions.assertEquals("NewOrgName", organization.getField("organizationName"));
    }

    @Test
    public void testPrintInfo() {
        organization.printInfo();
        Assertions.assertTrue(true);
    }
}
