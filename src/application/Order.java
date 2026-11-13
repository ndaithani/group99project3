package application;

import java.util.ArrayList;
import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 *  Represents a customer order in the JavaHouse application, tracking the
 *  customer name, order ID, ordered items, and preparation status.
 * 
 *  @author Naomika
 *  @version Nov 13, 2026
 */
public class Order {

    /**
     * Status constant indicating an order has been placed but not started.
     */
    public static final String NOT_READY = "NOT READY";

    /**
     * Status constant indicating an order is currently being prepared.
     */
    public static final String PREPARING = "PREPARING";

    /**
     * Status constant indicating an order is finished and ready for pickup.
     */
    public static final String COMPLETE = "COMPLETE";

    private static int autoID = 0;

    private String name;
    private int id;
    private Item[] items;
    private ArrayList<Item> draftItems;
    private String status;

    // ----------------------------------------------------------
    /**
     * Creates a new Order object with an explicit ID and an array of items.
     * 
     * @param name
     *            the name of the customer placing the order
     * @param id
     *            the unique identifier assigned to this order
     * @param items
     *            the array of items included in this order
     */
    public Order(String name, int id, Item[] items) {
        if (name == null || name.trim().isEmpty() || items == null) {
            throw new IllegalArgumentException(
                "Order name and items cannot be null.");
        }
        this.name = name;
        this.id = id;
        this.items = Arrays.copyOf(items, items.length);
        this.draftItems = new ArrayList<Item>(Arrays.asList(this.items));
        this.status = NOT_READY;
    }


    // ----------------------------------------------------------
    /**
     * Creates a new Order object with an automatically generated ID.
     * 
     * @param name
     *            the name of the customer placing the order
     * @param items
     *            the list of items included in this order
     */
    public Order(String name, ArrayList<Item> items) {
        if (name == null || items == null) {
            throw new IllegalArgumentException(
                "Order name and items cannot be null.");
        }
        this.name = name;
        this.id = autoID++;
        this.draftItems = items;
        this.items = items.toArray(new Item[0]);
        this.status = NOT_READY;
    }


    // ----------------------------------------------------------
    /**
     * Gets the name of the customer who placed this order.
     * 
     * @return the customer name
     */
    public String getName() {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Gets the unique ID number for this order.
     * 
     * @return the order ID
     */
    public int getID() {
        return id;
    }


    // ----------------------------------------------------------
    /**
     * Gets the items in this order as a modifiable list for the customer
     * window and display iteration.
     * 
     * @return the ArrayList of items in this order
     */
    public ArrayList<Item> getItems() {
        return draftItems;
    }


    // ----------------------------------------------------------
    /**
     * Gets a defensive copy of the items in this order as an array.
     * 
     * @return an array containing the ordered items
     */
    public Item[] getItemsArray() {
        return Arrays.copyOf(items, items.length);
    }


    // ----------------------------------------------------------
    /**
     * Gets the current preparation status of this order.
     * 
     * @return the current status string
     */
    public String getStatus() {
        return status;
    }


    // ----------------------------------------------------------
    /**
     * Updates the status of this order if the provided status is valid.
     * 
     * @param status
     *            the new status to assign (NOT READY, PREPARING, or COMPLETE)
     */
    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        String normalized = status.trim().toUpperCase();
        if (!normalized.equals(NOT_READY)
                && !normalized.equals(PREPARING)
                && !normalized.equals(COMPLETE)) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        this.status = normalized;
    }


    // ----------------------------------------------------------
    /**
     * Returns a formatted string representation of this order including its
     * ID, customer name, and current status.
     * 
     * @return a string describing the order
     */
    @Override
    public String toString() {
        return "Order #" + id + " - " + name + " [" + status + "]";
    }
}