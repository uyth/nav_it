package main;

public abstract class AbstractIDValidator implements IDValidator {

    @Override
    // check if all constraints are ok
    public boolean validateID(String id) {
        return isValidCharacters(id)
                && isValidDate(id)
                && isValidIndividualNumber(id)
                && isValidChecksum(id);
    }

    abstract public boolean isValidCharacters(String id);

    abstract public boolean isValidDate(String id);

    abstract public boolean isValidIndividualNumber(String id);

    abstract public boolean isValidChecksum(String id);

    protected String unShiftID(String id, int shiftIndex, int shiftValue) {

        int shiftDigit = Character.getNumericValue(id.charAt(shiftIndex));
        String unShiftedDigit = String.valueOf(shiftDigit-shiftValue);
        StringBuilder unShiftedID = new StringBuilder();
        for (int i = 0; i < id.length(); i++) {
            if (i == shiftIndex) {
                unShiftedID.append(unShiftedDigit);
            } else {
                unShiftedID.append(id.charAt(i));
            }
        }
        return unShiftedID.toString();
    }
}
