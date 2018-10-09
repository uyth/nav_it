import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NationalIDValidator implements INationalIDValidator {

    private static NationalIDValidator ourInstance = new NationalIDValidator();
    private DateFormat dateFormat;

    public static NationalIDValidator getInstance() {
        return ourInstance;
    }

    private NationalIDValidator() {
        dateFormat = new SimpleDateFormat("ddMMyy");
        dateFormat.setLenient(false);
    }

    @Override
    // check if all constraints are ok
    public boolean validateNationalID(String nationalID) {
        return isValidCharacters(nationalID)
                && isValidDate(nationalID)
                && isValidIndividualNumber(nationalID)
                && isValidChecksum(nationalID);
    }

    @Override
    public boolean isValidCharacters(String nationalID) {
        // only allow strings that are numbers and 11 characters long
        return nationalID.matches("^[0-9]{11}");
    }

    @Override
    public boolean isValidDate(String nationalID) {
        // check if valid date
        String data = nationalID.substring(0, 6);
        try {
            dateFormat.parse(data);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidIndividualNumber(String nationalID) {
        // check personal number
        int individualNumber = Integer.valueOf(nationalID.substring(6, 9));
        int year = Integer.valueOf(nationalID.substring(4, 6));
        if (year < 40) {
            return true;
        } else if (year < 54) {
            return individualNumber <= 499 || individualNumber >= 900;
        } else {
            return individualNumber <= 749 || individualNumber >= 900;
        }
    }

    @Override
    public boolean isValidChecksum(String nationalID) {
        boolean c1, c2;
        c1 = checkControlDigit(nationalID,
                new int[]{3, 7, 6, 1, 8, 9, 4, 5, 2},
                Character.getNumericValue(nationalID.charAt(9)));
        c2 = checkControlDigit(nationalID,
                new int[]{5, 4, 3, 2, 7, 6, 5, 4, 3, 2},
                Character.getNumericValue(nationalID.charAt(10)));
        return c1 && c2;
    }

    private boolean checkControlDigit(String nationalID, int[] toMultiPly, int checkSum) {
        // check checksum
        int sum = 0;
        for (int i = 0; i < toMultiPly.length; i++) {
            sum += toMultiPly[i] * Character.getNumericValue(nationalID.charAt(i));
        }
        // only valid if computed number equals checksum digit
        // or if computed number is 11 -> checksum digit == 0
        return (11 - (sum % 11)) % 11 == checkSum;
    }
}
