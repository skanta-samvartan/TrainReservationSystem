package TrainPackage;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Station implements Serializable{
  private static final long serialVersionUID = 1L;
  public String stationName;
  public LocalTime arrivalTime;
  public double distance;

  public Station(String stationName, String arrivalTime, double distance) {

    this.stationName = stationName;

    DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("HH:mm");
    this.arrivalTime = LocalTime.parse(arrivalTime, formatObj);
    this.distance = distance;    
  }
}
