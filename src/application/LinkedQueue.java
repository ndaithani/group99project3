package application;

import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 *  LinkedQueue class holding generic nodes.
 *  @param <T>
 * 
 *  @author naomi
 *  @version Nov 13, 2026
 */
public class LinkedQueue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // ----------------------------------------------------------
    /**
     * Create a new LinkedQueue object.
     */
    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    // ----------------------------------------------------------
    /**
     * tests if empty.
     * @return size == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // ----------------------------------------------------------
    /**
     * Returns size of queue
     * @return size
     */
    public int size() {
        return size;
    }

    // ----------------------------------------------------------
    /**
     * Adds new entry to the back
     * @param newEntry
     */
    public void enqueue(T newEntry) {
        if (newEntry == null) {
            throw new IllegalArgumentException("Cannot enqueue null.");
        }
        Node<T> node = new Node<T>(newEntry);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // ----------------------------------------------------------
    /**
     * Removes oldest entry from the front
     * @return -- old head
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    // ----------------------------------------------------------
    /**
     * Nondestructively returns the head of queue.
     * @return -- head
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}