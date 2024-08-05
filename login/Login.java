package login;

import java.io.*;
import java.util.Scanner;

public class Login {
    public String userName;
    public String password;

    public int login() {
        try {
            File file = new File("login/loginCredentials.txt");
            Scanner scan = new Scanner(System.in);
            Scanner fileObj = new Scanner(file);

            System.out.println("\n");
            System.out.println("**********************************");
            System.out.println("************ LOGIN PAGE **********");
            System.out.println("**********************************");

            System.out.print("\nEnter the username: ");
            userName = scan.nextLine();
            System.out.print("Enter the password: ");
            password = scan.nextLine();

            StringBuilder updatedContent = new StringBuilder();

            while (fileObj.hasNextLine()) {
                String line = fileObj.nextLine();
                String[] arr = line.split(",");

                if (userName.equals(arr[0]) && password.equals(arr[1])) {
                    System.out.println("\n**********************************");
                    System.out.println("***** You Logged in successfully *");
                    System.out.println("**********************************");
                    System.out.println();
                    return 1;
                }

                if (userName.equals(arr[0])) {
                    System.out.println("\nUser found but invalid password..");
                    System.out.println("\nEnter 1 to reset password:");

                    int reset = scan.nextInt();
                    scan.nextLine();

                    if (reset == 1) {
                        System.out.print("Enter the password reset code provided to you or Enter your old Password: ");
                        String code = scan.nextLine();

                        if (code.equals(arr[3]) || code.equals(arr[1])) {
                            String newPassword;
                            String newPassword1;

                            do {
                                System.out.print("Enter the New Password: ");
                                newPassword = scan.nextLine();

                                System.out.print("Reenter the New Password: ");
                                newPassword1 = scan.nextLine();

                                if (newPassword.equals(newPassword1)) {
                                    System.out.println("**********************************");
                                    System.out.println("***** Password reset successfully ****");
                                    System.out.println("**********************************");
                                    arr[1] = newPassword; // Update the password in the array
                                } else {
                                    System.out.println("Passwords do not match. Try again.");
                                }
                            } while (!newPassword.equals(newPassword1));
                        }
                    }

                    updatedContent.append(String.join(",", arr)).append("\n");
                } else {
                    updatedContent.append(line).append("\n");
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(updatedContent.toString().trim());
            }

            System.out.println("\n**********************************");
            System.out.println("***** Entered UserName or password is wrong ****");
            System.out.println("**********************************");
            return 0;
        } catch (IOException e) {
            System.out.println("Problem in opening the file or error reading/writing to the file");
            return 0;
        }
    }

    public String getUserName() {
        return userName;
    }
}
