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

        customerWindow = new CustomerWindow(
            model.getMenu(),
            model.getOrderQueue(),
            model.getPickupList());

        baristaWindow = new BaristaWindow(
            model.getOrderQueue(),
            model.getPickupList());

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

        customerWindow.addItemToOrder(selectedItem);
    }


    /**
     * Submits the customer's order after validating the name and items.
     *
     * @param name
     *            the customer's name
     * @param items
     *            the items in the customer's cart
     */
    public void handleSubmitOrder(String name, ArrayList<Item> items)
    {
        // Check that a customer name was entered.
        if (name == null || name.trim().isEmpty())
        {
            customerWindow.showMessage(
                "Please enter your name.");
            return;
        }

        // Check that the cart contains at least one item.
        if (items == null || items.isEmpty())
        {
            customerWindow.showMessage(
                "Please add at least one item to your order.");
            return;
        }

        Item[] menu = model.getMenu();

        // Make sure every item belongs to the menu and is in stock.
        for (Item item : items)
        {
            boolean onMenu = false;

            for (Item menuItem : menu)
            {
                if (item == menuItem)
                {
                    onMenu = true;
                    break;
                }
            }

            if (!onMenu)
            {
                customerWindow.showMessage(
                    item.getName() + " is not on the menu.");
                return;
            }

            if (!item.inStock())
            {
                customerWindow.showMessage(
                    item.getName() + " is out of stock.");
                return;
            }
        }

        // Convert the cart into the array expected by the model.
        Item[] submittedItems = items.toArray(new Item[0]);

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
     * Refreshes both windows using the latest model information.
     */
    public void refreshAll()
    {
        customerWindow.refreshPickup();

        baristaWindow.refreshQueue();
        baristaWindow.refreshCurrentOrder();
        baristaWindow.refreshPickup();
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
