
package login;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Custom exception for invalid age format
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Custom exception for invalid Aadhar format
class InvalidAadharException extends Exception {
    public InvalidAadharException(String message) {
        super(message);
    }
}

// Custom exception for invalid email format
class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}

public class Signup {

    public String userName;
    public String password;
    public String email;
    public String recoveryPassword;
    public String phnNo, aadhar;
    public int age;
    public double wallet;

    public Signup() {
    }

    public Signup(String userName, String password, String email, String recoveryPassword) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.recoveryPassword = recoveryPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRecoveryPassword() {
        return recoveryPassword;
    }

    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    public boolean isValidAadhar(String aadhar) {
        return aadhar.matches("\\d{12}"); //Aadhar has 12 digits
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void signup() {
        try {
            FileWriter file = new FileWriter("login/loginCredentials.txt", true);

            Scanner scan = new Scanner(System.in);
            System.out.println("\nEnter the username: ");
            userName = scan.nextLine();
            System.out.println("\nEnter the password: ");
            password = scan.nextLine();

            // Validate age
            do {
                System.out.println("\nEnter your age: ");
                try {
                    age = scan.nextInt();
                    if (age <= 0) {
                        throw new InvalidAgeException("Age must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    throw new InvalidAgeException("Invalid age format. Please enter a valid age.");
                }
               scan.nextLine();
            } while (age <= 0);

            // Validate Aadhar
            do {
                System.out.println("\nEnter your Aadhar number(12 digit): ");
                aadhar = scan.nextLine();
                if (!isValidAadhar(aadhar)) {
                    throw new InvalidAadharException("Invalid Aadhar format(Must have 5 digits). Please enter a valid Aadhar number.");
                }
            } while (!isValidAadhar(aadhar));

            // Validate email
            do {
                System.out.println("\nEnter the email: ");
                email = scan.nextLine();

                if (!isEmailValid(email)) {
                    throw new InvalidEmailException("Invalid email format. Please provide a valid email.");
                }
            } while (!isEmailValid(email));

            System.out.println("\nEnter your phone number: ");
            phnNo = scan.nextLine();
            System.out.println("\nEnter your wallet amount: ");
            wallet = scan.nextDouble();
            scan.nextLine();

            recoveryPassword = generateRandomString(5);
            System.out.println("Please note down your account recovery code: " + recoveryPassword);
            System.out.println("\n\nPlease note down the above-generated password to reset your password later!");

            file.write(userName + "," + password + "," + email + "," + recoveryPassword + "," + wallet + "\n");
            scan.close();
            file.close();

            System.out.println("\nSignup successful.");

        } catch (IOException e) {
            System.out.println("Problem in opening the file");
        } catch (InvalidAgeException | InvalidAadharException | InvalidEmailException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
}


