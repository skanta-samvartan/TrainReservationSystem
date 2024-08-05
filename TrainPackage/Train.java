package TrainPackage;

import PersonPackage.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.EOFException;
import java.io.*;


public class Train implements Serializable{
  private static final long serialVersionUID = 1L;
  public String train_no;
  public String train_name;
  public LocalDateTime dateTime;  
  public LinkedList<Station> stations = new LinkedList<Station>();
  public ArrayList<Person> passengers = new ArrayList<Person>();

  public Train() {
  }

  public Train(String train_no, String train_name, String date) {

    this.train_no = train_no;
    this.train_name = train_name;
    DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    this.dateTime= LocalDateTime.parse(date, formatObj);   

  }

  public String toString() {
    return "Train No: " + train_no + " Train Name: " + train_name + " Date: " + dateTime;
  }

  public void displayRoutes() {
    
    System.out.println("\nTRAIN SCHEDULE:");
    for (Station s : stations) {
      System.out.println(s.stationName + " " + s.arrivalTime );
    }
    
  }

  public static void writeListToFile(ArrayList<Train[]> trains)  throws Exception{

    
    try {
        FileOutputStream fos =new FileOutputStream("TrainPackage/trainData.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(trains);
        oos.close();
        fos.close();
    }catch (FileNotFoundException e) {
          System.out.println("File not found. Returning empty list.");
    } 
    catch(IOException e) {
        e.printStackTrace();
    }      
  }

  public static ArrayList<Train[]> readListFromFile() throws Exception{
      try{
          FileInputStream fos =new FileInputStream("TrainPackage/trainData.ser");
          ObjectInputStream ois = new ObjectInputStream(fos);
          return (ArrayList<Train[]>) ois.readObject();
      } catch (FileNotFoundException e) {
          System.out.println("File not found. Returning empty list.");
      } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
      }
      return new ArrayList<>();
  }
}
