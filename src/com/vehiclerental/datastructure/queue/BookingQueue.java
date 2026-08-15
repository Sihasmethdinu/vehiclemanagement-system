package com.vehiclerental.datastructure.queue;

import com.vehiclerental.model.BookingRequest;

public class BookingQueue {
    private BookingRequest[] queue;
    private int front;
    private int rear;
    private int count;

    public BookingQueue(int capacity) {
        queue = new BookingRequest[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    public void enqueue(BookingRequest request) {
        if (count == queue.length) {
            resize();
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = request;
        count++;
        System.out.println("Request inserted to rear of queue.");
    }

    public BookingRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        BookingRequest request = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        return request;
    }

    public BookingRequest peek() {
        if (isEmpty()) {
            return null;
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Booking queue is empty.");
            return;
        }
        System.out.println("Front -> Rear");
        for (int i = 0; i < count; i++) {
            int index = (front + i) % queue.length;
            System.out.println((i + 1) + ". " + queue[index]);
        }
    }

    private void resize() {
        BookingRequest[] newQueue = new BookingRequest[queue.length * 2];
        for (int i = 0; i < count; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        rear = count - 1;
    }
}
