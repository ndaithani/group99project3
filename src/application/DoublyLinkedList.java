package application;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @param <E>
 * @author naomi
 * @version Nov 6, 2026
 */
public class DoublyLinkedList<E>
{

    // Internal Node class holding the data and pointers
    private static class Node<E>
    {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data)
        {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    // Get the current number of elements in the list
    public int size()
    {
        return size;
    }


    // Check if the list is empty
    public boolean isEmpty()
    {
        return size == 0;
    }


    // Insert an item at the end of the list
    public void add(E element)
    {
        Node<E> newNode = new Node<>(element);
        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }


    // Remove and return the first item
    public E removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("List is empty");
        }
        E data = head.data;
        if (head == tail)
        { // Only one element in the list
            head = null;
            tail = null;
        }
        else
        {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }


    // Remove and return the last item
    public E removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("List is empty");
        }
        E data = tail.data;
        if (head == tail)
        { // Only one element in the list
            head = null;
            tail = null;
        }
        else
        {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }


    /**
     * Returns the element at the given index, throwing
     * IndexOutOfBoundsException if the index is invalid.
     *
     * @param index
     *            the position of the element
     * @return the element at that position
     */
    public E get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.data;
    }


    // Display the list from head to tail
    public void printForward()
    {
        Node<E> current = head;
        System.out.print("Forward: ");
        while (current != null)
        {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }


    // Display the list from tail to head
    public void printBackward()
    {
        Node<E> current = tail;
        System.out.print("Backward: ");
        while (current != null)
        {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }

}
