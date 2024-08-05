package TrainPackage;
import java.io.*;

public class ThirdClass extends Train implements ClassInfo,Serializable {
  private static final long serialVersionUID = 1L;
  public int thirdClassCapacity;
  public int thirdClassBaseFare;
  // ArrayList<Tickets> thirdClassTickets = new ArrayList<Tickets>();
  public double fine_amt;

  public ThirdClass() {
  }

  public ThirdClass(int thirdClassCapacity, int thirdClassBaseFare, double fine_amt) {
    this.thirdClassCapacity = thirdClassCapacity;
    this.thirdClassBaseFare = thirdClassBaseFare;
    this.fine_amt = fine_amt;
  }
  public int getCapacity() {
      return thirdClassCapacity;
  }

  public int getBaseFare() {
      return thirdClassBaseFare;
  }
  
  public double getFineAmount() {
      return fine_amt;
  }
  public String toString() {
    return " ThirdClassCapacity: " + thirdClassCapacity  + " ThirdClassBaseFair: " + thirdClassBaseFare + " Fine_amt: " + fine_amt;
  }
}