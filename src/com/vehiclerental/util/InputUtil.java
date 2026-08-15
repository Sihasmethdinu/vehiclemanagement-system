package com.vehiclerental.util;

import java.util.Scanner;

public class InputUtil {
    private Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number. Try again.");
            }
        }
    }
}
