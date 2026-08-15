package com.vehiclerental.datastructure.hash;

public class StringSet {
    private SetEntry[] table;
    private int count;

    public StringSet(int capacity) {
        table = new SetEntry[capacity];
        count = 0;
    }

    public boolean add(String value) {
        if (contains(value)) {
            return false;
        }

        if ((double) count / table.length > 0.70) {
            resize();
        }

        String normalizedValue = normalize(value);
        int index = hash(normalizedValue);
        int firstDeletedIndex = -1;

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            SetEntry entry = table[probeIndex];

            if (entry == null) {
                if (firstDeletedIndex != -1) {
                    probeIndex = firstDeletedIndex;
                }
                table[probeIndex] = new SetEntry(normalizedValue);
                count++;
                return true;
            }

            if (entry.deleted && firstDeletedIndex == -1) {
                firstDeletedIndex = probeIndex;
            }
        }

        if (firstDeletedIndex != -1) {
            table[firstDeletedIndex] = new SetEntry(normalizedValue);
            count++;
            return true;
        }
        return false;
    }

    public boolean contains(String value) {
        String normalizedValue = normalize(value);
        int index = hash(normalizedValue);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            SetEntry entry = table[probeIndex];

            if (entry == null) {
                return false;
            }

            if (!entry.deleted && entry.value.equals(normalizedValue)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(String value) {
        String normalizedValue = normalize(value);
        int index = hash(normalizedValue);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            SetEntry entry = table[probeIndex];

            if (entry == null) {
                return false;
            }

            if (!entry.deleted && entry.value.equals(normalizedValue)) {
                entry.deleted = true;
                count--;
                return true;
            }
        }
        return false;
    }

    private int hash(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++) {
            hashValue = (hashValue * 31 + value.charAt(i)) % table.length;
        }
        return hashValue;
    }

    private String normalize(String value) {
        return value.trim().toUpperCase();
    }

    private void resize() {
        SetEntry[] oldTable = table;
        table = new SetEntry[oldTable.length * 2 + 1];
        count = 0;

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].deleted) {
                add(oldTable[i].value);
            }
        }
    }

    private static class SetEntry {
        private String value;
        private boolean deleted;

        private SetEntry(String value) {
            this.value = value;
            this.deleted = false;
        }
    }
}
