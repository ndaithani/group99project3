package application;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the decoupled BaristaWindow view methods.
 * 
 * @author lquezadr
 * @version Nov 12, 2026
 */
public class BaristaWindowTest
    extends TestCase
{
    // ~ Fields ................................................................

    private BaristaWindow window;

    // ~ Public Methods ........................................................

    /**
     * Creates a BaristaWindow before each test.
     */
    public void setUp()
    {
        window = new BaristaWindow();
    }


    // ----------------------------------------------------------
    /**
     * Tests that an empty queue disables the next-order button.
     */
    public void testRefreshQueueEmpty()
    {
        window.refreshQueue(0);
        assertEquals("Next Order (0)", window.nextOrderButton.getText());
        assertFalse(window.nextOrderButton.isEnabled());
    }


    // ----------------------------------------------------------
    /**
     * Tests that waiting orders show in the button and enable it.
     */
    public void testRefreshQueue()
    {
        window.refreshQueue(1);
        assertEquals("Next Order (1)", window.nextOrderButton.getText());
        assertTrue(window.nextOrderButton.isEnabled());
    }


    // ----------------------------------------------------------
    /**
     * Tests that a no-order message displays when nothing is preparing.
     */
    public void testRefreshCurrentOrderPending()
    {
        window.refreshCurrentOrder("No order in progress");
        assertEquals(
            "No order in progress",
            window.currentOrderDisplay.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that a preparing order summary is shown as the current order.
     */
    public void testRefreshCurrentOrderPreparing()
    {
        window.refreshCurrentOrder(
            "Order #0 - Ann [PREPARING]\nStatus: PREPARING\n- Latte");
        assertTrue(window.currentOrderDisplay.getText().contains("Ann"));
    }


    // ----------------------------------------------------------
    /**
     * Tests that refreshPickup fills the list and selecting shows details.
     */
    public void testRefreshPickup()
    {
        window.refreshPickup(new String[] {
            "Order #0 - Ann [COMPLETE]\nStatus: COMPLETE\n- Latte"
        });
        assertEquals(1, window.pickupModel.size());

        window.pickupDisplay.setSelectedIndex(0);
        assertTrue(window.selectedOrderDisplay.getText().contains("Ann"));
    }
}