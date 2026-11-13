
package application;

import java.util.ArrayList;

/**
 * Represents a customer's JavaHouse order.
 *
 * @author Daana
 * @version Nov 12, 2026
 */
public class Order
{
    public static final String NOT_READY = "NOT READY";
    public static final String PREPARING = "PREPARING";
    public static final String COMPLETE = "COMPLETE";

    private String name;
    private int id;
    private ArrayList<Item> items;
    private String status;

    /**
     * Creates an order with a name and item list.
     *
     * @param name the customer's name
     * @param items the items in the order
     */
    public Order(String name, ArrayList<Item> items)
    {
        this(name, -1, items);
    }

    /**
     * Creates an order with a name, ID, and item array.
     *
     * @param name the customer's name
     * @param id the order ID
     * @param items the items in the order
     */
    public Order(String name, int id, Item[] items)
    {
        this.name = name;
        this.id = id;
        this.items = new ArrayList<>();

        if (items != null)
        {
            for (Item item : items)
            {
                this.items.add(item);
            }
        }

        this.status = NOT_READY;
    }

    private Order(String name, int id, ArrayList<Item> items)
    {
        this.name = name;
        this.id = id;
        this.items = new ArrayList<>(items);
        this.status = NOT_READY;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return id;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public String getStatus()
    {
        return status;
    }

    /**
     * Updates the order status.
     *
     * @param status the new order status
     */
    public void setStatus(String status)
    {
        if (!NOT_READY.equals(status)
            && !PREPARING.equals(status)
            && !COMPLETE.equals(status))
        {
            throw new IllegalArgumentException("Invalid order status.");
        }

        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Order #" + id + " - " + name;
    }
}
