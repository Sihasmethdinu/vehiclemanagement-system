package com.vehiclerental.app;

import java.util.Scanner;

import com.vehiclerental.algorithm.SearchAlgorithms;
import com.vehiclerental.algorithm.SortingAlgorithms;
import com.vehiclerental.model.BookingRequest;
import com.vehiclerental.model.Vehicle;
import com.vehiclerental.service.VehicleRentalService;
import com.vehiclerental.util.InputUtil;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputUtil input = new InputUtil(scanner);
    private static final VehicleRentalService service = new VehicleRentalService();

    public static void main(String[] args) {
        runMainMenu();
    }

    private static void runMainMenu() {
        while (true) {
            System.out.println("\n====================================================");
            System.out.println("        VEHICLE RENTAL MANAGEMENT SYSTEM");
            System.out.println("====================================================");
            System.out.println("1.  Show Fixed Arrays");
            System.out.println("2.  Add Vehicle");
            System.out.println("3.  Display Vehicles");
            System.out.println("4.  Delete Vehicle");
            System.out.println("5.  Search Vehicle");
            System.out.println("6.  Add Customer");
            System.out.println("7.  Display Customers");
            System.out.println("8.  Rent Vehicle");
            System.out.println("9.  Return Vehicle");
            System.out.println("10. Booking Queue Operations");
            System.out.println("11. Stack: Recent Activity / Undo");
            System.out.println("12. Hash Table and Set ADT Demo");
            System.out.println("13. BST Operations");
            System.out.println("14. AVL Tree Operations");
            System.out.println("15. Graph Operations");
            System.out.println("16. Sorting and Performance Comparison");
            System.out.println("17. Display Rentals");
            System.out.println("0.  Exit");
            System.out.println("====================================================");

            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    showFixedArrays();
                    break;
                case 2:
                    addVehicleFromInput();
                    break;
                case 3:
                    service.getVehicleList().display();
                    break;
                case 4:
                    deleteVehicleFromInput();
                    break;
                case 5:
                    searchVehicleMenu();
                    break;
                case 6:
                    addCustomerFromInput();
                    break;
                case 7:
                    service.getCustomerList().display();
                    break;
                case 8:
                    rentVehicleFromInput();
                    break;
                case 9:
                    returnVehicleFromInput();
                    break;
                case 10:
                    bookingQueueMenu();
                    break;
                case 11:
                    activityStackMenu();
                    break;
                case 12:
                    hashTableAndSetMenu();
                    break;
                case 13:
                    bstMenu();
                    break;
                case 14:
                    avlMenu();
                    break;
                case 15:
                    graphMenu();
                    break;
                case 16:
                    sortingMenu();
                    break;
                case 17:
                    service.getRentalList().display();
                    break;
                case 0:
                    System.out.println("Thank you. Program ended.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showFixedArrays() {
        System.out.println("\n--- Array Data Structure Demo ---");
        printStringArray("Vehicle Types", service.getVehicleTypes());
        printStringArray("Fuel Types", service.getFuelTypes());
        printStringArray("Branches", service.getBranches());
        System.out.println("Array access example: VehicleTypes[0] = " + service.getVehicleTypes()[0]);
        System.out.println("Array length: VehicleTypes.length = " + service.getVehicleTypes().length);
    }

    private static void printStringArray(String title, String[] arr) {
        System.out.print(title + ": ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private static void addVehicleFromInput() {
        System.out.println("\n--- Add Vehicle ---");
        String regNo = input.readText("Registration No: ").toUpperCase();
        printStringArray("Available Vehicle Types", service.getVehicleTypes());
        String type = input.readText("Vehicle Type: ");
        String brand = input.readText("Brand: ");
        String model = input.readText("Model: ");
        int year = input.readInt("Year: ");
        double dailyRate = input.readDouble("Daily Rental Rate: ");
        printStringArray("Available Branches", service.getBranches());
        String branch = input.readText("Branch: ");

        System.out.println(service.addVehicle(regNo, type, brand, model, year, dailyRate, branch));
    }

    private static void deleteVehicleFromInput() {
        System.out.println("\n--- Delete Vehicle ---");
        int id = input.readInt("Enter Vehicle ID to delete: ");
        System.out.println(service.deleteVehicle(id));
    }

    private static void searchVehicleMenu() {
        System.out.println("\n--- Search Vehicle ---");
        System.out.println("1. Linear Search by Vehicle ID");
        System.out.println("2. Hash Table Search by Registration No");
        System.out.println("3. Linear Search by Type");
        int choice = input.readInt("Enter choice: ");

        switch (choice) {
            case 1:
                int id = input.readInt("Enter Vehicle ID: ");
                long start = System.nanoTime();
                Vehicle result = SearchAlgorithms.linearSearchById(service.getVehicleList().toArray(), id);
                long end = System.nanoTime();
                printSearchResult(result);
                System.out.println("Linear Search Time: " + (end - start) + " ns");
                break;
            case 2:
                String regNo = input.readText("Enter Registration No: ").toUpperCase();
                start = System.nanoTime();
                result = service.getVehicleHashTable().get(regNo);
                end = System.nanoTime();
                printSearchResult(result);
                System.out.println("Hash Table Search Time: " + (end - start) + " ns");
                break;
            case 3:
                String type = input.readText("Enter Vehicle Type: ");
                Vehicle[] vehicles = SearchAlgorithms.linearSearchByType(service.getVehicleList().toArray(), type);
                if (vehicles.length == 0) {
                    System.out.println("No vehicles found for type: " + type);
                } else {
                    Vehicle.printHeader();
                    for (int i = 0; i < vehicles.length; i++) {
                        vehicles[i].printRow();
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void printSearchResult(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
        } else {
            Vehicle.printHeader();
            vehicle.printRow();
        }
    }

    private static void addCustomerFromInput() {
        System.out.println("\n--- Add Customer ---");
        String name = input.readText("Customer Name: ");
        String nic = input.readText("NIC: ").toUpperCase();
        String phone = input.readText("Phone: ");
        String licenseNo = input.readText("Driving License No: ").toUpperCase();

        System.out.println(service.addCustomer(name, nic, phone, licenseNo));
    }

    private static void rentVehicleFromInput() {
        System.out.println("\n--- Rent Vehicle ---");
        int vehicleId = input.readInt("Vehicle ID: ");
        int customerId = input.readInt("Customer ID: ");
        int days = input.readInt("Number of Days: ");
        System.out.println(service.rentVehicle(vehicleId, customerId, days));
    }

    private static void returnVehicleFromInput() {
        System.out.println("\n--- Return Vehicle ---");
        int rentalId = input.readInt("Enter Rental ID: ");
        System.out.println(service.returnVehicle(rentalId));

        System.out.println("Do you want to process next booking request? 1.Yes  2.No");
        int choice = input.readInt("Choice: ");
        if (choice == 1) {
            System.out.println(service.processNextBookingRequest());
        }
    }

    private static void bookingQueueMenu() {
        while (true) {
            System.out.println("\n--- Booking Queue Menu ---");
            System.out.println("1. Display Booking Queue");
            System.out.println("2. Process Next Booking Request");
            System.out.println("3. Peek Front Request");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getBookingQueue().display();
                    break;
                case 2:
                    System.out.println(service.processNextBookingRequest());
                    break;
                case 3:
                    BookingRequest front = service.getBookingQueue().peek();
                    if (front == null) {
                        System.out.println("Queue is empty.");
                    } else {
                        System.out.println(front);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void activityStackMenu() {
        while (true) {
            System.out.println("\n--- Activity Stack Menu ---");
            System.out.println("1. Display Recent Activities");
            System.out.println("2. Peek Last Activity");
            System.out.println("3. Undo Last Activity (Pop)");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getActivityStack().display();
                    break;
                case 2:
                    String top = service.getActivityStack().peek();
                    System.out.println(top == null ? "Stack is empty." : "Last Activity: " + top);
                    break;
                case 3:
                    String removed = service.getActivityStack().pop();
                    System.out.println(removed == null ? "Stack is empty." : "Undo/Removed Activity: " + removed);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void hashTableAndSetMenu() {
        while (true) {
            System.out.println("\n--- Hash Table and Set ADT Menu ---");
            System.out.println("1. Display Vehicle Hash Table");
            System.out.println("2. Search Vehicle by Registration No using Hash Table");
            System.out.println("3. Check Duplicate Vehicle Registration using Set ADT");
            System.out.println("4. Check Duplicate Customer NIC using Set ADT");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getVehicleHashTable().display();
                    break;
                case 2:
                    String regNo = input.readText("Registration No: ").toUpperCase();
                    printSearchResult(service.getVehicleHashTable().get(regNo));
                    break;
                case 3:
                    regNo = input.readText("Registration No: ").toUpperCase();
                    if (service.getVehicleRegSet().contains(regNo)) {
                        System.out.println("Duplicate found. This registration already exists.");
                    } else {
                        System.out.println("No duplicate. This registration can be used.");
                    }
                    break;
                case 4:
                    String nic = input.readText("NIC: ").toUpperCase();
                    if (service.getNicSet().contains(nic)) {
                        System.out.println("Duplicate found. This NIC already exists.");
                    } else {
                        System.out.println("No duplicate. This NIC can be used.");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void bstMenu() {
        while (true) {
            System.out.println("\n--- Binary Search Tree Menu ---");
            System.out.println("1. Inorder Traversal");
            System.out.println("2. Preorder Traversal");
            System.out.println("3. Postorder Traversal");
            System.out.println("4. Search Vehicle ID in BST");
            System.out.println("5. Delete Vehicle from Full System");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getVehicleBST().inorder();
                    break;
                case 2:
                    service.getVehicleBST().preorder();
                    break;
                case 3:
                    service.getVehicleBST().postorder();
                    break;
                case 4:
                    int id = input.readInt("Vehicle ID: ");
                    printSearchResult(service.getVehicleBST().search(id));
                    break;
                case 5:
                    deleteVehicleFromInput();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void avlMenu() {
        while (true) {
            System.out.println("\n--- AVL Tree Menu ---");
            System.out.println("1. Inorder Traversal");
            System.out.println("2. Preorder Traversal");
            System.out.println("3. Postorder Traversal");
            System.out.println("4. Search Vehicle ID in AVL Tree");
            System.out.println("5. Delete Vehicle from Full System");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getVehicleAVLTree().inorder();
                    break;
                case 2:
                    service.getVehicleAVLTree().preorder();
                    break;
                case 3:
                    service.getVehicleAVLTree().postorder();
                    break;
                case 4:
                    int id = input.readInt("Vehicle ID: ");
                    printSearchResult(service.getVehicleAVLTree().search(id));
                    break;
                case 5:
                    deleteVehicleFromInput();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void graphMenu() {
        while (true) {
            System.out.println("\n--- Branch Graph Menu ---");
            System.out.println("1. Display Graph");
            System.out.println("2. BFS Traversal");
            System.out.println("3. DFS Traversal");
            System.out.println("0. Back");
            int choice = input.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    service.getBranchGraph().display();
                    break;
                case 2:
                    String startBfs = input.readText("Start Branch: ");
                    service.getBranchGraph().bfs(startBfs);
                    break;
                case 3:
                    String startDfs = input.readText("Start Branch: ");
                    service.getBranchGraph().dfs(startDfs);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void sortingMenu() {
        Vehicle[] original = service.getVehicleList().toArray();
        if (original.length == 0) {
            System.out.println("No vehicles to sort.");
            return;
        }

        System.out.println("\nSorting vehicles by Daily Rental Rate.");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Merge Sort");
        System.out.println("5. Quick Sort");
        System.out.println("6. Compare All Sorting Algorithms");
        int choice = input.readInt("Enter choice: ");

        switch (choice) {
            case 1:
                runSingleSort("Bubble Sort", original);
                break;
            case 2:
                runSingleSort("Selection Sort", original);
                break;
            case 3:
                runSingleSort("Insertion Sort", original);
                break;
            case 4:
                runSingleSort("Merge Sort", original);
                break;
            case 5:
                runSingleSort("Quick Sort", original);
                break;
            case 6:
                compareSortingAlgorithms(original);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void runSingleSort(String sortName, Vehicle[] original) {
        Vehicle[] arr = SortingAlgorithms.copyVehicleArray(original);
        long start = System.nanoTime();
        SortingAlgorithms.applySort(sortName, arr);
        long end = System.nanoTime();

        System.out.println("\n--- " + sortName + " Result ---");
        SortingAlgorithms.displayVehicleArray(arr);
        System.out.println(sortName + " Execution Time: " + (end - start) + " ns");
    }

    private static void compareSortingAlgorithms(Vehicle[] original) {
        String[] names = { "Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort" };

        System.out.println("\n--- Sorting Performance Comparison ---");
        System.out.printf("%-20s %-20s%n", "Algorithm", "Time (ns)");
        System.out.println("----------------------------------------");

        for (int i = 0; i < names.length; i++) {
            Vehicle[] arr = SortingAlgorithms.copyVehicleArray(original);
            long start = System.nanoTime();
            SortingAlgorithms.applySort(names[i], arr);
            long end = System.nanoTime();
            System.out.printf("%-20s %-20d%n", names[i], (end - start));
        }
    }
}
