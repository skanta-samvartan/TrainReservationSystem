package bookingPackage;
import TrainPackage.FirstClass;
import TrainPackage.SecondClass;
import TrainPackage.ThirdClass;
import TrainPackage.Train;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class FareCalculator implements Serializable{
  private static final long serialVersionUID = 1L;
  public double distance = 0.0;
  public double fare1 = 0.0;
  public double fare2 = 0.0;
  public double fare3 = 0.0;

  public FareCalculator() {}
  
  public double initializeDistance(String StationToFind) throws FileNotFoundException {
    
    File routesFile = new File("routes.txt");
    Scanner scanner = new Scanner(routesFile);

    while (scanner.hasNextLine()) {
      
      String line = scanner.nextLine();
      String[] stationDetails = line.split(",");
      
      for (int i = 0; i < stationDetails.length; i++) {
          String stationDetail = stationDetails[i];
          String[] parts = stationDetail.split("-");
          String stationName = parts[0];
          double dist = Double.parseDouble(parts[2]);
      
          if (stationName.equals(StationToFind)) {
              return dist;
          }
      }
    }
      
    return 0.0;
  }

  
  public void calculateFare(Train[] train, String start, String end) {
    double startDistance=0.0;
    double endDistance=0.0;
    
    try {
      
      startDistance = initializeDistance(start);
      endDistance = initializeDistance(end);
      
      
    } catch (FileNotFoundException e) {
      
      System.out.println("Error: file not found.");
      
    }

    this.distance = endDistance - startDistance;
    
    this.fare1 = ((FirstClass) train[1]).firstClassBaseFare + (this.distance * 0.7);
      
    this.fare2 = ((SecondClass) train[2]).secondClassBaseFare + (this.distance * 0.7);
      
    this.fare3 = ((ThirdClass) train[3]).thirdClassBaseFare + (this.distance * 0.7);
    
  }
}