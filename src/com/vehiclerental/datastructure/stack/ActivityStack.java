package com.vehiclerental.datastructure.stack;

public class ActivityStack {
    private String[] stack;
    private int top;

    public ActivityStack(int capacity) {
        stack = new String[capacity];
        top = -1;
    }

    public void push(String activity) {
        if (top == stack.length - 1) {
            resize();
        }
        stack[++top] = activity;
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String value = stack[top];
        stack[top] = null;
        top--;
        return value;
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Activity stack is empty.");
            return;
        }
        System.out.println("Top -> Bottom");
        for (int i = top; i >= 0; i--) {
            System.out.println((top - i + 1) + ". " + stack[i]);
        }
    }

    private void resize() {
        String[] newStack = new String[stack.length * 2];
        for (int i = 0; i < stack.length; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }
}
