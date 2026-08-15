package com.vehiclerental.model;

public class Customer {
    private int customerId;
    private String name;
    private String nic;
    private String phone;
    private String licenseNo;

    public Customer(int customerId, String name, String nic, String phone, String licenseNo) {
        this.customerId = customerId;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.licenseNo = licenseNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getNic() {
        return nic;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public static void printHeader() {
        System.out.printf("%-8s %-20s %-15s %-12s %-15s%n", "ID", "Name", "NIC", "Phone", "License No");
        System.out.println("------------------------------------------------------------------------");
    }

    public void printRow() {
        System.out.printf("%-8d %-20s %-15s %-12s %-15s%n", customerId, name, nic, phone, licenseNo);
    }
}
