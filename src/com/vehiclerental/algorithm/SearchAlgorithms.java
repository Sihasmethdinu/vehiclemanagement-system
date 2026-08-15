package com.vehiclerental.algorithm;

import com.vehiclerental.model.Vehicle;

public class SearchAlgorithms {
    public static Vehicle linearSearchById(Vehicle[] arr, int id) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getVehicleId() == id) {
                return arr[i];
            }
        }
        return null;
    }

    public static Vehicle[] linearSearchByType(Vehicle[] arr, String type) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getType().equalsIgnoreCase(type)) {
                count++;
            }
        }

        Vehicle[] result = new Vehicle[count];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getType().equalsIgnoreCase(type)) {
                result[index++] = arr[i];
            }
        }
        return result;
    }
}
