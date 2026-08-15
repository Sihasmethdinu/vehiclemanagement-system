package com.vehiclerental.datastructure.list;

import com.vehiclerental.model.Customer;

public class CustomerLinkedList {
    private CustomerNode head;
    private CustomerNode tail;

    public void add(Customer customer) {
        CustomerNode newNode = new CustomerNode(customer);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public Customer searchById(int id) {
        CustomerNode current = head;
        while (current != null) {
            if (current.data.getCustomerId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void display() {
        if (head == null) {
            System.out.println("No customers available.");
            return;
        }
        Customer.printHeader();
        CustomerNode current = head;
        while (current != null) {
            current.data.printRow();
            current = current.next;
        }
    }

    private static class CustomerNode {
        private Customer data;
        private CustomerNode next;

        private CustomerNode(Customer data) {
            this.data = data;
        }
    }
}
