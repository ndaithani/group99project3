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
public class BaristaWindow
{
    // ~ Fields ................................................................
    JTextArea currentOrderDisplay;
    JButton completeOrderButton;
    JButton nextOrderButton;
    JList<Order> pickupDisplay;
    JTextArea selectedOrderDisplay;
    JButton removeOrderButton;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new BaristaWindow object.
     * @param orderQ
     * @param pickup
     */
    public BaristaWindow(LinkedQueue<Order> orderQ, DoublyLinkedList<Order> pickup)
    {
        //
    }


    private void initComponents()
    {
        //
    }

    private void nextOrder()
    {
        //
    }


    private void completeOrder()
    {
        //
    }

    public void refreshQueue()
    {
        //
    }


    private void refreshCurrentOrder()
    {
        //
    }


    public void refreshPickup()
    {
        //
    }

}
