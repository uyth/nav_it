import main.DNumberValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class DNumberValidatorTest {

    private DNumberValidator validator = new DNumberValidator();

    @Test
    public void validateDNumber() {
        // a valid National ID, but not a valid D-Number
        assertFalse(validator.validateNationalID("11063326641"));
        // 11063326641 shifted
        assertTrue(validator.validateNationalID("51063326635"));
    }
}