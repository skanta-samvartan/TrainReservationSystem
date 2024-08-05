package bookingPackage;

import TrainPackage.*;
import java.util.*;
import PersonPackage.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;


public class Booking implements WalletOperations, Serializable {

    Scanner scan = new Scanner(System.in);
    private static final long serialVersionUID = 1L;
    public int trainNumber;
    public double totalFare = 0.0;
    public Train[] train;
    public Person person;
    public double fare1;
    public double fare2;
    public double fare3;
    public String start;
    public String end;
    public String trainNo;
    ArrayList<Train[]> Alltrains;
    ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    public Booking(Person person, String trainNo, ArrayList<Train[]> Alltrains, String start, String end) {
        this.person = person;
        this.trainNo = trainNo;
        this.Alltrains = Alltrains;
        this.start = start;
        this.end = end;

    }

    public String createTicketNo(Train[] trains, int category) {
        String categoryCode;

        if (category == 1) {
            categoryCode = "A";
        } else if (category == 2) {
            categoryCode = "B";
        } else {
            categoryCode = "C";
        }

        int capacity;
        if (category == 1) {
            capacity = ((FirstClass) trains[1]).firstClassCapacity;
        } else if (category == 2) {
            capacity = ((SecondClass) trains[2]).secondClassCapacity;
        } else {
            capacity = ((ThirdClass) trains[3]).thirdClassCapacity;
        }

        String ticketNo = trains[0].train_name + categoryCode + String.valueOf(capacity);

        return ticketNo;
    }

    public static void writeSeatCategoryToFile(ArrayList<Train[]> trains, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Train[] trainArray : trains) {
                Train train = trainArray[0];
                FirstClass firstClass = (FirstClass) trainArray[1];
                SecondClass secondClass = (SecondClass) trainArray[2];
                ThirdClass thirdClass = (ThirdClass) trainArray[3];

                String line = String.format("%s,%s,%s-%s,%s,%s-%s,%s,%s",
                        Integer.toString(firstClass.firstClassCapacity),
                        Integer.toString(firstClass.firstClassBaseFare), Double.toString(firstClass.fine_amt),
                        Integer.toString(secondClass.secondClassCapacity),
                        Integer.toString(secondClass.secondClassBaseFare), Double.toString(secondClass.fine_amt),
                        Integer.toString(thirdClass.thirdClassCapacity),
                        Integer.toString(thirdClass.thirdClassBaseFare), Double.toString(thirdClass.fine_amt));

                writer.write(line);
                writer.newLine();
            }
        }

        catch (IOException e) {
            System.err.println("Error writing to the seat category file: " + e.getMessage());
        }
    }

    public void reduceSeat(Passenger passenger) {

        if (passenger.category == 1) {
            ((FirstClass) train[1]).firstClassCapacity -= 1;
        } else if (passenger.category == 2) {
            ((SecondClass) train[2]).secondClassCapacity -= 1;
        } else {
            ((ThirdClass) train[3]).thirdClassCapacity -= 1;
        }

    }

    public void updateWalletAmount(double newAmount) {
        person.wallet = newAmount;
        updateCredentialsFile(); // Call a method to update the credentials file with the new wallet amount
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
        if (this.person != null && this.person.wallet >= fare) {
            System.out.println("balance: " + this.person.wallet);
            person.wallet -= fare;
            System.out.println("Payment successful. Remaining balance: " + this.person.wallet);
            updateWalletAmount(this.person.wallet);
            return true;
        } else {
            return false;
        }
    }

    public double checkBalance() {
        return person.wallet;
    }

    public void displayWalletStatus() {
        System.out.println("Balance Wallet Amount :" + this.person.wallet);
    }

    public void book(ArrayList<Train[]> matchingTrains, double fare1, double fare2, double fare3) {

        int findCtrl = 0;
        int paymentCtrl = 0;

        for (Train[] tempObj : matchingTrains) {

            if (tempObj[0].train_no.equals(trainNo)) {
                train = tempObj;
                findCtrl = 1;
                break;
            }
        }

        if (findCtrl == 1) {

            System.out.println("Enter the no of tickets to be reserved : ");
            int noOfTickets = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < noOfTickets; i++) {

                System.out.println("\n");
                System.out.println("Enter the name of passenger : ");
                String name = scan.nextLine();

                System.out.println("Enter the age of passenger : ");
                int age = scan.nextInt();
                scan.nextLine();

                System.out.println("Enter the gender of passenger : ");
                String gender = scan.nextLine();

                System.out.println(
                        "Enter the category of passenger\n1.Enter 1 for First class\n2. Enter 2 for Second class\n3. Enter 3 for Third class");
                System.out.println("Enter the category of passenger(1/2/3):");

                int category = scan.nextInt();
                scan.nextLine();

                int ctrl = 0;

                switch (category) {
                    case 1:
                        ctrl = (((FirstClass) train[1]).firstClassCapacity > 0) ? 1 : 0;
                        break;
                    case 2:
                        ctrl = (((SecondClass) train[2]).secondClassCapacity > 0) ? 1 : 0;
                        break;
                    case 3:
                        ctrl = (((ThirdClass) train[3]).thirdClassCapacity > 0) ? 1 : 0;
                        break;
                    default:
                        System.out.println("Invalid category");
                        System.out.println("Re enter the details....");
                        i--; // to fill the details of the same person;
                }
                if (ctrl == 1) { // we will be only adding in to the list if the seat availablity of that
                                 // particular seatcategory is greater than 0;
                    Passenger passenger = new Passenger(name, age, gender, category);
                    passengers.add(passenger);
                }

                else {
                    System.out.println("\n\n Sorry! Seat is not available \nTry Again Later\n\n");
                }
            }

            FareCalculator fareCalculator = new FareCalculator();

            // Here we are calculating individual fair for the passengers.
            for (Passenger passenger : passengers) {
                if (passenger.category == 1) {
                    passenger.fare = fare1;
                    this.totalFare += fare1;
                } else if (passenger.category == 2) {
                    passenger.fare = fare2;
                    this.totalFare += fare2;
                } else {
                    passenger.fare = fare3;
                    this.totalFare += fare3;
                }
            }

            // Prompt the person to pay for the tickets..

            System.out.println("Total fare : " + this.totalFare);
            if (person.wallet >= this.totalFare) {

                System.out.println("\n");
                System.out.println("Money Available in wallet : " + person.wallet);
                System.out.println("Do you want to pay for the tickets using wallet ? (1 for yes /0 for no)");

                int choice = scan.nextInt();
                scan.nextLine();

                if (choice == 1) {

                    if (updateWallet(this.totalFare)) {
                        System.out.println("Ticket booked successfully");
                        System.out.println("\n");
                        paymentCtrl = 1;
                    } else {
                        System.out.println("Insufficient fund in wallet. Payment failed.");
                    }
                } else {
                    System.out.println("Rs." + this.totalFare + " paid successfully");
                    paymentCtrl = 1;
                }
            }

            else {
                System.out.println("Rs." + this.totalFare + " paid successfully");
                paymentCtrl = 1;
            }

            // ticket generation after paymentCtrl=1
            if (paymentCtrl == 1) {

                for (Passenger passenger : passengers) {

                    String ticketNo = createTicketNo(train, passenger.category);
                    Ticket ticket;


                    if (passenger.category == 1) {
                        ticket = new Ticket(ticketNo,fare1,train[0].train_name, train[0].train_no, passenger.category,passenger, start, end);
                    } else if (passenger.category == 2) {
                        ticket = new Ticket(ticketNo,fare2,train[0].train_name, train[0].train_no, passenger.category,passenger, start, end);
                    } else {
                        ticket = new Ticket(ticketNo,fare3,train[0].train_name, train[0].train_no, passenger.category,passenger, start, end);
                    }
                    person.ticketlist.add(ticket);
                    reduceSeat(passenger);// we are reducing the seat occupied by that passenger.
                }

                train[0].passengers.add(person); // We are adding person object to the ArrayList inside the Train Class
                                                 // to have the collection of entire ticket booked in the Train Class.

                writeSeatCategoryToFile(Alltrains, "seatCategory.txt"); // writing the seat category to the file..);
            }
        } else {
            System.out.println("Invalid train number");
        }
    }

}