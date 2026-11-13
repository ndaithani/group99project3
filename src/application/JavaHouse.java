package application;

import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 * Controls communication between the JavaHouse model and its windows.
 * Handles customer and barista actions, validates input, and refreshes
 * the windows when the model changes.
 *
 * @author Daana Doddabendigere
 * @version Nov 12, 2026
 */
public class JavaHouse
{
    // Fields
    private JavaHouseModel model;
    private CustomerWindow customerWindow;
    private BaristaWindow baristaWindow;


    /**
     * Creates the model and both application windows.
     */
    public JavaHouse()
    {
        model = new JavaHouseModel(buildMenu());

        Item[] menu = model.getMenu();
        String[] menuLabels = new String[menu.length];
        for (int i = 0; i < menu.length; i++)
        {
            menuLabels[i] = menu[i].toString();
        }

        customerWindow = new CustomerWindow(menuLabels);
        baristaWindow = new BaristaWindow();

        customerWindow.setController(this);
        baristaWindow.setController(this);

        refreshAll();
    }


    /**
     * Adds the selected menu item to the customer's cart.
     *
     * @param index
     *            the selected menu item index
     */
    public void handleAddItem(int index)
    {
        Item[] menu = model.getMenu();

        // Make sure the selected index is valid.
        if (index < 0 || index >= menu.length)
        {
            customerWindow.showMessage(
                "Please select an item from the menu.");
            return;
        }

        Item selectedItem = menu[index];

        // Make sure the item is in stock.
        if (!selectedItem.inStock())
        {
            customerWindow.showMessage(
                selectedItem.getName() + " is out of stock.");
            return;
        }

        ArrayList<Integer> preview = new ArrayList<>(customerWindow.cartIndices);
        preview.add(index);

        customerWindow.addItemToOrder(
            index,
            selectedItem.toString(),
            calculateCartTotal(preview));
    }


    /**
     * Calculates the total price for a list of selected menu indices.
     *
     * @param indices
     *            the selected menu indices
     * @return the total dollar amount
     */
    public double calculateCartTotal(ArrayList<Integer> indices)
    {
        Item[] menu = model.getMenu();
        double total = 0;
        for (int idx : indices)
        {
            if (idx >= 0 && idx < menu.length)
            {
                total += menu[idx].getPrice();
            }
        }
        return total;
    }


    /**
     * Submits the customer's order after validating the name and selected indices.
     *
     * @param name
     *            the customer's name
     * @param indices
     *            the selected menu item indices in the customer's cart
     */
    public void handleSubmitOrder(String name, ArrayList<Integer> indices)
    {
        // Check that a customer name was entered.
        if (name == null || name.trim().isEmpty())
        {
            customerWindow.showMessage(
                "Please enter your name.");
            return;
        }

        // Check that the cart contains at least one item.
        if (indices == null || indices.isEmpty())
        {
            customerWindow.showMessage(
                "Please add at least one item to your order.");
            return;
        }

        Item[] menu = model.getMenu();
        Item[] submittedItems = new Item[indices.size()];

        // Make sure every index is valid and the corresponding item is in stock.
        for (int i = 0; i < indices.size(); i++)
        {
            int idx = indices.get(i);
            if (idx < 0 || idx >= menu.length)
            {
                customerWindow.showMessage(
                    "Invalid menu selection.");
                return;
            }

            if (!menu[idx].inStock())
            {
                customerWindow.showMessage(
                    menu[idx].getName() + " is out of stock.");
                return;
            }

            submittedItems[i] = menu[idx];
        }

        try
        {
            model.submitOrder(name.trim(), submittedItems);

            // Clear the customer's cart only after a successful submission.
            customerWindow.clearOrder();

            refreshAll();

            customerWindow.showMessage(
                "Your order has been submitted successfully!");
        }
        catch (IllegalArgumentException e)
        {
            customerWindow.showMessage(
                "Unable to submit your order. Please check your information.");
        }
        catch (IllegalStateException e)
        {
            customerWindow.showMessage(
                "One or more items are out of stock. Please update your order.");
        }
    }


    /**
     * Starts preparing the first order in the queue.
     */
    public void handleNextOrder()
    {
        if (model.getOrderQueue().isEmpty())
        {
            baristaWindow.showMessage(
                "There are no orders waiting.");
            return;
        }

        try
        {
            model.nextOrder();
            refreshAll();
        }
        catch (IllegalStateException e)
        {
            baristaWindow.showMessage(
                "There are no orders waiting.");
        }
    }


    /**
     * Completes the order currently being prepared.
     */
    public void handleCompleteOrder()
    {
        if (model.getOrderQueue().isEmpty())
        {
            baristaWindow.showMessage(
                "There is no order to complete.");
            return;
        }

        try
        {
            model.completeOrder();
            refreshAll();

            baristaWindow.showMessage(
                "Order marked as complete.");
        }
        catch (IllegalStateException e)
        {
            baristaWindow.showMessage(
                "There is no order to complete.");
        }
    }


    /**
     * Looks up the order at the given pickup list index and processes pickup.
     *
     * @param index
     *            the selected index in the pickup list
     */
    public void handlePickUpOrderAtIndex(int index)
    {
        DoublyLinkedList<Order> pickup = model.getPickupList();
        if (index < 0 || index >= pickup.size())
        {
            return;
        }

        Order target = pickup.get(index);
        handlePickUpOrder(target.getID(), target.getName());
    }


    /**
     * Checks the order ID and customer name before allowing pickup.
     *
     * @param id
     *            the order ID
     * @param name
     *            the customer's name
     */
    public void handlePickUpOrder(int id, String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            customerWindow.showMessage(
                "Please enter the name associated with your order.");
            return;
        }

        try
        {
            // The model compares names against lowercase input.
            boolean pickedUp = model.pickUpOrder(
                id, name.trim().toLowerCase());

            if (!pickedUp)
            {
                customerWindow.showMessage(
                    "No order was found with that ID and name.");
                return;
            }

            refreshAll();

            customerWindow.showMessage(
                "Your order has been picked up. Enjoy!");
        }
        catch (IllegalArgumentException e)
        {
            customerWindow.showMessage(
                "No order was found with that ID and name.");
        }
    }


    /**
     * Refreshes both windows using plain strings/primitives derived from the model.
     */
    public void refreshAll()
    {
        DoublyLinkedList<Order> pickup = model.getPickupList();
        String[] pickupLabels = new String[pickup.size()];
        String[] baristaPickupDetails = new String[pickup.size()];

        for (int i = 0; i < pickup.size(); i++)
        {
            Order o = pickup.get(i);
            pickupLabels[i] = o.toString();
            baristaPickupDetails[i] = describeOrder(o);
        }

        customerWindow.refreshPickup(pickupLabels);

        LinkedQueue<Order> q = model.getOrderQueue();
        baristaWindow.refreshQueue(q.size());

        Order head = q.peek();
        if (head != null && "PREPARING".equals(head.getStatus()))
        {
            baristaWindow.refreshCurrentOrder(describeOrder(head));
        }
        else
        {
            baristaWindow.refreshCurrentOrder("No order in progress");
        }

        baristaWindow.refreshPickup(baristaPickupDetails);
    }


    /**
     * Formats an order's summary, status, and item names into multi-line text.
     *
     * @param order
     *            the order to describe
     * @return the formatted description
     */
    private String describeOrder(Order order)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(order).append("\nStatus: ").append(order.getStatus());
        for (Item item : order.getItems())
        {
            sb.append("\n- ").append(item.getName());
        }
        return sb.toString();
    }


    /**
     * Creates the menu for JavaHouse.
     *
     * @return an array containing the menu items
     */
    private Item[] buildMenu()
    {
        return new Item[] {
            new Item("Latte", "Coffee", 4.50, 20),
            new Item("Cappuccino", "Coffee", 4.25, 20),
            new Item("Americano", "Coffee", 3.50, 20),
            new Item("Iced Coffee", "Coffee", 3.75, 20),
            new Item("Hot Chocolate", "Other", 3.25, 15),
            new Item("Chai Latte", "Tea", 4.00, 15)
        };
    }


    /**
     * Starts the JavaHouse application.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new JavaHouse());
    }
}