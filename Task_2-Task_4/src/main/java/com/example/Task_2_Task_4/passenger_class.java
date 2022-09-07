package com.example.Task_2_Task_4;

public class passenger_class {

    private String PassengerFname;
    private String PassengerLname;
    private String PassengerVehicleNo;
    private String PassengerFuelAmnt;
    private String PassengerQueue;

    public passenger_class(String FirstName, String LastName, String VehicleNumber, String FuelAMount, String QueueNum) {
        this.PassengerFname = FirstName;
        this.PassengerLname = LastName;
        this.PassengerVehicleNo = VehicleNumber;
        this.PassengerFuelAmnt = FuelAMount;
        this.PassengerQueue = QueueNum;
    }

    public String getPassengerFname() {
        return PassengerFname;
    }

    public String getPassengerLname() {
        return PassengerLname;
    }

    public String getPassengerVehicleNo() {
        return PassengerVehicleNo;
    }

    public String getPassengerFuelAmnt() {
        return PassengerFuelAmnt;
    }

    public String getPassengerQueue() {
        return PassengerQueue;
    }
}
