import main.AbstractNationalIDValidator;
import main.NationalIDValidator;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

class NationalIDValidatorTest {
    private AbstractNationalIDValidator validator = NationalIDValidator.getInstance();

    @Test
    void validateNationalID() {
        assertTrue(validator.validateNationalID("11063326641"));
        assertFalse(validator.validateNationalID("11063326631"));
        assertFalse(validator.validateNationalID("11063326642"));
        assertFalse(validator.validateNationalID("12345678910"));
        assertFalse(validator.validateNationalID("abcdefghij"));
        assertFalse(validator.validateNationalID("1106332664"));
        assertFalse(validator.validateNationalID("110633266410"));
        assertFalse(validator.validateNationalID("110633a26641"));

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
        assertFalse(validator.isValidCharacters("110633a26641"));
        // whitespace -> false
        assertFalse(validator.isValidCharacters(" 11063326641"));
    }

    @Test
    void isValidDate() {
        // valid dates
        assertTrue(validator.isValidDate("11063326641"));
        assertTrue(validator.isValidDate("28023326641"));
        // date > 31
        assertFalse(validator.isValidDate("32099526641"));
        // date < 1
        assertFalse(validator.isValidDate("00099526641"));
        // date not valid for month
        assertFalse(validator.isValidDate("30029526641"));
        // month > 12
        assertFalse(validator.isValidDate("01139526641"));
        // month < 1
        assertFalse(validator.isValidDate("01009526641"));
    }

    @Test
    void isValidIndividualNumber() {
        // year [xx00, xx39] -> [000, 999]
        assertTrue(validator.isValidIndividualNumber("11063326641"));
        assertTrue(validator.isValidIndividualNumber("29091280389"));
        assertTrue(validator.isValidIndividualNumber("32093940123"));

        // year [xx40, xx53] -> [000, 499]+[900, 999]
        assertFalse(validator.isValidIndividualNumber("29094556641"));
        assertTrue(validator.isValidIndividualNumber("29094546641"));
        assertTrue(validator.isValidIndividualNumber("29094596641"));

        // year [xx54, xx99] -> [000, 749]+[900, 999]
        assertTrue(validator.isValidIndividualNumber("29099526641"));
        assertTrue(validator.isValidIndividualNumber("30025526641"));
        assertFalse(validator.isValidIndividualNumber("30029575049"));
        assertFalse(validator.isValidIndividualNumber("29095480589"));
    }

    @Test
    void isCorrectChecksum() {
        // correct
        assertTrue(validator.isValidChecksum("11063326641"));
        // shift 1st control digit
        assertFalse(validator.isValidChecksum("11063326651"));
        assertFalse(validator.isValidChecksum("11063326661"));
        assertFalse(validator.isValidChecksum("11063326671"));
        assertFalse(validator.isValidChecksum("11063326681"));
        assertFalse(validator.isValidChecksum("11063326691"));
        assertFalse(validator.isValidChecksum("11063326601"));
        assertFalse(validator.isValidChecksum("11063326611"));
        assertFalse(validator.isValidChecksum("11063326621"));
        assertFalse(validator.isValidChecksum("11063326631"));
        // shift 2nd control digit
        assertFalse(validator.isValidChecksum("11063326642"));
        assertFalse(validator.isValidChecksum("11063326643"));
        assertFalse(validator.isValidChecksum("11063326644"));
        assertFalse(validator.isValidChecksum("11063326645"));
        assertFalse(validator.isValidChecksum("11063326646"));
        assertFalse(validator.isValidChecksum("11063326647"));
        assertFalse(validator.isValidChecksum("11063326648"));
        assertFalse(validator.isValidChecksum("11063326649"));
        assertFalse(validator.isValidChecksum("11063326640"));
    }
}