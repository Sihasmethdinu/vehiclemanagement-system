package com.vehiclerental.datastructure.tree;

import com.vehiclerental.model.Vehicle;

public class VehicleAVLTree {
    private AVLNode root;

    public void insert(Vehicle vehicle) {
        root = insertRecursive(root, vehicle);
    }

    private AVLNode insertRecursive(AVLNode node, Vehicle vehicle) {
        if (node == null) {
            return new AVLNode(vehicle);
        }

        if (vehicle.getVehicleId() < node.data.getVehicleId()) {
            node.left = insertRecursive(node.left, vehicle);
        } else if (vehicle.getVehicleId() > node.data.getVehicleId()) {
            node.right = insertRecursive(node.right, vehicle);
        } else {
            node.data = vehicle;
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    public Vehicle search(int vehicleId) {
        AVLNode current = root;
        while (current != null) {
            if (vehicleId == current.data.getVehicleId()) {
                return current.data;
            } else if (vehicleId < current.data.getVehicleId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public void delete(int vehicleId) {
        root = deleteRecursive(root, vehicleId);
    }

    private AVLNode deleteRecursive(AVLNode node, int vehicleId) {
        if (node == null) {
            return null;
        }

        if (vehicleId < node.data.getVehicleId()) {
            node.left = deleteRecursive(node.left, vehicleId);
        } else if (vehicleId > node.data.getVehicleId()) {
            node.right = deleteRecursive(node.right, vehicleId);
        } else {
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode successor = findMinimum(node.right);
                node.data = successor.data;
                node.right = deleteRecursive(node.right, successor.data.getVehicleId());
            }
        }

        if (node == null) {
            return null;
        }

        updateHeight(node);
        return balance(node);
    }

    private AVLNode findMinimum(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private AVLNode balance(AVLNode node) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int getBalanceFactor(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    public void inorder() {
        System.out.println("\nAVL Inorder Traversal (Sorted by Vehicle ID):");
        Vehicle.printHeader();
        inorderRecursive(root);
    }

    private void inorderRecursive(AVLNode node) {
        if (node != null) {
            inorderRecursive(node.left);
            node.data.printRow();
            inorderRecursive(node.right);
        }
    }

    public void preorder() {
        System.out.println("\nAVL Preorder Traversal:");
        Vehicle.printHeader();
        preorderRecursive(root);
    }

    private void preorderRecursive(AVLNode node) {
        if (node != null) {
            node.data.printRow();
            preorderRecursive(node.left);
            preorderRecursive(node.right);
        }
    }

    public void postorder() {
        System.out.println("\nAVL Postorder Traversal:");
        Vehicle.printHeader();
        postorderRecursive(root);
    }

    private void postorderRecursive(AVLNode node) {
        if (node != null) {
            postorderRecursive(node.left);
            postorderRecursive(node.right);
            node.data.printRow();
        }
    }

    private static class AVLNode {
        private Vehicle data;
        private AVLNode left;
        private AVLNode right;
        private int height;

        private AVLNode(Vehicle data) {
            this.data = data;
            this.height = 1;
        }
    }
}
