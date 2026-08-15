package com.vehiclerental.model;

public class Rental {
    private int rentalId;
    private int vehicleId;
    private int customerId;
    private int days;
    private double totalCost;
    private String status;

    public Rental(int rentalId, int vehicleId, int customerId, int days, double totalCost, String status) {
        this.rentalId = rentalId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.days = days;
        this.totalCost = totalCost;
        this.status = status;
    }

    public int getRentalId() {
        return rentalId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void printHeader() {
        System.out.printf("%-8s %-10s %-10s %-6s %-12s %-10s%n",
                "Rental", "Vehicle", "Customer", "Days", "Total", "Status");
        System.out.println("----------------------------------------------------------------");
    }

    public void printRow() {
        System.out.printf("%-8d %-10d %-10d %-6d %-12.2f %-10s%n",
                rentalId, vehicleId, customerId, days, totalCost, status);
    }
}
