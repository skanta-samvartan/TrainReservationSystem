package SearchingPackage;

import TrainPackage.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

public class Search implements Serializable {
    private static final long serialVersionUID = 1L;

    public Search() {
    }

    public static ArrayList<Train[]> search(String start, String end, ArrayList<Train[]> trains) {

        ArrayList<Train[]> matchingTrains = new ArrayList<>();
        for (Train[] train : trains) {
            LinkedList<Station> stations = train[0].stations;

            int startIndex = findStationIndex(stations, start);
            int endIndex = findStationIndex(stations, end);

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                matchingTrains.add(train);
            }
        }
        return matchingTrains;
    }

    private static int findStationIndex(LinkedList<Station> stations, String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).stationName.equals(stationName)) {
                return i;
            }
        }
        return -1; // Station not found in the route
    }
}
