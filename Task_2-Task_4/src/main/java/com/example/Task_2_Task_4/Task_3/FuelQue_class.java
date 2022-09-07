package com.example.Task_2_Task_4.Task_3;
import java.util.ArrayList;

public class FuelQue_class {
    private ArrayList<passenger_class> CustomerQueue =new ArrayList<passenger_class>();
    private ArrayList<passenger_class> CustomerWaittingQueue =new ArrayList<passenger_class>();

    private int queueLength;

    public int getQueueLength() {
        return queueLength;
    }

    public FuelQue_class(int fuelQueueLength) {
        this.queueLength = fuelQueueLength;
    }

    public void addPassenger(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters) {
        CustomerQueue.add(new passenger_class(passengerFirstName,passengerLastName,vehicleNumber,noOfLiters));
    }

    public void addWaitingPassenger(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters){
        CustomerWaittingQueue.add(new passenger_class(passengerFirstName,passengerLastName,vehicleNumber,noOfLiters));
    }

    public void removePassenger(int removeCustomerIndex){
                CustomerQueue.remove(removeCustomerIndex);
    }
    public void removeWaitingPassenger(int removeWaitingCustomerIndex){
        CustomerWaittingQueue.remove(removeWaitingCustomerIndex);
    }



    public  int QueueSize(){
        return CustomerQueue.size();
    }

    public  int waitingQueueSize(){
        return CustomerWaittingQueue.size();
    }

    public int getNoOfEmptySlots(){
        return queueLength-CustomerQueue.size();
    }

    public int getNoOfWaitingEmptySlots(){
        return queueLength-CustomerWaittingQueue.size();
    }
    public String getFname(int i){
       return CustomerQueue.get(i).getPassengerFirstName();
    }
    public String getLname(int i){return CustomerQueue.get(i).getPassengerLastName();}
    public String getRegNo(int i){return CustomerQueue.get(i).getVehicleNumber();}
    public int getFuelAmount(int i){return CustomerQueue.get(i).getNoOfLiters();}

    public String getWaitingFname(int i){
        return CustomerWaittingQueue.get(i).getPassengerFirstName();
    }
    public String getWaitingLname(int i){return CustomerWaittingQueue.get(i).getPassengerLastName();}
    public String getWaitingRegNo(int i){return CustomerWaittingQueue.get(i).getVehicleNumber();}
    public int getWaitingFuelAmount(int i){return CustomerWaittingQueue.get(i).getNoOfLiters();}

    public void resetQueue(){
        CustomerQueue.clear();
    }

    public void resetWaitingQueue(){
        CustomerWaittingQueue.clear();
    }

    @Override
    public String toString() {
        for (int i = 0; i < CustomerQueue.size(); i++) {

            System.out.println(CustomerQueue.get(i).toString());
        }
        return null;
    }



}
