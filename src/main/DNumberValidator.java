package main;

public class DNumberValidator extends NationalIDValidator implements INationalIDValidator {

    public DNumberValidator() {
        super();
    }

    @Override
    public boolean isValidDate(String nationalID) {
        // remove shift on first digit in DNumber
        int shiftIndex = 0;
        int shiftValue = 4;
        String unShiftedNationalID = super.unShiftNationalID(nationalID, shiftIndex, shiftValue);
        return super.isValidDate(unShiftedNationalID);
    }
}
