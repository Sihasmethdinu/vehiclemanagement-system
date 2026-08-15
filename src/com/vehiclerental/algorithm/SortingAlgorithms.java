package com.vehiclerental.algorithm;

import com.vehiclerental.model.Vehicle;

public class SortingAlgorithms {
    public static Vehicle[] copyVehicleArray(Vehicle[] original) {
        Vehicle[] copy = new Vehicle[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static void displayVehicleArray(Vehicle[] arr) {
        Vehicle.printHeader();
        for (int i = 0; i < arr.length; i++) {
            arr[i].printRow();
        }
    }

    public static void applySort(String sortName, Vehicle[] arr) {
        if (sortName.equals("Bubble Sort")) {
            bubbleSort(arr);
        } else if (sortName.equals("Selection Sort")) {
            selectionSort(arr);
        } else if (sortName.equals("Insertion Sort")) {
            insertionSort(arr);
        } else if (sortName.equals("Merge Sort")) {
            mergeSort(arr, 0, arr.length - 1);
        } else if (sortName.equals("Quick Sort")) {
            quickSort(arr, 0, arr.length - 1);
        }
    }

    private static int compareByDailyRate(Vehicle a, Vehicle b) {
        if (a.getDailyRate() < b.getDailyRate()) {
            return -1;
        }
        if (a.getDailyRate() > b.getDailyRate()) {
            return 1;
        }
        return a.getVehicleId() - b.getVehicleId();
    }

    public static void bubbleSort(Vehicle[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (compareByDailyRate(arr[j], arr[j + 1]) > 0) {
                    Vehicle temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static void selectionSort(Vehicle[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (compareByDailyRate(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            Vehicle temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void insertionSort(Vehicle[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Vehicle key = arr[i];
            int j = i - 1;
            while (j >= 0 && compareByDailyRate(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void mergeSort(Vehicle[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Vehicle[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Vehicle[] leftArr = new Vehicle[n1];
        Vehicle[] rightArr = new Vehicle[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (compareByDailyRate(leftArr[i], rightArr[j]) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < n1) {
            arr[k++] = leftArr[i++];
        }
        while (j < n2) {
            arr[k++] = rightArr[j++];
        }
    }

    public static void quickSort(Vehicle[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(Vehicle[] arr, int low, int high) {
        Vehicle pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compareByDailyRate(arr[j], pivot) <= 0) {
                i++;
                Vehicle temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Vehicle temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
