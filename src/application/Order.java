package application;

import java.util.ArrayList;

public class Order {
    private static int nextAutoID = 1;

    private final String name;
    private final int id;
    private final ArrayList<Item> items;
    private String status;

    public Order(String name, int id, ArrayList<Item> items) {
        if (name == null || name.trim().isEmpty() || items == null) {
            throw new IllegalArgumentException("Order name and items cannot be null.");
        }
        this.name = name;
        this.id = id;
        this.items = new ArrayList<Item>(items);
        this.status = "NOT READY";
    }

    public Order(String name, ArrayList<Item> items) {
        this(name, nextAutoID++, items);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<Item>(items);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        String normalized = status.trim().toUpperCase();
        if (!normalized.equals("NOT READY")
                && !normalized.equals("PREPARING")
                && !normalized.equals("COMPLETE")) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        this.status = normalized;
    }

    @Override
    public String toString() {
        return "Order #" + id + " - " + name + " [" + status + "]";
    }
}