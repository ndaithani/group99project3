package group99project3;

import javax.swing.*;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author lquezadr
 * @version Nov 6, 2026
 */
public class CustomerWindow
{
    // ~ Fields ................................................................
    Order currentOrder;
    JButton[] menuDisplay;
    JTextArea cartDisplay;
    JList<Order> pickupDisplay;
    JLabel totalLabel;
    JButton addButton;
    JButton submitButton;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new CustomerWindow object.
     * @param menu
     * @param orderQ
     * @param pickup
     */
    public CustomerWindow(Item[] menu, LinkedQueue<Order> orderQ, DoublyLinkedList<Order> pickup)
    {
        //
    }


    private void initComponents()
    {
        //
    }
    
    private void addItemToOrder(Item item)
    {
        //
    }


    private void removeItemFromOrder()
    {
        //
    }


    private void submitOrder()
    {
        //
    }


    private void pickUpOrder()
    {
        //
    }

    private void refreshCart()
    {
        //
    }


    public void refreshPickup()
    {
        //
    }

}
