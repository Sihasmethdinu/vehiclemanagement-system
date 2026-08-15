package com.vehiclerental.datastructure.hash;

import com.vehiclerental.model.Vehicle;

public class VehicleHashTable {
    private HashEntry[] table;
    private int count;

    public VehicleHashTable(int capacity) {
        table = new HashEntry[capacity];
        count = 0;
    }

    public void put(String key, Vehicle value) {
        if ((double) count / table.length > 0.70) {
            resize();
        }

        String normalizedKey = normalize(key);
        int index = hash(normalizedKey);
        int firstDeletedIndex = -1;

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            HashEntry entry = table[probeIndex];

            if (entry == null) {
                if (firstDeletedIndex != -1) {
                    probeIndex = firstDeletedIndex;
                }
                table[probeIndex] = new HashEntry(normalizedKey, value);
                count++;
                return;
            }

            if (entry.deleted) {
                if (firstDeletedIndex == -1) {
                    firstDeletedIndex = probeIndex;
                }
            } else if (entry.key.equals(normalizedKey)) {
                entry.value = value;
                return;
            }
        }

        if (firstDeletedIndex != -1) {
            table[firstDeletedIndex] = new HashEntry(normalizedKey, value);
            count++;
        }
    }

    public Vehicle get(String key) {
        String normalizedKey = normalize(key);
        int index = hash(normalizedKey);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            HashEntry entry = table[probeIndex];

            if (entry == null) {
                return null;
            }

            if (!entry.deleted && entry.key.equals(normalizedKey)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean remove(String key) {
        String normalizedKey = normalize(key);
        int index = hash(normalizedKey);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            HashEntry entry = table[probeIndex];

            if (entry == null) {
                return false;
            }

            if (!entry.deleted && entry.key.equals(normalizedKey)) {
                entry.deleted = true;
                entry.value = null;
                count--;
                return true;
            }
        }
        return false;
    }

    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue = (hashValue * 31 + key.charAt(i)) % table.length;
        }
        return hashValue;
    }

    private String normalize(String key) {
        return key.trim().toUpperCase();
    }

    public void display() {
        System.out.println("\n--- Vehicle Hash Table ---");
        System.out.println("Collision handling method: Linear Probing");
        System.out.printf("%-8s %-15s %-10s%n", "Index", "Key", "Vehicle ID");
        System.out.println("-------------------------------------");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].deleted) {
                System.out.printf("%-8d %-15s %-10d%n", i, table[i].key, table[i].value.getVehicleId());
            }
        }
    }

    private void resize() {
        HashEntry[] oldTable = table;
        table = new HashEntry[oldTable.length * 2 + 1];
        count = 0;

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].deleted) {
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }

    private static class HashEntry {
        private String key;
        private Vehicle value;
        private boolean deleted;

        private HashEntry(String key, Vehicle value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }
}
