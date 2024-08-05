package PersonPackage;
import java.io.*;

public class Passenger extends Person implements Serializable{
  private static final long serialVersionUID = 1L;
  public String passengerName;
  public int passengerAge;
  public String passengerGender;
  public int category;
  public double fare;

  public Passenger(){}
  
  public Passenger(String passengerName, int passengerAge, String passengerGender, int category) {
    this.passengerName = passengerName;
    this.passengerAge = passengerAge;
    this.passengerGender = passengerGender;
    this.category = category;

  }
}