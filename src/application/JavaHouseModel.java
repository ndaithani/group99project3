package application;

import java.util.Arrays;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Manages the data of the JavaHouse App, including the menu, active order
 * queue, and completed pickup list.
 *
 * @author Naomika Aithani
 * @version Nov 12, 2026
 */
public class JavaHouseModel {
    private Item[] menu;
    private LinkedQueue<Order> queue;
    private DoublyLinkedList<Order> pickup;
    private int lastID = 0;

    // ----------------------------------------------------------
    /**
     * Creates a new JavaHouseModel object with the given menu.
     * 
     * @param menu
     *            the array of menu items available at JavaHouse
     */
    public JavaHouseModel(Item[] menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu cannot be null.");
        }
        this.menu = menu;
        this.queue = new LinkedQueue<Order>();
        this.pickup = new DoublyLinkedList<Order>();
    }


    // ----------------------------------------------------------
    /**
     * Gets the array of menu items.
     * 
     * @return the menu array
     */
    public Item[] getMenu() {
        return menu;
    }


    // ----------------------------------------------------------
    /**
     * Gets the queue of orders waiting to be prepared or in progress.
     * 
     * @return the order queue
     */
    public LinkedQueue<Order> getOrderQueue() {
        return queue;
    }


    // ----------------------------------------------------------
    /**
     * Gets the list of completed orders ready for customer pickup.
     * 
     * @return the pickup list
     */
    public DoublyLinkedList<Order> getPickupList() {
        return pickup;
    }


    // ----------------------------------------------------------
    /**
     * Validates stock, decrements inventory, creates a new order from an
     * Item array of any length, and adds it to the order queue.
     * 
     * @param name
     *            the customer's name
     * @param items
     *            the array of ordered items
     * @return the newly created Order
     */
    public Order submitOrder(String name, Item[] items) {
        if (name == null || name.trim().isEmpty() || items == null
            || items.length == 0) {
            throw new IllegalArgumentException("Please complete your order!");
        }

        // Validate every item and check stock against requested quantities
        for (int i = 0; i < items.length; i++) {
            Item current = items[i];
            if (current == null) {
                throw new IllegalArgumentException(
                    "Order cannot contain null items.");
            }
            if (!current.inStock()) {
                throw new IllegalStateException(
                    current.getName() + " is out of stock!");
            }
            int requested = 0;
            for (int j = 0; j < items.length; j++) {
                if (items[j] == current) {
                    requested++;
                }
            }
            if (requested > current.getStock()) {
                throw new IllegalStateException(
                    current.getName() + " is out of stock!");
            }
        }

        // Decrement stock for each ordered item
        for (int i = 0; i < items.length; i++) {
            items[i].decrementStock();
        }

        Order newOrder = new Order(name.trim(), lastID, items);
        queue.enqueue(newOrder);
        lastID++;
        return newOrder;
    }


    // ----------------------------------------------------------
    /**
     * Completes the order at the front of the queue, marks it complete, and
     * moves it to the pickup list.
     * 
     * @return the completed Order
     */
    public Order completeOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Order queue is empty.");
        }
        Order completed = queue.dequeue();
        completed.setStatus(Order.COMPLETE);
        pickup.add(completed);
        return completed;
    }


    // ----------------------------------------------------------
    /**
     * Marks the next order at the front of the queue as currently preparing.
     * 
     * @return the Order being prepared
     */
    public Order nextOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Order queue is empty.");
        }
        Order next = queue.peek();
        next.setStatus(Order.PREPARING);
        return next;
    }


    // ----------------------------------------------------------
    /**
     * Removes a completed order from the pickup list when the ID and customer
     * name match.
     * 
     * @param id
     *            the order ID to pick up
     * @param name
     *            the customer name associated with the order
     * @return true if the matching order was found and removed, false otherwise
     */
    public boolean pickUpOrder(int id, String name) {
        if (name == null) {
            return false;
        }
        for (int i = 0; i < pickup.size(); i++) {
            Order o = pickup.get(i);
            if (o.getID() == id && o.getName().equalsIgnoreCase(name.trim())) {
                pickup.remove(o);
                return true;
            }
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Runs a standalone console test mode using dynamically resized Item
     * arrays.
     * 
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
        Item[] menu = new Item[10];
        menu[0] = new Item("Hot Coffee", "Hot Drink", 4.00, 100);
        menu[1] = new Item("Hot Latte", "Hot Drink", 6.00, 100);
        menu[2] = new Item("Hot Chocolate", "Hot Drink", 3.00, 100);
        menu[3] = new Item("Iced Coffee", "Cold Drink", 4.00, 100);
        menu[4] = new Item("Iced Latte", "Cold Drink", 6.00, 100);
        menu[5] = new Item("Blended Coffee", "Cold Drink", 5.50, 100);
        menu[6] = new Item("Croissant", "Snack", 5.00, 75);
        menu[7] = new Item("Chocolate Croissant", "Snack", 5.00, 50);
        menu[8] = new Item("Donut", "Snack", 5.00, 75);
        menu[9] = new Item("Donut Hole (12)", "Snack", 7.00, 40);

        JavaHouseModel model = new JavaHouseModel(menu);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to JavaHouse (Standalone Console Mode)");

        while (running) {
            System.out.println("\n--- MENU ACTIONS ---");
            System.out.println("1. View Menu");
            System.out.println("2. Submit Customer Order");
            System.out.println("3. Barista: Start Next Order");
            System.out.println("4. Barista: Complete Current Order");
            System.out.println("5. Customer: Pick Up Order");
            System.out.println("6. View Queue & Pick-Up List");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        for (int i = 0; i < menu.length; i++) {
                            System.out.printf("[%d] %s - $%.2f (Stock: %d)%n",
                                i, menu[i].getName(), menu[i].getPrice(),
                                menu[i].getStock());
                        }
                        break;

                    case "2":
                        System.out.print("Enter customer name: ");
                        String custName = scanner.nextLine();
                        Item[] cart = new Item[4];
                        int itemCount = 0;

                        while (true) {
                            System.out.print("Enter item index (0-"
                                + (menu.length - 1) + ") or 'done': ");
                            String input = scanner.nextLine().trim();
                            if (input.equalsIgnoreCase("done")) {
                                break;
                            }
                            try {
                                int idx = Integer.parseInt(input);
                                if (idx < 0 || idx >= menu.length) {
                                    System.out.println("Invalid menu index.");
                                } else {
                                    if (itemCount == cart.length) {
                                        cart = Arrays.copyOf(
                                            cart, cart.length * 2);
                                    }
                                    cart[itemCount++] = menu[idx];
                                    System.out.println(
                                        "Added: " + menu[idx].getName());
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(
                                    "Please enter a valid number or 'done'.");
                            }
                        }

                        Item[] finalCart = Arrays.copyOf(cart, itemCount);
                        Order created = model.submitOrder(custName, finalCart);
                        System.out.println("Order submitted! " + created);
                        break;

                    case "3":
                        Order preparing = model.nextOrder();
                        System.out.println("Now preparing: " + preparing);
                        break;

                    case "4":
                        Order completed = model.completeOrder();
                        System.out.println(
                            "Completed & moved to pick-up: " + completed);
                        break;

                    case "5":
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Customer Name: ");
                        String pickupName = scanner.nextLine();
                        if (model.pickUpOrder(id, pickupName)) {
                            System.out.println("Order #" + id + " picked up!");
                        } else {
                            System.out.println(
                                "No matching order found in pick-up list.");
                        }
                        break;

                    case "6":
                        System.out.println(
                            "Order Queue: " + model.getOrderQueue());
                        System.out.println(
                            "Pick-Up List: " + model.getPickupList());
                        break;

                    case "7":
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Enter 1-7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number entered.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Action rejected: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Thank you for using JavaHouse!");
    }
}