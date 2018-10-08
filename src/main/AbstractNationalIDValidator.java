package main;

public abstract class AbstractNationalIDValidator implements INationalIDValidator {

    @Override
    // check if all constraints are ok
    public boolean validateNationalID(String nationalID) {
        return isValidCharacters(nationalID)
                && isValidDate(nationalID)
                && isValidIndividualNumber(nationalID)
                && isValidChecksum(nationalID);
    }

    abstract public boolean isValidCharacters(String nationalID);

    abstract public boolean isValidDate(String nationalID);

    abstract public boolean isValidIndividualNumber(String nationalID);

    abstract public boolean isValidChecksum(String nationalID);

    protected String unShiftNationalID(String nationalID, int shiftIndex, int shiftValue) {

        int shiftDigit = Character.getNumericValue(nationalID.charAt(shiftIndex));
        String unShiftedDigit = String.valueOf(shiftDigit-shiftValue);
        StringBuilder unShiftedNationalID = new StringBuilder();
        for (int i = 0; i < nationalID.length(); i++) {
            if (i == shiftIndex) {
                unShiftedNationalID.append(unShiftedDigit);
            } else {
                unShiftedNationalID.append(nationalID.charAt(i));
            }
        }
        return unShiftedNationalID.toString();
    }
}
