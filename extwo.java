import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class extwo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user data (lastname firstname middlename birth_date phone_number gender):");
        String input = scanner.nextLine();
        scanner.close();

        String[] dataParts = input.split("\\s+");
        if (dataParts.length != 6) {
            System.out.println("Error: Invalid number of data parts. Expected 6, found " + dataParts.length);
            return;
        }

        String lastName = dataParts[0];
        String firstName = dataParts[1];
        String middleName = dataParts[2];
        String birthDateString = dataParts[3];
        String phoneNumberString = dataParts[4];
        char gender = dataParts[5].charAt(0);

        try {
            validateBirthDate(birthDateString);
            validatePhoneNumber(phoneNumberString);
            validateGender(gender);
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastName + ".txt", true))) {
            writer.write(String.format("%s %s %s %s %s %c%n", lastName, firstName, middleName, birthDateString, phoneNumberString, gender));
        } catch (IOException e) {
            System.out.println("Error writing to file:");
            e.printStackTrace();
        }
    }

    private static void validateBirthDate(String birthDateString) throws InvalidDataException {
        try {
            DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(birthDateString);
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Invalid birth date format. Expected dd.mm.yyyy");
        }
    }

    private static void validatePhoneNumber(String phoneNumberString) throws InvalidDataException {
        try {
            long phoneNumber = Long.parseUnsignedLong(phoneNumberString);
            if (phoneNumberString.length() != 11 || phoneNumber == 0) {
                throw new InvalidDataException("Invalid phone number format. Expected 10-digit unsigned number without formatting");
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid phone number format. Expected 10-digit unsigned number without formatting");
        }
    }

    private static void validateGender(char gender) throws InvalidDataException {
        if (gender != 'f' && gender != 'm') {
            throw new InvalidDataException("Invalid gender. Expected 'f' or 'm'");
        }
    }

    private static class InvalidDataException extends Exception {
        public InvalidDataException(String message) {
            super(message);
        }
    }
}
