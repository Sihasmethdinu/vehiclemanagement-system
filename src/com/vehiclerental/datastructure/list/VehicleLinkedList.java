package com.vehiclerental.datastructure.list;

import com.vehiclerental.model.Vehicle;

public class VehicleLinkedList {
    private VehicleNode head;
    private VehicleNode tail;
    private int size;

    public void add(Vehicle vehicle) {
        VehicleNode newNode = new VehicleNode(vehicle);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public Vehicle searchById(int id) {
        VehicleNode current = head;
        while (current != null) {
            if (current.data.getVehicleId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Vehicle searchByRegNo(String regNo) {
        VehicleNode current = head;
        while (current != null) {
            if (current.data.getRegNo().equalsIgnoreCase(regNo)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Vehicle deleteById(int id) {
        if (head == null) {
            return null;
        }

        if (head.data.getVehicleId() == id) {
            Vehicle removed = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return removed;
        }

        VehicleNode previous = head;
        VehicleNode current = head.next;
        while (current != null) {
            if (current.data.getVehicleId() == id) {
                previous.next = current.next;
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return current.data;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public Vehicle[] toArray() {
        Vehicle[] arr = new Vehicle[size];
        VehicleNode current = head;
        int index = 0;
        while (current != null) {
            arr[index++] = current.data;
            current = current.next;
        }
        return arr;
    }

    public int size() {
        return size;
    }

    public void display() {
        if (head == null) {
            System.out.println("No vehicles available.");
            return;
        }
        Vehicle.printHeader();
        VehicleNode current = head;
        while (current != null) {
            current.data.printRow();
            current = current.next;
        }
    }

    private static class VehicleNode {
        private Vehicle data;
        private VehicleNode next;

        private VehicleNode(Vehicle data) {
            this.data = data;
        }
    }
}
