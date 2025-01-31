import static org.mockito.Mockito.*;

import org.example.Model.AbstractRecord;
import org.junit.jupiter.api.*;

public class AbstractRecordTest {

    private AbstractRecord record;

    @BeforeEach
    public void setup() {
        record = mock(AbstractRecord.class);
    }

    @Test
    public void testSetPhoneNumber() {
        record.setPhoneNumber("123456789");
        verify(record, times(1)).setPhoneNumber("123456789");
    }

    @Test
    public void testIsNotValidNumber() {

        Assertions.assertFalse(record.isValidNumber("as12345()()"));
    }

}
