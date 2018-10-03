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
    public boolean validateNationalID(String nationalID) {
        nationalID = nationalID.trim();
        if (isValidCharacters(nationalID)
                && isValidDate(nationalID)
                && isValidIndividualNumber(nationalID)
                && isCorrectChecksum(nationalID)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidCharacters(String nationalID) {
        return nationalID.matches("^[0-9]{11}");
    }

    @Override
    public boolean isValidDate(String nationalID) {
        // check if valid date
        String data = nationalID.substring(0, 6);
        try {
            dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
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
            // nothing
        } else if (year < 54) {
            if ((individualNumber > 499) && (individualNumber < 900)) {
                return false;
            }
        } else {
            if (individualNumber > 749 && individualNumber < 900) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCorrectChecksum(String nationalID) {
        // check control digit 1
        if(!isValidChecksum(nationalID,
                new int[]{3, 7, 6, 1, 8, 9, 4, 5, 2},
                nationalID.charAt(9))) return false;
        // check control digit 2
        if(!isValidChecksum(nationalID,
                new int[]{5, 4, 3, 2, 7, 6, 5, 4, 3, 2},
                nationalID.charAt(10))) return false;
        return true;
    }

    private boolean isValidChecksum(String nationalID, int[] toMultiPly, char checkSum) {
        // check checksum
        int sum = 0;
        for (int i = 0; i < toMultiPly.length; i++) {
            sum += toMultiPly[i] * Character.getNumericValue(nationalID.charAt(i));
        }
        // only valid if computed number equals checksum digit
        // or if computed number is 11 -> checksum digit == 0
        return (11 - (sum % 11)) % 11 == Character.getNumericValue(checkSum);
    }
}
