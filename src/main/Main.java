package main;

public class Main {

    public static void main(String[] args) {
        INationalIDValidator validator = NationalIDValidator.getInstance();
        boolean valid;
        String status;
        for (String arg : args) {
            valid = validator.validateNationalID(arg);
            status = valid ? "a valid" : "not a valid";
            System.out.println(arg + " is " + status + " national id.");
            if (!valid) {
                try {
                    if (!validator.isValidCharacters(arg)) {
                        throw new IllegalArgumentException("A national id must be 11 numbers");
                    } else if (!validator.isValidDate(arg)) {
                        throw new IllegalArgumentException(
                                "The first 6 characters must be in the form: " +
                                        "DDMMYY");
                    } else if (!validator.isValidIndividualNumber(arg)) {
                        throw new IllegalArgumentException(
                                "The given ID does not conform to the" +
                                " standards for person numbers");
                    } else if (!validator.isValidChecksum(arg)) {
                        throw new IllegalArgumentException("Checksum error");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            System.out.println("\n----------------------------------------\n");
        }
    }
}
