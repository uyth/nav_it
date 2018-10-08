package main;

public class HNumberValidator extends NationalIDValidator implements INationalIDValidator {

    public HNumberValidator() {
        super();
    }

    @Override
    public boolean isValidDate(String nationalID) {
        // remove shift on first digit in DNumber
        int shiftIndex = 2;
        int shiftValue = 4;
        String unShiftedNationalID = super.unShiftNationalID(nationalID, shiftIndex, shiftValue);
        return super.isValidDate(unShiftedNationalID);
    }
}
