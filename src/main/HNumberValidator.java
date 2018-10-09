package main;

public class HNumberValidator extends NationalIDValidator implements IDValidator {

    private final int SHIFT_INDEX = 2;
    private final int SHIFT_VALUE = 4;

    public HNumberValidator() {
        super();
    }

    @Override
    public boolean isValidDate(String id) {
        // remove shift on first digit in DNumber
        String unShiftedID = super.unShiftID(id, SHIFT_INDEX, SHIFT_VALUE);
        return super.isValidDate(unShiftedID);
    }
}
