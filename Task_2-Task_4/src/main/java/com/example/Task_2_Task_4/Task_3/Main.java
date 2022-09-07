package com.example.Task_2_Task_4.Task_3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Main {
    static  FuelQue_class[] fuelQueclassArray = new FuelQue_class[5];
    static FuelQue_class[] waitingList=new FuelQue_class[1];
    static int MainFuelTank=6600;

    static int[] queuIncome=new int[5];

    public static void main(String[] args) {

        //Initilizing scanner and variables
        Scanner sc=new Scanner(System.in);
        String Fname,Lname,RegPlate;
        int fuel;

        //Initializing fuel ques
        for (int i = 0; i < fuelQueclassArray.length; i++) {
            fuelQueclassArray[i]=new FuelQue_class(6);
        }

        //Initializing waiting queue
        waitingList[0]=new FuelQue_class(10);



        while (true) {
            //Displaying user options
            System.out.println("\n");
            System.out.println("100 or VFQ: View all Fuel Queues");
            System.out.println("101 or VEQ: View all Empty Queues");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
            System.out.println("104 or PCQ: Remove a served customer");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining Fuel Stock.");
            System.out.println("109 or AFS: Add Fuel Stock.");
            System.out.println("110 or IFQ: View income of each Fuel queue");
            System.out.println("999 or EXT: Exit the Program");
            System.out.print("choice--->>> ");
            String OptionChoice = sc.next().toUpperCase();
            switch (OptionChoice){

                case "100":
                case "VFQ":
                    //Displays current fuel ques
                    showQueues();
                    continue;

                case "101":
                case "VEQ":
                    //Displays number of empty slots in ques
                    for (int i = 0; i <5 ; i++) {
                        //Assumes that if there are empty slots in queue, waiting list will be empty also
                        if(fuelQueclassArray[i].getNoOfEmptySlots()>0) {
                            System.out.print("No of empty slots in queue " + (i + 1)+": ");
                            System.out.println(fuelQueclassArray[i].getNoOfEmptySlots());
                            System.out.println("\n");
                        }else{
                            System.out.println("Queue "+(i+1)+" is currently full");
                            System.out.println("\n");
                        }
                    }
                    continue;

                case "102":
                case "ACQ":
                    //Adds new customer to the shortest queue if available or to waiting list otherwise
                    sc.nextLine();
                    while (true){
                        System.out.print("First name: ");
                        Fname = sc.nextLine().toLowerCase();
                        if (Fname.length()!=0){
                            break;
                        }
                        else {
                            System.out.println("First name field cannot be empty, try again.\n");
                        }
                    }
                    while (true){
                        System.out.print("Last name: ");
                        Lname = sc.nextLine().toLowerCase();
                        if (Lname.length()!=0){
                            break;
                        }
                        else {
                            System.out.println("Last name field cannot be empty, try again.\n");
                        }
                    }

                    while (true){
                        System.out.print("Vehicle registration plate: ");
                        RegPlate = sc.nextLine().toLowerCase();
                        if (RegPlate.length() < 6 ||RegPlate.length() >7){
                            System.out.println("Invalid registration plate.\n");
                        }
                        else {
                            break;
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("fuel needed in litres: ");
                            fuel = sc.nextInt();
                            break;
                        }catch (Exception e){
                            System.out.println("Invalid input");

                            sc.next();
                        }
                    }
                    if(CheckQueueSlots()){
                        int shortestAvailableQueue = CheckShortestQueue();
                        fuelQueclassArray[shortestAvailableQueue].addPassenger(Fname, Lname, RegPlate, fuel);
                        System.out.println(Fname+" Successfully added to current shortest Queue: "+(shortestAvailableQueue+1));
                        MainFuelTank = MainFuelTank - fuel;
                        if (MainFuelTank<=500) {
                            System.out.println("!!!WARNING-FUEL LEVEL LOW!!! "+MainFuelTank+": litres remaining");
                        }
                    }else{
                        waitingList[0].addWaitingPassenger(Fname, Lname, RegPlate, fuel);
                        System.out.println("Sorry all queues are currently full, you have been added to the waiting list");
                    }
                    continue;
                case "103":
                case "RCQ":
                    //Removes selected customer from selected que
                    while (true) {
                        try {
                            sc.nextLine();
                            System.out.print("Queue to remove customer from?: ");
                            int removeCustomerQueu=sc.nextInt();
                            if(fuelQueclassArray[removeCustomerQueu-1].QueueSize()<1){
                                System.out.println("This Queue is currently empty");
                            }else {
                                showSelectedQueue(removeCustomerQueu);
                                sc.nextLine();
                                System.out.print("Index of customer to remove?: ");
                                int removeCustomerIndex = sc.nextInt();
                                removeCustomerIndex -= 1;
                                int fuelReturned=fuelQueclassArray[removeCustomerQueu-1].getFuelAmount(removeCustomerIndex);
                                fuelQueclassArray[removeCustomerQueu - 1].removePassenger(removeCustomerIndex);
                                if (waitingList[0].waitingQueueSize()>0) {
                                    fuelQueclassArray[removeCustomerQueu - 1].addPassenger(
                                            waitingList[0].getWaitingFname(0),
                                            waitingList[0].getWaitingLname(0),
                                            waitingList[0].getWaitingRegNo(0),
                                            waitingList[0].getWaitingFuelAmount(0));
                                    MainFuelTank = MainFuelTank - waitingList[0].getWaitingFuelAmount(0);
                                    waitingList[0].removeWaitingPassenger(0);
                                }
                                System.out.println("Selected customer removed successfully");

                                if (MainFuelTank+fuelReturned<=6600){
                                    MainFuelTank=MainFuelTank+fuelReturned;
                                }else {
                                    System.out.print("Fuel reserved for removed customer not returned to tank due to overflow");
                                }


                            }
                            break;
                        }
                        catch (InputMismatchException | IndexOutOfBoundsException e){
                            System.out.println("Invalid input");



                        }
                    }


                    continue;
                case "104":
                case "PCQ":
                    //Removes a served customer from selected que
                    while (true) {
                        try {
                            System.out.print("Queue to remove customer from?: ");
                            int removeCustomerQueu=sc.nextInt();
                            if(fuelQueclassArray[removeCustomerQueu-1].QueueSize()<1){
                                System.out.println("This Queue is currently empty");
                                break;
                            }else {
                                queuIncome[removeCustomerQueu-1]+=(fuelQueclassArray[removeCustomerQueu - 1].getFuelAmount(0)*430);
                                fuelQueclassArray[removeCustomerQueu - 1].removePassenger(0);
                                if (waitingList[0].waitingQueueSize()>0) {
                                    fuelQueclassArray[removeCustomerQueu - 1].addPassenger(
                                            waitingList[0].getWaitingFname(0),
                                            waitingList[0].getWaitingLname(0),
                                            waitingList[0].getWaitingRegNo(0),
                                            waitingList[0].getWaitingFuelAmount(0));
                                    MainFuelTank = MainFuelTank - waitingList[0].getWaitingFuelAmount(0);
                                    waitingList[0].removeWaitingPassenger(0);
                                    System.out.println(waitingList[0].waitingQueueSize());
                                }
                                showSelectedQueue(removeCustomerQueu);
                                System.out.println("Served customer successfully removed");
                                break;
                            }
                        }
                        catch (InputMismatchException | ArrayIndexOutOfBoundsException e ){
                            System.out.println("Invalid input");
                            sc.nextLine();
                        }
                    }

                    continue;

                case "105":
                case "VCS":
                    //Sorts the customers names currently in que in alphabetical order
                    sortedQueues();
                    continue;

                case "106":
                case "SPD":
                    try {
                        File myObj = new File("FuelQuesInfo.txt");
                        if (myObj.createNewFile()) {
                            System.out.println("File created: " + myObj.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException e) {
                        System.out.println("ERR: file could not be created!!!");
                        e.printStackTrace();
                    }
                    try {
                        FileWriter myWriter = new FileWriter("FuelQuesInfo.txt");
                        String fuelRemaining= String.valueOf(MainFuelTank);
                        for (int i = 0; i <5 ; i++) {
                            myWriter.write(fuelQueclassArray[i].QueueSize()+",");
                        }
                        myWriter.write("\n");
                        for (int i = 0; i <5 ; i++) {
                            for (int j = 0; j <fuelQueclassArray[i].QueueSize(); j++) {
                                if (fuelQueclassArray[i].getNoOfEmptySlots() < 6) {
                                    myWriter.write(
                                            fuelQueclassArray[i].getFname(j) + "," +
                                                    fuelQueclassArray[i].getLname(j) + "," +
                                                    fuelQueclassArray[i].getRegNo(j) + "," +
                                                    fuelQueclassArray[i].getFuelAmount(j) + "\n");
                                } else {
                                    myWriter.write("");
                                }
                            }
                        }
                        myWriter.write(fuelRemaining);
                        myWriter.write("\n"+waitingList[0].waitingQueueSize()+"\n");
                        for (int j = 0; j <waitingList[0].waitingQueueSize(); j++) {
                                myWriter.write(
                                        waitingList[0].getWaitingFname(j) + "," +
                                                waitingList[0].getWaitingLname(j) + "," +
                                                waitingList[0].getWaitingRegNo(j) + "," +
                                                waitingList[0].getWaitingFuelAmount(j) + "\n");
                        }
                        for (int j : queuIncome) {
                            myWriter.write(j + ",");
                        }
                        myWriter.close();
                        System.out.println("Successfully exported data!");
                    } catch (IOException e) {
                        System.out.println("ERR: file could not be written to");
                        e.printStackTrace();
                    }

                    continue;
                case "107":
                case "LPD":
                    //Loading passenger data from file
                    System.out.print("Are you sure you want to load data from saved file?(Y/N) it will over write current session: ");
                    String loadChoice=sc.next();
                    if (loadChoice.equalsIgnoreCase("y")){
                        Path file=Path.of("FuelQuesInfo.txt");
                        try {
                            //loading data of passenger currently in pump queue
                            //getting cursor pointer for start of passenger data
                            int lineNo=1;
                            String[] queLenghts=Files.readAllLines(file).get(0).split(",");
                            int noOfLinesRead;
                            for (int i = 0; i <queLenghts.length ; i++) {
                                noOfLinesRead=0;
                                fuelQueclassArray[i].resetQueue();
                                waitingList[0].resetWaitingQueue();
                                for (int j = lineNo; j < Integer.parseInt(queLenghts[i])+lineNo; j++) {
                                    String[] loadedPassengerData=Files.readAllLines(file).get(j).split(",");
                                    fuelQueclassArray[i].addPassenger(loadedPassengerData[0],loadedPassengerData[1],loadedPassengerData[2], Integer.parseInt(loadedPassengerData[3]));
                                    noOfLinesRead++;
                                }
                                lineNo+=noOfLinesRead;
                            }

                            //getting cursor pointer for remaining saved data
                            int totalLenght=0;
                            for (int i = 0; i <queLenghts.length ; i++) {
                                totalLenght=totalLenght+Integer.parseInt(queLenghts[i]);
                            }

                            //loading data of remaining main tank fuel amount
                            MainFuelTank= Integer.parseInt(Files.readAllLines(file).get(totalLenght+1));

                            //loading data of passenger currently in waiting queue
                            int waitingLineNo = totalLenght + 3;
                            String[] waitingQueLenghts=Files.readAllLines(file).get(totalLenght+2).split(",");
                            noOfLinesRead = waitingLineNo;
                            for (int i = 0; i < Integer.parseInt(waitingQueLenghts[0]); i++) {
                                String[] loadedWaitingPassengerData = Files.readAllLines(file).get(noOfLinesRead).split(",");
                                waitingList[0].addWaitingPassenger(loadedWaitingPassengerData[0], loadedWaitingPassengerData[1], loadedWaitingPassengerData[2], Integer.parseInt(loadedWaitingPassengerData[3]));
                                noOfLinesRead++;
                            }
                            String[] QueuIncomes=Files.readAllLines(file).get(totalLenght+3+Integer.parseInt(waitingQueLenghts[0])).split(",");
                            for (int i = 0; i < queuIncome.length; i++) {
                                queuIncome[i]= Integer.parseInt(QueuIncomes[i]);
                            }
                            System.out.println("Successfully resumed last saved session");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                        }
                    }

                    continue;

                case "108":
                case "STK":
                    //Checks remaining stock of fuel in main tank
                    System.out.println("Litres of fuel remaining: "+MainFuelTank);
                    continue;
                case "109":
                case "AFS":
                    //Adds new stock of fuel to main tank

                    int FillMainTank=0;
                    System.out.print("Enter amount of fuel to add: ");
                    FillMainTank= sc.nextInt();
                    if (FillMainTank+MainFuelTank>6600){
                        System.out.println("Re-fill amount too large by: "+(FillMainTank+MainFuelTank-6600)+" litres");
                    }else {
                        MainFuelTank=MainFuelTank+FillMainTank;
                        System.out.println(MainFuelTank);
                        System.out.println(FillMainTank+" litres successfully added to main fuel tank.");
                    }
                    continue;
                case "110":
                case "IFQ":
                    //Prints income from each queue so far
                    for (int i = 0; i < queuIncome.length; i++) {
                        System.out.println("Queue "+(i+1)+" income: "+queuIncome[i]);
                    }
                    continue;
                case "999":
                case "EXT":
                    //Exits program
                    break;
                default:
                    System.out.println("Invalid input, try again");
                    continue;
            }
            break;
        }
    }
    public static boolean CheckQueueSlots() {
        boolean SlotFree = false;
        for (int i = 0; i < 5; i++) {
            if (fuelQueclassArray[i].QueueSize() < 6) {
                SlotFree = true;
            }
        }
        return SlotFree;
    }

    public static int CheckShortestQueue(){
        int FreeSLots=0;
        int indexShortestQueue=0;
        for (int i = 0; i < 5; i++) {
            if (fuelQueclassArray[i].getNoOfEmptySlots()>FreeSLots){
                FreeSLots=fuelQueclassArray[i].getNoOfEmptySlots();
                indexShortestQueue=i;
            }
        }
        return indexShortestQueue;
    }

    public static void showQueues() {
        for (int x = 0; x < 5; x++) {
            int ListSize=fuelQueclassArray[x].QueueSize();
            System.out.println("Current income from Queue "+(x+1)+" is Rs: "+queuIncome[x]);
            if(ListSize<1){
                System.out.println("---------------------------------Queue "+(x+1)+"---------------------------------");
                System.out.printf("%43s%n","Currently Empty");
                System.out.println("\n");
            }else {
                System.out.println("---------------------------------Queue "+(x+1)+"---------------------------------");
                System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "|Queue Place|", "|First Name|", "|Last Name|", "|Vehicle No|", "|Fuel Amount|");
                for (int i = 0; i < ListSize; i++) {
                    System.out.printf("%-18s%-12s%-17s%-18s%-15s%n", "------" + (i + 1) + "------", fuelQueclassArray[x].getFname(i), fuelQueclassArray[x].getLname(i), fuelQueclassArray[x].getRegNo(i), fuelQueclassArray[x].getFuelAmount(i));
                }
                System.out.println("\n");
            }
        }
        if(waitingList[0].waitingQueueSize()>0) {
            System.out.println("---------------------------------Waiting Queue---------------------------------");
            System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "|Queue Place|", "|First Name|", "|Last Name|", "|Vehicle No|", "|Fuel Amount|");
            for (int i = 0; i < waitingList[0].waitingQueueSize(); i++) {
                System.out.printf("%-18s%-12s%-17s%-18s%-15s%n", "------" + (i + 1) + "------", waitingList[0].getWaitingFname(i), waitingList[0].getWaitingLname(i), waitingList[0].getWaitingRegNo(i), waitingList[0].getWaitingFuelAmount(i));
            }
        }else{
            System.out.println("---------------------------------Waiting Queue---------------------------------");
            System.out.printf("%43s%n","Currently Empty");
            System.out.println("\n");
        }
    }

    public static void showSelectedQueue(int removeCustomerQueu){
        System.out.println("---------------------------------Queue "+(removeCustomerQueu)+"---------------------------------");
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "|Queue Place|", "|First Name|", "|Last Name|", "|Vehicle No|", "|Fuel Amount|");
        for (int i = 0; i < fuelQueclassArray[removeCustomerQueu-1].QueueSize(); i++) {
            System.out.printf("%-18s%-12s%-17s%-18s%-15s%n", "------" + (i + 1) + "------", fuelQueclassArray[removeCustomerQueu-1].getFname(i), fuelQueclassArray[removeCustomerQueu-1].getLname(i), fuelQueclassArray[removeCustomerQueu-1].getRegNo(i), fuelQueclassArray[removeCustomerQueu-1].getFuelAmount(i));
        }
        System.out.println("\n");

    }


    public static void sortedQueues(){
        ArrayList<String> sortedQueue1 = new ArrayList<String>(fuelQueclassArray[0].QueueSize());
        ArrayList<String> sortedQueue2 = new ArrayList<String>(fuelQueclassArray[1].QueueSize());
        ArrayList<String> sortedQueue3 = new ArrayList<String>(fuelQueclassArray[2].QueueSize());
        ArrayList<String> sortedQueue4 = new ArrayList<String>(fuelQueclassArray[3].QueueSize());
        ArrayList<String> sortedQueue5 = new ArrayList<String>(fuelQueclassArray[4].QueueSize());


        for(int i=0;i<fuelQueclassArray[0].QueueSize();i++) {
            sortedQueue1.add(fuelQueclassArray[0].getFname(i)+" "+fuelQueclassArray[0].getLname(i)+" "+fuelQueclassArray[0].getRegNo(i));
        }
        for(int i=0;i<fuelQueclassArray[1].QueueSize();i++) {
            sortedQueue2.add(fuelQueclassArray[1].getFname(i)+" "+fuelQueclassArray[1].getLname(i)+" "+fuelQueclassArray[1].getRegNo(i));
        }
        for(int i=0;i<fuelQueclassArray[2].QueueSize();i++) {
            sortedQueue3.add(fuelQueclassArray[2].getFname(i)+" "+fuelQueclassArray[2].getLname(i)+" "+fuelQueclassArray[2].getRegNo(i));
        }
        for(int i=0;i<fuelQueclassArray[3].QueueSize();i++) {
            sortedQueue4.add(fuelQueclassArray[3].getFname(i)+" "+fuelQueclassArray[3].getLname(i)+" "+fuelQueclassArray[3].getRegNo(i));
        }
        for(int i=0;i<fuelQueclassArray[4].QueueSize();i++) {
            sortedQueue5.add(fuelQueclassArray[4].getFname(i)+" "+fuelQueclassArray[4].getLname(i)+" "+fuelQueclassArray[4].getRegNo(i));
        }

        Collections.sort(sortedQueue1);
        Collections.sort(sortedQueue2);
        Collections.sort(sortedQueue3);
        Collections.sort(sortedQueue4);
        Collections.sort(sortedQueue5);

        if(sortedQueue1.size()<1){
            System.out.println("-------------------------sorted Queue-1---------------------------------");
            System.out.println("\n");
            System.out.println("This queue is currently empty");
            System.out.println("\n");
        }else {
            System.out.println("-------------------------sorted Queue-1---------------------------------");
            System.out.println("\n");
            System.out.println(String.join("\n", sortedQueue1));
            System.out.println("\n");
        }
        if(sortedQueue2.size()<1){
            System.out.println("Queue-2 is currently empty");
            System.out.println("\n");
        }else {
            System.out.println("--------------------------sorted Queue-2---------------------------------");
            System.out.println("\n");
            System.out.println(String.join("\n", sortedQueue2));
            System.out.println("\n");
        }
        if(sortedQueue3.size()<1){
            System.out.println("Queue-3 is currently empty");
            System.out.println("\n");
        }else {
            System.out.println("--------------------------sorted Queue-3---------------------------------");
            System.out.println("\n");
            System.out.println(String.join(",\n", sortedQueue3));
            System.out.println("\n");
        }
        if(sortedQueue4.size()<1){
            System.out.println("Queue-4 is currently empty");
            System.out.println("\n");
        }else {
            System.out.println("--------------------------sorted Queue-4---------------------------------");
            System.out.println("\n");
            System.out.println(String.join("\n", sortedQueue4));
            System.out.println("\n");
        }
        if(sortedQueue5.size()<1){
            System.out.println("Queue-5 is currently empty");
            System.out.println("\n");
        }else {
            System.out.println("--------------------------sorted Queue-5---------------------------------");
            System.out.println("\n");
            System.out.println(String.join("\n", sortedQueue5));
            System.out.println("\n");
        }



    }
}

