package application;

import javax.swing.*;
import java.awt.*;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author lquezadr
 * @version Nov 6, 2026
 */
public class BaristaWindow
    extends JFrame
{
    // ~ Fields ................................................................
    private final LinkedQueue<Order> orderQ;
    private final DoublyLinkedList<Order> pickup;
    private JavaHouse controller;

    private final DefaultListModel<Order> pickupModel =
        new DefaultListModel<>();

    private JTextArea currentOrderDisplay;
    private JTextArea selectedOrderDisplay;
    private JList<Order> pickupDisplay;
    private JButton nextOrderButton;
    private JButton completeOrderButton;
    private JButton removeOrderButton;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new BaristaWindow object.
     * 
     * @param orderQ
     * @param pickup
     */
    public BaristaWindow(
        LinkedQueue<Order> orderQ,
        DoublyLinkedList<Order> pickup)
    {
        super("JavaHouse - Barista");
        this.orderQ = orderQ;
        this.pickup = pickup;

        initComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }


    // ----------------------------------------------------------
    /**
     * Sets the JavaHouse that receives this window's button input.
     *
     * @param controller
     *            the JavaHouse controller
     */
    public void setController(JavaHouse controller)
    {
        this.controller = controller;
    }


    // ----------------------------------------------------------
    /**
     * Builds the order displays, the pickup list, and the buttons.
     */
    private void initComponents()
    {
        setLayout(new BorderLayout());

        nextOrderButton = new JButton("Next Order (0)");
        completeOrderButton = new JButton("Complete Order");
        removeOrderButton = new JButton("Remove Picked-Up Order");
        nextOrderButton.addActionListener(e -> nextOrder());
        completeOrderButton.addActionListener(e -> completeOrder());
        removeOrderButton.addActionListener(e -> removeOrder());

        JPanel top = new JPanel();
        top.add(nextOrderButton);
        top.add(completeOrderButton);
        add(top, BorderLayout.NORTH);

        currentOrderDisplay = new JTextArea(5, 25);
        currentOrderDisplay.setEditable(false);
        selectedOrderDisplay = new JTextArea(5, 25);
        selectedOrderDisplay.setEditable(false);
        pickupDisplay = new JList<>(pickupModel);
        pickupDisplay.addListSelectionListener(e -> showSelectedOrder());

        JScrollPane currentPane = new JScrollPane(currentOrderDisplay);
        currentPane
            .setBorder(BorderFactory.createTitledBorder("Current Order"));
        JScrollPane pickupPane = new JScrollPane(pickupDisplay);
        pickupPane
            .setBorder(BorderFactory.createTitledBorder("Ready for Pickup"));
        JScrollPane selectedPane = new JScrollPane(selectedOrderDisplay);
        selectedPane
            .setBorder(BorderFactory.createTitledBorder("Selected Order"));

        JPanel center = new JPanel(new GridLayout(3, 1));
        center.add(currentPane);
        center.add(pickupPane);
        center.add(selectedPane);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(removeOrderButton);
        add(bottom, BorderLayout.SOUTH);
    }


    // ----------------------------------------------------------
    /**
     * Asks JavaHouse to start the next order in the queue.
     */
    void nextOrder()
    {
        controller.handleNextOrder();
    }


    // ----------------------------------------------------------
    /**
     * Asks JavaHouse to complete the order being prepared.
     */
    void completeOrder()
    {
        controller.handleCompleteOrder();
    }


    // ----------------------------------------------------------
    /**
     * Asks JavaHouse to remove the selected pickup order, if one is selected.
     */
    void removeOrder()
    {
        Order selected = pickupDisplay.getSelectedValue();
        if (selected != null)
        {
            controller.handlePickUpOrder(selected.getID(), selected.getName());
        }
    }


    // ----------------------------------------------------------
    /**
     * Updates the next-order button with the queue size and enables it only
     * when orders are waiting.
     */
    public void refreshQueue()
    {
        int count = orderQ.size();
        nextOrderButton.setText("Next Order (" + count + ")");
        nextOrderButton.setEnabled(count > 0);
    }


    // ----------------------------------------------------------
    /**
     * Shows the head of the queue if it is being prepared, otherwise a no-order
     * message.
     */
    void refreshCurrentOrder()
    {
        Order head = orderQ.peek();
        if (head != null && Order.PREPARING.equals(head.getStatus()))
        {
            currentOrderDisplay.setText(describe(head));
        }
        else
        {
            currentOrderDisplay.setText("No order in progress");
        }
    }


    // ----------------------------------------------------------
    /**
     * Redraws the pickup list from the shared pickup list.
     */
    public void refreshPickup()
    {
        pickupModel.clear();
        for (Order order : pickup)
        {
            pickupModel.addElement(order);
        }
        showSelectedOrder();
    }


    // ----------------------------------------------------------
    /**
     * Shows the details of the selected pickup order, or clears the display.
     */
    void showSelectedOrder()
    {
        Order selected = pickupDisplay.getSelectedValue();
        selectedOrderDisplay
            .setText(selected == null ? "" : describe(selected));
    }


    // ----------------------------------------------------------
    /**
     * Returns an order's id, name, status, and items as multi-line text.
     *
     * @param order
     *            the order to describe
     * @return the description
     */
    private String describe(Order order)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(order).append("\nStatus: ").append(order.getStatus());
        for (Item item : order.getItems())
        {
            sb.append("\n- ").append(item.getName());
        }
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * Shows the barista a message in a pop-up dialog.
     *
     * @param message
     *            the text to show
     */
    void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }
}
