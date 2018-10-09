package main;

public interface IDValidator {
    boolean validateID(String nationalID);
    boolean isValidCharacters(String nationalID);
    boolean isValidDate(String nationalID);
    boolean isValidIndividualNumber(String nationalID);
    boolean isValidChecksum(String nationalID);
}
