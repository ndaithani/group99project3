package application;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author lquezadr
 * @version Nov 12, 2026
 */
public class BaristaWindowTest
    extends TestCase
{
    // ~ Fields ................................................................

    private LinkedQueue<Order> orderQ;
    private DoublyLinkedList<Order> pickup;
    private BaristaWindow window;
    private Order order;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    
    /**
     * Creates a window and one sample order before each test.
     */
    public void setUp()
    {
        orderQ = new LinkedQueue<>();
        pickup = new DoublyLinkedList<>();
        window = new BaristaWindow(orderQ, pickup);
        order = new Order("Ann", new ArrayList<Item>());
    }


    // ----------------------------------------------------------
    /**
     * Tests that an empty queue disables the next-order button.
     */
    public void testRefreshQueueEmpty()
    {
        window.refreshQueue();
        assertEquals("Next Order (0)", window.nextOrderButton.getText());
        assertFalse(window.nextOrderButton.isEnabled());
    }


    // ----------------------------------------------------------
    /**
     * Tests that waiting orders show in the button and enable it.
     */
    public void testRefreshQueue()
    {
        orderQ.enqueue(order);
        window.refreshQueue();
        assertEquals("Next Order (1)", window.nextOrderButton.getText());
        assertTrue(window.nextOrderButton.isEnabled());
    }


    // ----------------------------------------------------------
    /**
     * Tests that a pending head order is not shown as the current order.
     */
    public void testRefreshCurrentOrderPending()
    {
        orderQ.enqueue(order);
        window.refreshCurrentOrder();
        assertEquals(
            "No order in progress",
            window.currentOrderDisplay.getText());
    }


    // ----------------------------------------------------------
    /**
     * Tests that a preparing head order is shown as the current order.
     */
    public void testRefreshCurrentOrderPreparing()
    {
        order.setStatus(Order.PREPARING);
        orderQ.enqueue(order);
        window.refreshCurrentOrder();
        assertTrue(window.currentOrderDisplay.getText().contains("Ann"));
    }


    // ----------------------------------------------------------
    /**
     * Tests that refreshPickup fills the list and selecting shows details.
     */
    public void testRefreshPickup()
    {
        pickup.add(order);
        window.refreshPickup();
        assertEquals(1, window.pickupModel.size());

        window.pickupDisplay.setSelectedIndex(0);
        assertTrue(window.selectedOrderDisplay.getText().contains("Ann"));
    }
}
