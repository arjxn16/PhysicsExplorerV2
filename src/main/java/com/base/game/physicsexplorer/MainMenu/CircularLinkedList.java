package com.base.game.physicsexplorer.MainMenu;

public class CircularLinkedList<T> {
    private Node<T> head; // head of list
    private Node<T> tail; // tail of list
    private int size; // number of elements in the list

    // Node class
    private static class Node<T> {
        T data;
        Node<T> next;

        // Constructor to create a new node
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Method to add a new node at the end of the list
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head; // circular link
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // circular link
        }
        size++;
    }

    // Method to get the size of the list
    public int size() {
        return size;
    }

    // Method to get the element at a specific position
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current.data;
            }
            count++;
            current = current.next;
        }
        return null;
    }

    // Method to clear the list
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // Method to add a new node with a double value
    public void add(double data) {
        addLast((T) Double.valueOf(data));
    }
}