public interface INationalIDValidator {
    boolean validateNationalID(String nationalID);
    boolean isValidCharacters(String nationalID);
    boolean isValidDate(String nationalID);
    boolean isValidIndividualNumber(String nationalID);
    boolean isCorrectChecksum(String nationalID);
}
