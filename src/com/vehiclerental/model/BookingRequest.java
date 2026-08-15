package com.vehiclerental.model;

public class BookingRequest {
    private int vehicleId;
    private int customerId;
    private int days;

    public BookingRequest(int vehicleId, int customerId, int days) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.days = days;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Customer ID: " + customerId + ", Days: " + days;
    }
}
