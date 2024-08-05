package bookingPackage;

import PersonPackage.*;
import java.io.Serializable;

public class Ticket implements Serializable {

  public String ticketNumber;
  public String trainName;
  public String trainNumber;
  public int seatCategory;
  Passenger passenger; // (var of type Passenger)
  public String start;
  public String end;
  public double ticket_fair;

  public Ticket() {
  }

  public Ticket(String ticketNumber,double ticket_fair, String trainName, String trainNumber, int seatCategory, Passenger passenger,
      String start, String end) {
    this.ticketNumber = ticketNumber;
    this.ticket_fair = ticket_fair;
    this.trainName = trainName;
    this.trainNumber = trainNumber;
    this.seatCategory = seatCategory;
    this.passenger = passenger;
    this.start = start;
    this.end = end;

  }

  public void print() {

    System.out.println("-".repeat(50));
    System.out.println("Ticket\n\n");

    System.out.println("|" + "Ticket Info :\n");
    System.out.println("|" + "Ticket No :" + ticketNumber );
    System.out.println("|" + "Train Number :" + trainNumber );
    System.out.println("|" + "Train Name :" + trainName);
    System.out.println("|" + "Ticket Fare :"+ ticket_fair);
    System.out.println("|" + "From :" + start );
    System.out.println("|" + "To :" + end );
    System.out.println("|" + "Seat Category :" + seatCategory);
    System.out.println("|" + "Passenger Info\n");
    System.out.println("|" + "Passenger name :" + passenger.passengerName );
    System.out.println("|" + "Passenger Age :" + passenger.passengerAge);
    System.out.println("|" + "Passenger Gender :" + passenger.passengerGender);
    System.out.println("-".repeat(50));

  }

}
