package com.goatdev.util;

import java.util.HashMap;

/**
 * A queue with a hashmap.
 * Created by ran on 4/20/16.
 */
public class HashQueue<KeyType, DataType> {

    private HashMap<KeyType, Node<KeyType, DataType>> nodeMapping;
    private Node<KeyType, DataType> head;
    private Node<KeyType, DataType> tail;

    private int size;

    public HashQueue() {
        nodeMapping = new HashMap<>();
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(KeyType key, DataType data) {
        Node<KeyType, DataType> newNode = new Node<>(key, data);

        // If we're empty, populate the pointers.
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            // Otherwise, we do this.
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        nodeMapping.put(key, newNode);
        size++;
    }

    public DataType peek() {
        if (head != null) return head.data;
        return null;
    }

    public DataType dequeue() {
        if (head == null) return null;

        size--;
        Node<KeyType, DataType> toRemove = head;

        if (tail == head) tail = null;
        head = head.next;
        if (head != null) head.previous = null;

        nodeMapping.remove(toRemove.key);
        return toRemove.data;
    }

    public boolean containsKey(KeyType key) {
        return nodeMapping.containsKey(key);
    }

    public DataType remove(KeyType key) {
        Node<KeyType, DataType> toRemove = nodeMapping.remove(key);
        if (toRemove == null) return null;

        size--;
        if (head == toRemove) head = toRemove.next;
        if (tail == toRemove) tail = toRemove.previous;

        if (toRemove.previous != null) toRemove.previous.next = toRemove.next;
        if (toRemove.next != null) toRemove.next.previous = toRemove.previous;

        return toRemove.data;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<KeyType, DataType> {
        Node<KeyType, DataType> previous;
        Node<KeyType, DataType> next;
        KeyType key;
        DataType data;

        Node(KeyType key, DataType data) {
            this.key = key;
            this.data = data;
            previous = null;
            next = null;
        }
    }
}
