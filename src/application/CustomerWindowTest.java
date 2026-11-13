package application;

import student.TestCase;
import java.util.ArrayList;


// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author lquezadr
 * @version Nov 12, 2026
 */
public class CustomerWindowTest
    extends TestCase
{
    // ~ Fields ................................................................

    private Item latte;
    private Item muffin;
    private DoublyLinkedList<Order> pickup;
    private CustomerWindow window;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    public void setUp()
    {
        latte = new Item("Latte", "Drink", 4.50, 10);
        muffin = new Item("Muffin", "Pastry", 3.25, 10);
        pickup = new DoublyLinkedList<>();
        window = new CustomerWindow(
            new Item[] { latte, muffin },
            new LinkedQueue<Order>(),
            pickup);
    }


    // ----------------------------------------------------------
    /**
     * Tests that adding an item fills the cart and updates the total.
     */
    public void testAddItemToOrder()
    {
        window.addItemToOrder(latte);
        window.addItemToOrder(muffin);
        assertEquals(2, window.cartModel.size());
        assertEquals("Total: $7.75", window.totalLabel.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that removing a selected item empties the cart.
     */
    public void testRemoveItemFromOrder()
    {
        window.addItemToOrder(latte);
        window.cartDisplay.setSelectedIndex(0);
        window.removeItemFromOrder();
        assertEquals(0, window.cartModel.size());
        assertTrue(window.currentOrder.getItems().isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Tests that removing with nothing selected leaves the cart unchanged.
     */
    public void testRemoveWithNoSelection()
    {
        window.addItemToOrder(latte);
        window.removeItemFromOrder();
        assertEquals(1, window.cartModel.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests that clearing the order resets the cart, name, and total.
     */
    public void testClearOrder()
    {
        window.addItemToOrder(latte);
        window.nameField.setText("Ann");
        window.clearOrder();
        assertEquals(0, window.cartModel.size());
        assertEquals("", window.nameField.getText());
        assertEquals("Total: $0.00", window.totalLabel.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that refreshPickup copies the pickup list into the display.
     */
    public void testRefreshPickup()
    {
        pickup.add(new Order("Ann", new ArrayList<Item>()));
        window.refreshPickup();
        assertEquals(1, window.pickupModel.size());
    }
}
