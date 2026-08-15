package com.vehiclerental.service;

import com.vehiclerental.datastructure.graph.BranchGraph;
import com.vehiclerental.datastructure.hash.StringSet;
import com.vehiclerental.datastructure.hash.VehicleHashTable;
import com.vehiclerental.datastructure.list.CustomerLinkedList;
import com.vehiclerental.datastructure.list.RentalLinkedList;
import com.vehiclerental.datastructure.list.VehicleLinkedList;
import com.vehiclerental.datastructure.queue.BookingQueue;
import com.vehiclerental.datastructure.stack.ActivityStack;
import com.vehiclerental.datastructure.tree.VehicleAVLTree;
import com.vehiclerental.datastructure.tree.VehicleBST;
import com.vehiclerental.model.BookingRequest;
import com.vehiclerental.model.Customer;
import com.vehiclerental.model.Rental;
import com.vehiclerental.model.Vehicle;

public class VehicleRentalService {
    private final String[] vehicleTypes = { "Car", "Van", "Bike", "SUV", "Truck" };
    private final String[] fuelTypes = { "Petrol", "Diesel", "Hybrid", "Electric" };
    private final String[] branches = { "Colombo", "Kandy", "Galle", "Negombo", "Matara", "Jaffna" };

    private final VehicleLinkedList vehicleList = new VehicleLinkedList();
    private final CustomerLinkedList customerList = new CustomerLinkedList();
    private final RentalLinkedList rentalList = new RentalLinkedList();

    private final ActivityStack activityStack = new ActivityStack(50);
    private final BookingQueue bookingQueue = new BookingQueue(50);

    private final VehicleHashTable vehicleHashTable = new VehicleHashTable(101);
    private final StringSet vehicleRegSet = new StringSet(101);
    private final StringSet nicSet = new StringSet(101);
    private final StringSet licenseSet = new StringSet(101);

    private final VehicleBST vehicleBST = new VehicleBST();
    private final VehicleAVLTree vehicleAVLTree = new VehicleAVLTree();
    private final BranchGraph branchGraph = new BranchGraph(20);

    private int nextVehicleId = 1001;
    private int nextCustomerId = 5001;
    private int nextRentalId = 8001;

    public VehicleRentalService() {
        loadSampleData();
        createBranchGraph();
    }

    private void loadSampleData() {
        addVehicleDirect("CAR-1001", "Car", "Toyota", "Aqua", 2020, 8500.00, "Colombo");
        addVehicleDirect("VAN-2001", "Van", "Nissan", "Caravan", 2019, 12000.00, "Kandy");
        addVehicleDirect("BIK-3001", "Bike", "Honda", "Dio", 2022, 2500.00, "Galle");
        addVehicleDirect("SUV-4001", "SUV", "Mitsubishi", "Montero", 2018, 18000.00, "Negombo");
        addVehicleDirect("CAR-1002", "Car", "Suzuki", "WagonR", 2021, 7500.00, "Matara");
        addVehicleDirect("TRK-5001", "Truck", "Tata", "Ace", 2017, 15000.00, "Jaffna");

        addCustomerDirect("Kasun Perera", "200012345678", "0771234567", "B1234567");
        addCustomerDirect("Nimal Silva", "199912345678", "0712223333", "B7654321");
        addCustomerDirect("Amal Fernando", "200145678901", "0758889999", "B9988776");

        activityStack.push("Sample data loaded into the system");
    }

    private void createBranchGraph() {
        for (int i = 0; i < branches.length; i++) {
            branchGraph.addVertex(branches[i]);
        }

        branchGraph.addEdge("Colombo", "Negombo");
        branchGraph.addEdge("Colombo", "Kandy");
        branchGraph.addEdge("Colombo", "Galle");
        branchGraph.addEdge("Galle", "Matara");
        branchGraph.addEdge("Kandy", "Jaffna");
        branchGraph.addEdge("Negombo", "Jaffna");
    }

    public String addVehicle(String regNo, String type, String brand, String model, int year, double dailyRate, String branch) {
        String normalizedRegNo = regNo.toUpperCase();
        if (vehicleRegSet.contains(normalizedRegNo)) {
            return "Duplicate vehicle registration number. Vehicle not added.";
        }

        Vehicle vehicle = new Vehicle(nextVehicleId++, normalizedRegNo, type, brand, model, year, dailyRate, true, branch);
        addVehicleToAllStructures(vehicle);
        activityStack.push("Added vehicle " + normalizedRegNo);
        return "Vehicle added successfully. Vehicle ID = " + vehicle.getVehicleId();
    }

    private void addVehicleDirect(String regNo, String type, String brand, String model, int year, double dailyRate, String branch) {
        Vehicle vehicle = new Vehicle(nextVehicleId++, regNo, type, brand, model, year, dailyRate, true, branch);
        addVehicleToAllStructures(vehicle);
    }

    private void addVehicleToAllStructures(Vehicle vehicle) {
        vehicleList.add(vehicle);
        vehicleRegSet.add(vehicle.getRegNo());
        vehicleHashTable.put(vehicle.getRegNo(), vehicle);
        vehicleBST.insert(vehicle);
        vehicleAVLTree.insert(vehicle);
    }

    public String deleteVehicle(int vehicleId) {
        Vehicle vehicle = vehicleList.searchById(vehicleId);
        if (vehicle == null) {
            return "Vehicle not found.";
        }
        if (!vehicle.isAvailable()) {
            return "This vehicle is currently rented. Return it before deleting.";
        }

        Vehicle removed = vehicleList.deleteById(vehicleId);
        if (removed != null) {
            vehicleHashTable.remove(removed.getRegNo());
            vehicleRegSet.remove(removed.getRegNo());
            vehicleBST.delete(vehicleId);
            vehicleAVLTree.delete(vehicleId);
            activityStack.push("Deleted vehicle " + removed.getRegNo());
            return "Vehicle deleted successfully.";
        }
        return "Vehicle delete failed.";
    }

    public String addCustomer(String name, String nic, String phone, String licenseNo) {
        String normalizedNic = nic.toUpperCase();
        String normalizedLicense = licenseNo.toUpperCase();

        if (nicSet.contains(normalizedNic)) {
            return "Duplicate NIC. Customer not added.";
        }
        if (licenseSet.contains(normalizedLicense)) {
            return "Duplicate Driving License No. Customer not added.";
        }

        Customer customer = new Customer(nextCustomerId++, name, normalizedNic, phone, normalizedLicense);
        customerList.add(customer);
        nicSet.add(normalizedNic);
        licenseSet.add(normalizedLicense);
        activityStack.push("Added customer " + name);
        return "Customer added successfully. Customer ID = " + customer.getCustomerId();
    }

    private void addCustomerDirect(String name, String nic, String phone, String licenseNo) {
        Customer customer = new Customer(nextCustomerId++, name, nic, phone, licenseNo);
        customerList.add(customer);
        nicSet.add(nic);
        licenseSet.add(licenseNo);
    }

    public String rentVehicle(int vehicleId, int customerId, int days) {
        Vehicle vehicle = vehicleList.searchById(vehicleId);
        Customer customer = customerList.searchById(customerId);

        if (vehicle == null) {
            return "Vehicle not found.";
        }
        if (customer == null) {
            return "Customer not found.";
        }
        if (days <= 0) {
            return "Days must be greater than 0.";
        }

        if (!vehicle.isAvailable()) {
            bookingQueue.enqueue(new BookingRequest(vehicleId, customerId, days));
            activityStack.push("Added booking request for vehicle " + vehicle.getRegNo());
            return "Vehicle is not available now. Booking request added to queue.";
        }

        return createRental(vehicle, customer, days);
    }

    private String createRental(Vehicle vehicle, Customer customer, int days) {
        vehicle.setAvailable(false);
        double totalCost = vehicle.getDailyRate() * days;
        Rental rental = new Rental(nextRentalId++, vehicle.getVehicleId(), customer.getCustomerId(), days, totalCost, "ACTIVE");
        rentalList.add(rental);

        activityStack.push("Rented vehicle " + vehicle.getRegNo() + " to customer " + customer.getName());
        return "Rental created successfully.\nRental ID: " + rental.getRentalId() + "\nTotal Cost: " + String.format("%.2f", totalCost);
    }

    public String returnVehicle(int rentalId) {
        Rental rental = rentalList.searchById(rentalId);
        if (rental == null) {
            return "Rental not found.";
        }
        if (!rental.getStatus().equalsIgnoreCase("ACTIVE")) {
            return "This rental is already returned.";
        }

        Vehicle vehicle = vehicleList.searchById(rental.getVehicleId());
        if (vehicle != null) {
            vehicle.setAvailable(true);
        }
        rental.setStatus("RETURNED");

        activityStack.push("Returned vehicle for rental ID " + rentalId);
        return "Vehicle returned successfully.";
    }

    public String processNextBookingRequest() {
        BookingRequest request = bookingQueue.dequeue();
        if (request == null) {
            return "Booking queue is empty.";
        }

        Vehicle vehicle = vehicleList.searchById(request.getVehicleId());
        Customer customer = customerList.searchById(request.getCustomerId());

        if (vehicle == null || customer == null) {
            return "Request skipped because vehicle/customer does not exist.";
        }

        if (!vehicle.isAvailable()) {
            bookingQueue.enqueue(request);
            return "Vehicle is still unavailable. Request added back to rear of queue.";
        }

        activityStack.push("Processed booking queue request");
        return createRental(vehicle, customer, request.getDays());
    }

    public String[] getVehicleTypes() {
        return vehicleTypes;
    }

    public String[] getFuelTypes() {
        return fuelTypes;
    }

    public String[] getBranches() {
        return branches;
    }

    public VehicleLinkedList getVehicleList() {
        return vehicleList;
    }

    public CustomerLinkedList getCustomerList() {
        return customerList;
    }

    public RentalLinkedList getRentalList() {
        return rentalList;
    }

    public ActivityStack getActivityStack() {
        return activityStack;
    }

    public BookingQueue getBookingQueue() {
        return bookingQueue;
    }

    public VehicleHashTable getVehicleHashTable() {
        return vehicleHashTable;
    }

    public StringSet getVehicleRegSet() {
        return vehicleRegSet;
    }

    public StringSet getNicSet() {
        return nicSet;
    }

    public VehicleBST getVehicleBST() {
        return vehicleBST;
    }

    public VehicleAVLTree getVehicleAVLTree() {
        return vehicleAVLTree;
    }

    public BranchGraph getBranchGraph() {
        return branchGraph;
    }
}
