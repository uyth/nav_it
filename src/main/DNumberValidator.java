package main;

public class DNumberValidator extends NationalIDValidator implements IDValidator {

    private final int SHIFT_INDEX = 0;
    private final int SHIFT_VALUE = 4;

    public DNumberValidator() {
        super();
    }

    @Override
    public boolean isValidDate(String id) {
        // remove shift on first digit in DNumber
        String unShiftedID = super.unShiftID(id, SHIFT_INDEX, SHIFT_VALUE);
        return super.isValidDate(unShiftedID);
    }
}
