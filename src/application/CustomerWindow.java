package group99project3;

import java.awt.BorderLayout;
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
    extends JFrame
{
    // ~ Fields ................................................................
    private final Item[] menu;
    private final LinkedQueue<Order> orderQ;
    private final DoublyLinkedList<Order> pickup;
    
    private Order currentOrder;
    private JButton[] menuDisplay;
    private JList<Item> cartDisplay;
    private JList<Order> pickupDisplay;
    private JLabel totalLabel;
    private JButton addButton;
    private JButton submitButton;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new CustomerWindow object.
     * 
     * @param menu
     * @param orderQ
     * @param pickup
     */
    public CustomerWindow(
        Item[] menu,
        LinkedQueue<Order> orderQ,
        DoublyLinkedList<Order> pickup)
    {
        super("JavaHouse(Customer)");
        
        this.menu = menu;
        this.orderQ = orderQ;
        this.pickup = pickup;
        this.currentOrder = currentOrder;
        
        initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }


    private void initComponents()
    {
        setLayout(new BorderLayout());
        
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
