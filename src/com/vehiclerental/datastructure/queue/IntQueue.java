package com.vehiclerental.datastructure.queue;

public class IntQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int count;

    public IntQueue(int capacity) {
        queue = new int[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    public void enqueue(int value) {
        if (count == queue.length) {
            resize();
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = value;
        count++;
    }

    public int dequeue() {
        int value = queue[front];
        front = (front + 1) % queue.length;
        count--;
        return value;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private void resize() {
        int[] newQueue = new int[queue.length * 2];
        for (int i = 0; i < count; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        rear = count - 1;
    }
}
