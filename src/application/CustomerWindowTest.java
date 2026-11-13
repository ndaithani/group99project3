package application;

import student.TestCase;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Tests the decoupled CustomerWindow view methods.
 * 
 * @author lquezadr
 * @version Nov 12, 2026
 */
public class CustomerWindowTest
    extends TestCase
{
    // ~ Fields ................................................................

    private CustomerWindow window;

    // ~ Public Methods ........................................................

    /**
     * Creates a CustomerWindow and controller stub before each test.
     */
    public void setUp()
    {
        window = new CustomerWindow(
            new String[] { "Latte ($4.50)", "Muffin ($3.25)" });
        window.setController(new JavaHouse() {
            @Override
            public double calculateCartTotal(ArrayList<Integer> indices)
            {
                double total = 0;
                for (int idx : indices)
                {
                    total += (idx == 0) ? 4.50 : 3.25;
                }
                return total;
            }
        });
    }


    // ----------------------------------------------------------
    /**
     * Tests that adding an item fills the cart and updates the total.
     */
    public void testAddItemToOrder()
    {
        window.addItemToOrder(0, "Latte ($4.50)", 4.50);
        window.addItemToOrder(1, "Muffin ($3.25)", 7.75);
        assertEquals(2, window.cartModel.size());
        assertEquals("Total: $7.75", window.totalLabel.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that removing a selected item empties the cart.
     */
    public void testRemoveItemFromOrder()
    {
        window.addItemToOrder(0, "Latte ($4.50)", 4.50);
        window.cartDisplay.setSelectedIndex(0);
        window.removeItemFromOrder();
        assertEquals(0, window.cartModel.size());
        assertTrue(window.cartIndices.isEmpty());
        assertEquals("Total: $0.00", window.totalLabel.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that removing with nothing selected leaves the cart unchanged.
     */
    public void testRemoveWithNoSelection()
    {
        window.addItemToOrder(0, "Latte ($4.50)", 4.50);
        window.removeItemFromOrder();
        assertEquals(1, window.cartModel.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests that clearing the order resets the cart, name, and total.
     */
    public void testClearOrder()
    {
        window.addItemToOrder(0, "Latte ($4.50)", 4.50);
        window.nameField.setText("Ann");
        window.clearOrder();
        assertEquals(0, window.cartModel.size());
        assertEquals("", window.nameField.getText());
        assertEquals("Total: $0.00", window.totalLabel.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that refreshPickup copies the pickup array into the display.
     */
    public void testRefreshPickup()
    {
        window.refreshPickup(new String[] { "Order #0 - Ann [COMPLETE]" });
        assertEquals(1, window.pickupModel.size());
    }
}