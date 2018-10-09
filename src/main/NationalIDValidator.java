package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NationalIDValidator extends AbstractIDValidator implements IDValidator {

    private static NationalIDValidator ourInstance = new NationalIDValidator();
    private DateFormat dateFormat;

    public static NationalIDValidator getInstance() {
        return ourInstance;
    }

    protected NationalIDValidator() {
        dateFormat = new SimpleDateFormat("ddMMyy");
        dateFormat.setLenient(false);
    }

    @Override
    public boolean isValidCharacters(String id) {
        // only allow strings that are numbers and 11 characters long
        return id.matches("^[0-9]{11}");
    }

    @Override
    public boolean isValidDate(String id) {
        // check if valid date
        String date = id.substring(0, 6);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidIndividualNumber(String id) {
        // check personal number
        int individualNumber = Integer.valueOf(id.substring(6, 9));
        int year = Integer.valueOf(id.substring(4, 6));
        if (year < 40) {
            return true;
        } else if (year < 54) {
            return individualNumber <= 499 || individualNumber >= 900;
        } else {
            return individualNumber <= 749 || individualNumber >= 900;
        }
    }

    @Override
    public boolean isValidChecksum(String id) {
        boolean c1, c2;
        c1 = checkControlDigit(id,
                new int[]{3, 7, 6, 1, 8, 9, 4, 5, 2},
                Character.getNumericValue(id.charAt(9)));
        c2 = checkControlDigit(id,
                new int[]{5, 4, 3, 2, 7, 6, 5, 4, 3, 2},
                Character.getNumericValue(id.charAt(10)));
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
