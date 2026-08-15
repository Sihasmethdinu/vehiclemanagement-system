package com.vehiclerental.datastructure.tree;

import com.vehiclerental.model.Vehicle;

public class VehicleBST {
    private BSTNode root;

    public void insert(Vehicle vehicle) {
        root = insertRecursive(root, vehicle);
    }

    private BSTNode insertRecursive(BSTNode node, Vehicle vehicle) {
        if (node == null) {
            return new BSTNode(vehicle);
        }

        if (vehicle.getVehicleId() < node.data.getVehicleId()) {
            node.left = insertRecursive(node.left, vehicle);
        } else if (vehicle.getVehicleId() > node.data.getVehicleId()) {
            node.right = insertRecursive(node.right, vehicle);
        } else {
            node.data = vehicle;
        }
        return node;
    }

    public Vehicle search(int vehicleId) {
        BSTNode current = root;
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

    private BSTNode deleteRecursive(BSTNode node, int vehicleId) {
        if (node == null) {
            return null;
        }

        if (vehicleId < node.data.getVehicleId()) {
            node.left = deleteRecursive(node.left, vehicleId);
        } else if (vehicleId > node.data.getVehicleId()) {
            node.right = deleteRecursive(node.right, vehicleId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            BSTNode successor = findMinimum(node.right);
            node.data = successor.data;
            node.right = deleteRecursive(node.right, successor.data.getVehicleId());
        }
        return node;
    }

    private BSTNode findMinimum(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void inorder() {
        System.out.println("\nBST Inorder Traversal (Sorted by Vehicle ID):");
        Vehicle.printHeader();
        inorderRecursive(root);
    }

    private void inorderRecursive(BSTNode node) {
        if (node != null) {
            inorderRecursive(node.left);
            node.data.printRow();
            inorderRecursive(node.right);
        }
    }

    public void preorder() {
        System.out.println("\nBST Preorder Traversal:");
        Vehicle.printHeader();
        preorderRecursive(root);
    }

    private void preorderRecursive(BSTNode node) {
        if (node != null) {
            node.data.printRow();
            preorderRecursive(node.left);
            preorderRecursive(node.right);
        }
    }

    public void postorder() {
        System.out.println("\nBST Postorder Traversal:");
        Vehicle.printHeader();
        postorderRecursive(root);
    }

    private void postorderRecursive(BSTNode node) {
        if (node != null) {
            postorderRecursive(node.left);
            postorderRecursive(node.right);
            node.data.printRow();
        }
    }

    private static class BSTNode {
        private Vehicle data;
        private BSTNode left;
        private BSTNode right;

        private BSTNode(Vehicle data) {
            this.data = data;
        }
    }
}
