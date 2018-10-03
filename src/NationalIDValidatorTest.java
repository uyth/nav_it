import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class NationalIDValidatorTest {
    INationalIDValidator validator = NationalIDValidator.getInstance();

    @Test
    void validateNationalID() {
        // a valid ID
        assertTrue(validator.isValidCharacters("290995***REMOVED***"));
        // wrong checksum
        // the whole validation
        assertFalse(validator.validateNationalID("***REMOVED***"));
        assertFalse(validator.validateNationalID("***REMOVED***"));
        assertTrue(validator.validateNationalID("290995***REMOVED***"));
    }

    @Test
    void isValidCharacters() {
        // 11 char which is numbers -> correct
        assertTrue(validator.isValidCharacters("12345678910"));
        // only letters -> false
        assertFalse(validator.isValidCharacters("abcdefghij"));
        // wrong length
        assertFalse(validator.isValidCharacters("1234567890"));
        assertFalse(validator.isValidCharacters("1234567891012"));
        // characters inbetween
        assertFalse(validator.isValidCharacters("290995a0***REMOVED***"));
        // whitespace -> false
        assertFalse(validator.isValidCharacters(" 290995***REMOVED***"));
    }

    @Test
    void isValidDate() {
        // valid
        assertTrue(validator.isValidDate("290995***REMOVED***"));
        assertTrue(validator.isValidDate("280295***REMOVED***"));
        // date > 31
        assertFalse(validator.isValidDate("320995***REMOVED***"));
        // date not a valid one
        assertFalse(validator.isValidDate("300295***REMOVED***"));
    }

    @Test
    void isValidIndividualNumber() {
        // year [xx00, xx39] -> [000, 999]
        assertTrue(validator.isValidIndividualNumber("290900***REMOVED***"));
        assertTrue(validator.isValidIndividualNumber("29091280***REMOVED***"));
        assertTrue(validator.isValidIndividualNumber("320939***REMOVED***"));

        // year [xx40, xx53] -> [000, 499]+[900, 999]
        assertFalse(validator.isValidIndividualNumber("29094550***REMOVED***"));
        assertTrue(validator.isValidIndividualNumber("290945***REMOVED***"));
        assertTrue(validator.isValidIndividualNumber("29094590***REMOVED***"));

        // year [xx54, xx99] -> [000, 749]+[900, 999]
        assertTrue(validator.isValidIndividualNumber("290995***REMOVED***"));
        assertTrue(validator.isValidIndividualNumber("300255***REMOVED***"));
        assertFalse(validator.isValidIndividualNumber("300295***REMOVED***"));
        assertFalse(validator.isValidIndividualNumber("29095480***REMOVED***"));
    }

    @Test
    void isCorrectChecksum() {
        // correct
        assertTrue(validator.isCorrectChecksum("290995***REMOVED***"));
        // false
        assertFalse(validator.isCorrectChecksum("290995***REMOVED***"));
        assertFalse(validator.isCorrectChecksum("290995***REMOVED***"));
        assertFalse(validator.isCorrectChecksum("290995***REMOVED***"));
    }
}