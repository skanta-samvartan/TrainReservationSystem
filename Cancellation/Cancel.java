package Cancellation;

import java.util.Scanner;
import java.util.*;
import PersonPackage.*;
import TrainPackage.*; // Import the TrainPackage to access the Train classes
import bookingPackage.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class Cancel implements WalletOperations, Serializable {
    Person person;
    Scanner scan;
    ArrayList<Train[]> trains;
    Train[] currTrain;
    private static final long serialVersionUID = 1L;

    public Cancel(Person person, ArrayList<Train[]> trains) {

        
        this.person = person;
        this.scan = new Scanner(System.in);
        this.trains = trains;

    }

    public void increaseSeat(Ticket ticket, Train[] train) {
        if (ticket.seatCategory == 1) {
            ((FirstClass) train[1]).firstClassCapacity += 1;
        } else if (ticket.seatCategory == 2) {
            ((SecondClass) train[2]).secondClassCapacity += 1;
        } else {
            ((ThirdClass) train[3]).thirdClassCapacity += 1;
        }
    }

    
    public void cancel() {
        System.out.println("Do you want to cancel the booking? (1 for yes / 0 for no)");
        int cancelChoice;
        try {
            cancelChoice = scan.nextInt();
            scan.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter either 1 or 0.");
            scan.nextLine(); // Consume the invalid input
            return;
        }

        if (cancelChoice == 1) {
            System.out.println("Enter the train number to cancel the booking:");
            String trainNumber = scan.nextLine();

            for (Train[] train : trains) {
                if (train[0].train_no.equals(trainNumber)) {
                    currTrain = train; // Assigning the current train object to the train variable

                    System.out.println("Enter the number of tickets to cancel:");
                    int no_of_seats;
                    try {
                        no_of_seats = scan.nextInt();
                        scan.nextLine(); // Consume the newline character
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scan.nextLine(); // Consume the invalid input
                        return;
                    }

                    for (int i = 0; i < no_of_seats; i++) {
                        System.out.println("Enter the ticket number to cancel the ticket:");
                        String ticketNumber = scan.nextLine();

                        for (Ticket ticket : person.ticketlist) {
                            if (ticket.ticketNumber.equals(ticketNumber)) {
                                System.out.println("Booking canceled successfully!");
                                System.out.println("\n");
                                increaseSeat(ticket, currTrain);
                                updateWallet(ticket.ticket_fair); 
                                checkBalance();                               
                                person.ticketlist.remove(ticket);
                                break;
                            }
                        }

                    }
                    Booking.writeSeatCategoryToFile(trains, "seatCategory.txt");

                    return;
                }
            }

            System.out.println("Train not found.");

        } else if (cancelChoice == 0) {
            System.out.println("Your booking has not been cancelled.");
        } else {
            System.out.println("Invalid choice. Please enter either 1 or 0.");
        }
    }


    public void updateWalletAmount(double newAmount) {
        person.wallet = newAmount;
        updateCredentialsFile(); // Call a method to update the credentials file with the new wallet amount
    }

     public void displayWalletStatus() {
        System.out.println("Balance Wallet Amount :" + this.person.wallet);
    }

    public double checkBalance() {
        return person.wallet;
    }

    private void updateCredentialsFile() {
        try {
            // Read all lines from the existing file
            File inputFile = new File("login/loginCredentials.txt");
            File tempFile = new File("login/loginCredentialsTemp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(person.getUsername())) {
                    // Update the line with the new wallet amount
                    String[] parts = line.split(",");
                    parts[4] = String.valueOf(person.getWallet());
                    line = String.join(",", parts);
                }
                writer.write(line + System.lineSeparator());
            }

            reader.close();
            writer.close();

            // Replace the existing file with the updated file
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // overidding the methods
    public boolean updateWallet(double fare) {
        if (this.person != null) {
            person.wallet += fare;
            System.out.println("Refund Successful !");
            displayWalletStatus();
            updateWalletAmount(this.person.wallet);
            return true;
        } else {
            return false;
        }
    }
}
