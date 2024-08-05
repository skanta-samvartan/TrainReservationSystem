package TrainPackage;
import java.io.*;
public class FirstClass extends Train implements ClassInfo,Serializable{

  public int firstClassCapacity;
  public int firstClassBaseFare;
  public double fine_amt;
  private static final long serialVersionUID = 1L;
  public FirstClass() {
  }

  public FirstClass(int firstClassCapacity, int firstClassBaseFare, double fine_amt) {

    this.firstClassCapacity = firstClassCapacity;
    this.firstClassBaseFare = firstClassBaseFare;
    this.fine_amt = fine_amt;

  }
  public int getCapacity() {
      return firstClassCapacity;
  }

  public int getBaseFare() {
      return firstClassBaseFare;
  }

  public double getFineAmount() {
      return fine_amt;
  }
  public String toString() {
    return "FirstClassCapacity : " + firstClassCapacity + "FirstClassBaseFair: " + firstClassBaseFare + "Fine_amt: " + fine_amt ;
  }
}