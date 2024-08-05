import TrainPackage.*;
import login.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.*;
import java.util.*;
import java.io.IOException;
import bookingPackage.*;
import SearchingPackage.*;
import PersonPackage.*;
import Cancellation.*;
import Box.*;

public class Main {

    private static Person currPer;
    private static ArrayList<Person> persons = new ArrayList<>();
    private static ArrayList<Train[]> trains = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        // This Block of code is to read the data from the file and store it in the
        // ArrayList of Train[] and ArrayList of Person[]

        try {

            File trainFile = new File("TrainPackage/trainData.ser");
            File personFile = new File("PersonPackage/personData.ser");

            if (trainFile.exists() && trainFile.length() != 0) {
                trains = (ArrayList<Train[]>) (Train.readListFromFile());
            } else {
                System.out.println("There is no data in the file trainData.ser");
            }

            if (personFile.exists() && personFile.length() != 0) {
                persons = (ArrayList<Person>) (Person.readListFromFile());
            } else {
                System.out.println("There is no data in the file personData.ser");
            }

        }

        catch (Exception e) {
            System.out.println("There is a error in reading the file...\n");
        }

        if (trains.isEmpty()) {
            try {

                File fileObj = new File("trainDetails.txt");
                File routeObj = new File("routes.txt");
                File seatcategoryObj = new File("seatCategory.txt");

                Scanner scan1 = new Scanner(fileObj);
                Scanner route = new Scanner(routeObj);
                Scanner seatcategory = new Scanner(seatcategoryObj);

                while (scan1.hasNextLine() && route.hasNextLine() && seatcategory.hasNextLine()) {
                    Train[] traindetail = new Train[4];// Using this we will be storing train obj ,firstclass obj ,
                                                       // second and
                    // third class obj...

                    String line = scan1.nextLine();
                    String routes = route.nextLine();
                    String categoryDetails = seatcategory.nextLine();

                    String[] arr = line.split(",");// splitting the train related info's
                    String train_no = arr[0];
                    String train_name = arr[1];
                    String date = arr[2];

                    Train newObj = new Train(train_no, train_name, date);
                    traindetail[0] = newObj;// we are having the 0th element as the trainobj...

                    String[] arr2 = routes.split(",");// for splitting the train routes.

                    // after this we are further splitting the details like junction , arrival time
                    // and distance..
                    String[] arr3;

                    for (int i = 0; i < arr2.length; i++) {
                        arr3 = arr2[i].split("-");
                        (newObj.stations).add(new Station(arr3[0], arr3[1], Double.parseDouble(arr3[2]))); // we are
                                                                                                           // adding
                                                                                                           // each
                        // junction into the routes
                        // of the respective train
                        // obj.
                    }

                    // After this we are splitting the seat category details.
                    String[] arr4 = categoryDetails.split("-");

                    for (int i = 0; i < arr4.length; i++) {
                        String[] dup = arr4[i].split(",");

                        switch (i + 1) {
                            case 1:
                                traindetail[1] = new FirstClass(Integer.parseInt(dup[0]), Integer.parseInt(dup[1]),
                                        Double.parseDouble(dup[2]));
                                break;

                            case 2:
                                traindetail[2] = new SecondClass(Integer.parseInt(dup[0]),
                                        Integer.parseInt(dup[1]), Double.parseDouble(dup[2]));
                                break;

                            case 3:
                                traindetail[3] = new ThirdClass(Integer.parseInt(dup[0]), Integer.parseInt(dup[1]),
                                        Double.parseDouble(dup[2]));
                                break;
                            default:
                                System.out.println("Wrong file format..");
                        }
                    }

                    trains.add(traindetail); // Finally we are adding the train info with the respective first,second
                                             // and
                    // third class info as a array into the ArrayList.
                }
            } catch (FileNotFoundException e) {
                System.out.println("error occurred");
            }
        }
        // End of the data reading block....

        System.out.println("\n\n");
        System.out.println("Welcome to the Train Reservation System");

        int loginCtrl = 0;

        while (loginCtrl == 0) {

            System.out.println("-".repeat(50));
            System.out.println("\n");

            System.out.println("1. Enter 1 to Login\n2. Enter 2  to Sign Up\nEnter Your Choice :");
            int choice = scan.nextInt();

            System.out.println("\n");
            System.out.println("-".repeat(50));

            if (choice == 1) {

                // Creating Login Object....

                Login loginObj = new Login();
                loginCtrl = loginObj.login();

                if (loginCtrl == 1) {

                    for (Person person_ : persons) {
                        if (person_.getUsername().equals(loginObj.userName)) {

                            currPer = person_;
                            System.out.println("Current person: " + currPer.getUsername());
                            break;
                        }
                    }
                }
                
            } else if (choice == 2) {

                Signup signupObj = new Signup();
                signupObj.signup();
                currPer = new Person(signupObj.userName, signupObj.age, signupObj.phnNo, signupObj.email,
                        signupObj.aadhar, signupObj.wallet);

                persons.add(currPer);
                try {
                    Train.writeListToFile(trains);
                    Person.writeListToFile(persons);
                }

                catch (Exception e) {
                    System.out.println("error occurred in writting the objects to the file");
                }

                System.exit(0);
            } else {
                System.exit(0);
            }
        }
        // After logging in :
        System.out.println("\n\n");
        System.out.println(
                "1. Enter 1 to Search for a Train and also to Book Ticket\n2. Enter 2 to cancel Ticket\n3. Enter 3 to Display the ticket.\n4. Enter 4 to Exit\n5. Enter Your Choice :");
        int user_ctrl = scan.nextInt();

        ArrayList<Train[]> matchingTrains;
        Scanner sc = new Scanner(System.in);
        String start, end;

        switch (user_ctrl) {

            case 1:

                System.out.println("Enter the From location :");
                start = sc.nextLine();

                System.out.println("\n");

                System.out.println("Enter the To location :");
                end = sc.nextLine();

                System.out.println("\n");
                matchingTrains = Search.search(start, end, trains);

                if (!matchingTrains.isEmpty()) {
                    FareCalculator fareCalculator = new FareCalculator();

                    String[] headers = { "Train No", "Train Name", "Date", "First Class Fare", "Seats",
                            "Second Class Fare", "Seats", "Third Class Fare", "Seats" };
                    String[][] data = new String[matchingTrains.size()][headers.length];

                    int index = 0;
                    for (Train[] foundTrain : matchingTrains) {
                        data[index][0] = foundTrain[0].train_no;
                        data[index][1] = foundTrain[0].train_name;
                        data[index][2] = foundTrain[0].dateTime.toString();
                        fareCalculator.calculateFare(foundTrain, start, end);
                        data[index][3] = String.valueOf(fareCalculator.fare1);
                        data[index][4] = String.valueOf(((FirstClass) foundTrain[1]).firstClassCapacity);
                        data[index][5] = String.valueOf(fareCalculator.fare2);
                        data[index][6] = String.valueOf(((SecondClass) foundTrain[2]).secondClassCapacity);
                        data[index][7] = String.valueOf(fareCalculator.fare3);
                        data[index][8] = String.valueOf(((ThirdClass) foundTrain[3]).thirdClassCapacity);
                        index++;
                    }

                    Boxing.displayTable(headers, data);
                }

                else {
                    Boxing.displayBox("No train found");
                    System.exit(0);
                }

                System.out.println("\n");
                String trainNo;
                System.out.println("If you want to book a ticket enter 1 else enter 0");
                int bookCtrl = sc.nextInt();
                sc.nextLine();

                if (bookCtrl == 1) {
                    System.out.println("\n");
                    System.out.println("Enter the train number to book : ");
                    trainNo = sc.nextLine();
                    Booking booking = new Booking(currPer, trainNo, trains, start, end);// person

                    FareCalculator fareFinal = new FareCalculator();// we are creating a new object of fare calculator
                                                                    // class to calculate the fare of the selected train

                    for (Train[] tempObj : matchingTrains) {
                        if (tempObj[0].train_no.equals(trainNo)) {
                            fareFinal.calculateFare(tempObj, start, end);
                            break;
                        }
                    }
                    booking.book(matchingTrains, fareFinal.fare1, fareFinal.fare2, fareFinal.fare3);
                    try {
                        Train.writeListToFile(trains);
                        Person.writeListToFile(persons);
                    }

                    catch (Exception e) {
                        System.out.println("error occurred in writting the objects to the file");
                    }

                } else {
                    System.out.println("Thank you for using the system");
                    System.exit(0);
                }

                break;

            case 2:
                System.out.println("Booked Tickets");
                currPer.displayTicket();
                Cancel cancelObj = new Cancel(currPer, trains);
                cancelObj.cancel();
                try {
                    Train.writeListToFile(trains);
                    Person.writeListToFile(persons);
                }

                catch (Exception e) {
                    System.out.println("error occurred in writting the objects to the file");
                }
                break;

            case 3:
                currPer.displayTicket();
        }

    }
}
