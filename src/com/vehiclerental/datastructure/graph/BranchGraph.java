package com.vehiclerental.datastructure.graph;

import com.vehiclerental.datastructure.queue.IntQueue;

public class BranchGraph {
    private String[] vertices;
    private NeighborNode[] adjacencyList;
    private int vertexCount;

    public BranchGraph(int capacity) {
        vertices = new String[capacity];
        adjacencyList = new NeighborNode[capacity];
        vertexCount = 0;
    }

    public void addVertex(String branch) {
        if (indexOf(branch) != -1) {
            return;
        }
        if (vertexCount == vertices.length) {
            resize();
        }
        vertices[vertexCount] = branch;
        adjacencyList[vertexCount] = null;
        vertexCount++;
    }

    public void addEdge(String branch1, String branch2) {
        int index1 = indexOf(branch1);
        int index2 = indexOf(branch2);

        if (index1 == -1 || index2 == -1) {
            System.out.println("Cannot add edge. Branch not found.");
            return;
        }

        adjacencyList[index1] = new NeighborNode(index2, adjacencyList[index1]);
        adjacencyList[index2] = new NeighborNode(index1, adjacencyList[index2]);
    }

    private int indexOf(String branch) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equalsIgnoreCase(branch)) {
                return i;
            }
        }
        return -1;
    }

    public void display() {
        System.out.println("\n--- Branch Graph Adjacency List ---");
        for (int i = 0; i < vertexCount; i++) {
            System.out.print(vertices[i] + " -> ");
            NeighborNode current = adjacencyList[i];
            while (current != null) {
                System.out.print(vertices[current.vertexIndex]);
                if (current.next != null) {
                    System.out.print(" -> ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    public void bfs(String startBranch) {
        int startIndex = indexOf(startBranch);
        if (startIndex == -1) {
            System.out.println("Start branch not found.");
            return;
        }

        boolean[] visited = new boolean[vertexCount];
        IntQueue queue = new IntQueue(vertexCount + 5);

        visited[startIndex] = true;
        queue.enqueue(startIndex);

        System.out.print("BFS Traversal: ");
        while (!queue.isEmpty()) {
            int currentIndex = queue.dequeue();
            System.out.print(vertices[currentIndex] + " ");

            NeighborNode neighbor = adjacencyList[currentIndex];
            while (neighbor != null) {
                int nextIndex = neighbor.vertexIndex;
                if (!visited[nextIndex]) {
                    visited[nextIndex] = true;
                    queue.enqueue(nextIndex);
                }
                neighbor = neighbor.next;
            }
        }
        System.out.println();
    }

    public void dfs(String startBranch) {
        int startIndex = indexOf(startBranch);
        if (startIndex == -1) {
            System.out.println("Start branch not found.");
            return;
        }

        boolean[] visited = new boolean[vertexCount];
        System.out.print("DFS Traversal: ");
        dfsRecursive(startIndex, visited);
        System.out.println();
    }

    private void dfsRecursive(int index, boolean[] visited) {
        visited[index] = true;
        System.out.print(vertices[index] + " ");

        NeighborNode neighbor = adjacencyList[index];
        while (neighbor != null) {
            int nextIndex = neighbor.vertexIndex;
            if (!visited[nextIndex]) {
                dfsRecursive(nextIndex, visited);
            }
            neighbor = neighbor.next;
        }
    }

    private void resize() {
        String[] newVertices = new String[vertices.length * 2];
        NeighborNode[] newAdjacencyList = new NeighborNode[adjacencyList.length * 2];

        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];
            newAdjacencyList[i] = adjacencyList[i];
        }

        vertices = newVertices;
        adjacencyList = newAdjacencyList;
    }

    private static class NeighborNode {
        private int vertexIndex;
        private NeighborNode next;

        private NeighborNode(int vertexIndex, NeighborNode next) {
            this.vertexIndex = vertexIndex;
            this.next = next;
        }
    }
}
