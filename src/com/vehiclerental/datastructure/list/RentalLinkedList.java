package com.vehiclerental.datastructure.list;

import com.vehiclerental.model.Rental;

public class RentalLinkedList {
    private RentalNode head;
    private RentalNode tail;

    public void add(Rental rental) {
        RentalNode newNode = new RentalNode(rental);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public Rental searchById(int rentalId) {
        RentalNode current = head;
        while (current != null) {
            if (current.data.getRentalId() == rentalId) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void display() {
        if (head == null) {
            System.out.println("No rental records available.");
            return;
        }
        Rental.printHeader();
        RentalNode current = head;
        while (current != null) {
            current.data.printRow();
            current = current.next;
        }
    }

    private static class RentalNode {
        private Rental data;
        private RentalNode next;

        private RentalNode(Rental data) {
            this.data = data;
        }
    }
}
