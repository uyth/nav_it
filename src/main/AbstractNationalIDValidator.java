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
}
