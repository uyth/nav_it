import main.INationalIDValidator;
import main.NationalIDValidator;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

class NationalIDValidatorTest {
    private INationalIDValidator validator = NationalIDValidator.getInstance();

    @Test
    void validateNationalID() {
        assertTrue(validator.validateNationalID("290995***REMOVED***"));
        assertFalse(validator.validateNationalID("***REMOVED***"));
        assertFalse(validator.validateNationalID("***REMOVED***"));
        assertFalse(validator.validateNationalID("12345678910"));
        assertFalse(validator.validateNationalID("abcdefghij"));
        assertFalse(validator.validateNationalID("***REMOVED***"));
        assertFalse(validator.validateNationalID("290995***REMOVED***0"));
        assertFalse(validator.validateNationalID("290995a0***REMOVED***"));

        // national numbers fetched from
        // http://www.fnrinfo.no/Verktoy/FinnLovlige_Tilfeldig.aspx
        List<String> randomNationalIDs = Arrays.asList(
                "11063326641", "06094814403", "31038238798", "22011788010",
                "08042035409", "03087348082", "20051302851", "09120318281",
                "14061439561", "14041279681", "30016214937", "18064332321",
                "10118613908", "02061841272", "08074749365", "09080811370",
                "15093449085", "05048926683", "23110599994", "29123600779",
                "16034905014", "19040689918", "13058247469", "11040020006",
                "08028508027", "25019932381", "04036920675", "29073216379",
                "30120872806", "28030913002", "24102318990", "24098345953",
                "15054231358", "13126604860", "01125034795", "09040841722",
                "08011677135", "12103625171", "06017620205", "09048948909",
                "23051169549", "15087424429", "25088731487", "18035307873",
                "09040532762", "24075719562", "09100589157", "06036337689",
                "05094641626", "27061660580", "24100955268", "25087806459",
                "23091876884", "23043927248", "16091804003", "20079533913",
                "15080085238", "14060626546", "01120787841", "02037023038",
                "31035910347", "20117804713", "10064708428", "04080604861",
                "31016513620", "01041337571", "02108533687", "22098704438",
                "06011566128", "20032314873", "22049024081", "21128717002",
                "28041374138", "01084737751", "23070616505", "18014220549",
                "16080205701", "04020731007", "16097607420", "19096010993",
                "05089856568", "07041567273", "23041641312", "13063736060",
                "24039866907", "16115820633", "07093408904", "14086727138",
                "22016725432", "19039130974", "21071118581", "05109129285",
                "22107443723", "07038214661", "15126009558", "09074439117",
                "17101053091", "14039940303", "04049447820", "05112218957");
        for (String nationalID : randomNationalIDs) {
            assertTrue(validator.validateNationalID(nationalID));
        }
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
        // letters in between
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
        assertTrue(validator.isValidChecksum("290995***REMOVED***"));
        // false
        assertFalse(validator.isValidChecksum("290995***REMOVED***"));
        assertFalse(validator.isValidChecksum("290995***REMOVED***"));
        assertFalse(validator.isValidChecksum("290995***REMOVED***"));
    }
}