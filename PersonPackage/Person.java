package PersonPackage;

import java.util.*;
import bookingPackage.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.EOFException;
import java.io.*;

public class Person implements Serializable {

    public String name;
    public int age;
    public String ph_no;
    public String email;
    public String aadhar;
    public ArrayList<Ticket> ticketlist = new ArrayList();

    public int ticketCount = ticketlist.size();
    public double wallet;

    public Person() {
    }

    public Person(String name, int age, String ph_no, String email, String aadhar, double wallet) {
        this.name = name;
        this.age = age;
        this.ph_no = ph_no;
        this.email = email;
        this.aadhar = aadhar;
        this.wallet = wallet;
        writeToFile();
    }

    public void writeToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("PersonPackage/person.txt", true))) {
            String line = String.format("%s,%d,%s,%s,%s,%.2f,%s", // Added %s for ticketNumber
                    this.name, this.age, this.ph_no, this.email, this.aadhar, this.wallet, getTicketNumbers());

            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to get comma-separated ticket numbers
    private String getTicketNumbers() {
        StringBuilder ticketNumbers = new StringBuilder();
        for (Ticket ticket : ticketlist) {
            ticketNumbers.append(ticket.ticketNumber).append(",");
        }
        return ticketNumbers.toString();
    }

    public void displayTicket() {
        if (ticketlist.size() == 0) {
            System.out.println("No tickets booked");
        }
        for (Ticket ticket : ticketlist) {
            ticket.print();
        }

    } // displays details of all the tickets that the person has booked.

    public void cancelTicket() {
        Scanner scan = new Scanner(System.in);
        String TicketNo;

        displayTicket();
        TicketNo = scan.nextLine();

    } // in here call displayTicket() method first.Then get ticketNumber as input.
      // Then, using input we will identify corresponding ticket object(which has same
      // ticketNumber).Using ticket object, we will identify the category of seat.Then
      // calculate fine amount based on category.(display fine in wallet).

    public static void writeListToFile(ArrayList<Person> persons) {
        try {
            FileOutputStream fos = new FileOutputStream("PersonPackage/personData.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // In Person class
    public String getUsername() {
        return this.name;
    }

    public double getWallet() {
        return this.wallet;
    }

    public static ArrayList<Person> readListFromFile() {
        File file = new File("PersonPackage/personData.ser");

        if (!file.exists()) {
            System.out.println("File is empty. Returning empty list.");
            return new ArrayList<>();
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (ArrayList<Person>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Returning empty list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
