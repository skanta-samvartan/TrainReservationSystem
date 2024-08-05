package TrainPackage;
import java.io.*;

public class SecondClass extends Train implements ClassInfo,Serializable {

  public int secondClassCapacity;
  public int secondClassBaseFare;

  public double fine_amt;
  private static final long serialVersionUID = 1L;
  public SecondClass() {
  }

  public SecondClass(int secondClassCapacity, int secondClassBaseFare, double fine_amt) {

    this.secondClassCapacity = secondClassCapacity;
    this.secondClassBaseFare = secondClassBaseFare;
    this.fine_amt = fine_amt;

  }
  public int getCapacity() {
      return secondClassCapacity;
  }

  public int getBaseFare() {
      return secondClassBaseFare;
  }

  public double getFineAmount() {
      return fine_amt;
  }
  public String toString() {
    return "SecondClassCapacity: " + secondClassCapacity + " SecondClassBaseFare " + secondClassBaseFare + "Fine_amt: " + fine_amt;
  }
}
