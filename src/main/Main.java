package main;

import java.util.Scanner;

public class Main {

    private void printInitMessage() {
        System.out.println("\n----------------------------------------\n");
        System.out.println("This is a simple National ID validator");
        System.out.println("To exit type \"exit\"");
    }

    private void validate(String arg, IDValidator validator) {
        boolean valid = validator.validateID(arg);
        String status = valid ? "a valid" : "not a valid";
        System.out.println("\""+arg + "\" is " + status + " national id.");
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

    public static void main(String[] args) {
        IDValidator validator = NationalIDValidator.getInstance();
        Main main = new Main();
        if (args.length == 0) {
            main.printInitMessage();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Validate National ID \n> ");
                if (scanner.hasNext()) {
                    String token = scanner.next();
                    if (token.toLowerCase().equals("exit")) {
                        break;
                    }
                    main.validate(token, validator);
                }
            }
        } else {
            for (String arg : args) {
                main.validate(arg, validator);
            }
        }
        System.out.println("Exits program!");
    }
}
