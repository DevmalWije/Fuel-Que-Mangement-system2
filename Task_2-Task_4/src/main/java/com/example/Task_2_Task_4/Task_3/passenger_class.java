package com.example.Task_2_Task_4.Task_3;

public class passenger_class {
    private String passengerFirstName;
    private String passengerLastName;
    private String vehicleNumber;
    private int noOfLiters;

    public passenger_class(String passengerFName, String passengerLName, String PlateNumber, int noOfLiters) {
        this.passengerFirstName = passengerFName;
        this.passengerLastName = passengerLName;
        this.vehicleNumber = PlateNumber;
        this.noOfLiters = noOfLiters;
    }

    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public int getNoOfLiters() {
        return noOfLiters;
    }

    @Override
    public String toString() {
        return "passenger{" +
                "passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", noOfLiters=" + noOfLiters +
                '}';
    }
}
