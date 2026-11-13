package application;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the data of the JavaHouse App.
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
     * Create a new JavaHouseModel object.
     * @param menu
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
     * Get the menu.
     * @return -- menu
     */
    public Item[] getMenu() {
        return menu;
    }

    // ----------------------------------------------------------
    /**
     * Get the order queue.
     * @return -- queue
     */
    public LinkedQueue<Order> getOrderQueue() {
        return queue;
    }

    // ----------------------------------------------------------
    /**
     * Get the pickup list.
     * @return -- pickup
     */
    public DoublyLinkedList<Order> getPickupList() {
        return pickup;
    }

    // ----------------------------------------------------------
    /**
     * Create and submit an order.
     * @return -- a new order
     */
    public Order submitOrder(String name, ArrayList<Item> items) {
        if (name == null || name.trim().isEmpty() || items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Please complete your order!");
        }
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).inStock()) {
                throw new IllegalStateException(items.get(i).getName() + " is out of stock!");
            }
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).decrementStock();
        }
        Order newOrder = new Order(name.trim(), lastID, items);
        queue.enqueue(newOrder);
        lastID++;
        return newOrder;
    }

    public Order completeOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Order queue is empty.");
        }
        Order completed = queue.dequeue();
        completed.setStatus("COMPLETE");
        pickup.add(completed);
        return completed;
    }

    public Order nextOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Order queue is empty.");
        }
        Order next = queue.peek();
        next.setStatus("PREPARING");
        return next;
    }

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
                                i, menu[i].getName(), menu[i].getPrice(), menu[i].getStock());
                        }
                        break;

                    case "2":
                        System.out.print("Enter customer name: ");
                        String custName = scanner.nextLine();
                        ArrayList<Item> cart = new ArrayList<>();

                        while (true) {
                            System.out.print("Enter item index (0-" + (menu.length - 1) + ") or 'done': ");
                            String input = scanner.nextLine().trim();
                            if (input.equalsIgnoreCase("done")) {
                                break;
                            }
                            try {
                                int idx = Integer.parseInt(input);
                                if (idx < 0 || idx >= menu.length) {
                                    System.out.println("Invalid menu index.");
                                } else {
                                    cart.add(menu[idx]);
                                    System.out.println("Added: " + menu[idx].getName());
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid number or 'done'.");
                            }
                        }

                        Order created = model.submitOrder(custName, cart);
                        System.out.println("Order submitted! " + created);
                        break;

                    case "3":
                        Order preparing = model.nextOrder();
                        System.out.println("Now preparing: " + preparing);
                        break;

                    case "4":
                        Order completed = model.completeOrder();
                        System.out.println("Completed & moved to pick-up: " + completed);
                        break;

                    case "5":
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Customer Name: ");
                        String pickupName = scanner.nextLine();
                        if (model.pickUpOrder(id, pickupName)) {
                            System.out.println("Order #" + id + " picked up!");
                        } else {
                            System.out.println("No matching order found in pick-up list.");
                        }
                        break;

                    case "6":
                        System.out.println("Order Queue: " + model.getOrderQueue());
                        System.out.println("Pick-Up List: " + model.getPickupList());
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