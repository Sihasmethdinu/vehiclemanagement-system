package com.vehiclerental.model;

public class Vehicle {
    private int vehicleId;
    private String regNo;
    private String type;
    private String brand;
    private String model;
    private int year;
    private double dailyRate;
    private boolean available;
    private String branch;

    public Vehicle(int vehicleId, String regNo, String type, String brand, String model, int year,
                   double dailyRate, boolean available, String branch) {
        this.vehicleId = vehicleId;
        this.regNo = regNo;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
        this.available = available;
        this.branch = branch;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBranch() {
        return branch;
    }

    public static void printHeader() {
        System.out.printf("%-6s %-12s %-8s %-12s %-12s %-6s %-12s %-12s %-12s%n",
                "ID", "Reg No", "Type", "Brand", "Model", "Year", "Rate", "Available", "Branch");
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public void printRow() {
        System.out.printf("%-6d %-12s %-8s %-12s %-12s %-6d %-12.2f %-12s %-12s%n",
                vehicleId, regNo, type, brand, model, year, dailyRate, available ? "Yes" : "No", branch);
    }
}
