import main.HNumberValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class HNumberValidatorTest {

    private HNumberValidator validator = new HNumberValidator();

    @Test
    public void validateHNumber() {
        // a valid National ID, but not a valid H-Number
        assertFalse(validator.validateNationalID("11063326641"));
        // 11063326641 shifted
        assertTrue(validator.validateNationalID("11463326624"));
    }
}