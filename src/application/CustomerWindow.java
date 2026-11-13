package application;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

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

    private JavaHouse controller;
    private Order currentOrder;

    private final DefaultListModel<Item> cartModel = new DefaultListModel<>();
    private final DefaultListModel<Order> pickupModel =
        new DefaultListModel<>();

    private JList<Item> menuDisplay;
    private JList<Item> cartDisplay;
    private JList<Order> pickupDisplay;
    private JLabel totalLabel;
    private JTextField nameField;
    private JButton addButton;
    private JButton removeButton;
    private JButton submitButton;
    private JButton pickupButton;

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
        this.currentOrder = new Order("", new ArrayList<>());

        initComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }


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
     * Builds the menu, cart, and pickup lists plus the bottom button bar.
     */
    private void initComponents()
    {
        setLayout(new BorderLayout());

        menuDisplay = new JList<>(menu);
        addButton = new JButton("Add to Order");
        addButton.addActionListener(
            e -> controller.handleAddItem(menuDisplay.getSelectedIndex()));
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        menuPanel.add(new JScrollPane(menuDisplay), BorderLayout.CENTER);
        menuPanel.add(addButton, BorderLayout.SOUTH);
        add(menuPanel, BorderLayout.WEST);

        cartDisplay = new JList<>(cartModel);
        pickupDisplay = new JList<>(pickupModel);
        JScrollPane cartPane = new JScrollPane(cartDisplay);
        cartPane.setBorder(BorderFactory.createTitledBorder("Your Order"));
        JScrollPane pickupPane = new JScrollPane(pickupDisplay);
        pickupPane
            .setBorder(BorderFactory.createTitledBorder("Ready for Pickup"));
        JPanel center = new JPanel(new GridLayout(2, 1));
        center.add(cartPane);
        center.add(pickupPane);
        add(center, BorderLayout.CENTER);

        nameField = new JTextField(10);
        totalLabel = new JLabel("Total: $0.00");
        removeButton = new JButton("Remove Item");
        submitButton = new JButton("Submit Order");
        pickupButton = new JButton("Pick Up");
        removeButton.addActionListener(e -> removeItemFromOrder());
        submitButton.addActionListener(e -> submitOrder());
        pickupButton.addActionListener(e -> pickUpOrder());

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Name:"));
        bottom.add(nameField);
        bottom.add(totalLabel);
        bottom.add(removeButton);
        bottom.add(submitButton);
        bottom.add(pickupButton);
        add(bottom, BorderLayout.SOUTH);
    }


    // ----------------------------------------------------------
    /**
     * Adds the given item to the current order and updates the cart.
     *
     * @param item
     *            the item to add
     */
    void addItemToOrder(Item item)
    {
        currentOrder.getItems().add(item);
        refreshCart();
    }


    // ----------------------------------------------------------
    /**
     * Removes the selected cart item from the current order, if one is
     * selected.
     */
    void removeItemFromOrder()
    {
        int index = cartDisplay.getSelectedIndex();
        if (index >= 0)
        {
            currentOrder.getItems().remove(index);
            refreshCart();
        }
    }


    // ----------------------------------------------------------
    /**
     * Sends the current order and typed name to JavaHouse, ignoring an empty
     * cart.
     */
    void submitOrder()
    {
        if (currentOrder.getItems().isEmpty())
        {
            return;
        }
        String name = nameField.getText().trim();
        controller.handleSubmitOrder(
            name.isEmpty() ? null : name,
            currentOrder.getItems());
    }


    // ----------------------------------------------------------
    /**
     * Sends the selected pickup order's id and name to JavaHouse, if one is
     * selected.
     */
    void pickUpOrder()
    {
        Order selected = pickupDisplay.getSelectedValue();
        if (selected != null)
        {
            controller.handlePickUpOrder(selected.getID(), selected.getName());
        }
    }


    // ----------------------------------------------------------
    /**
     * Empties the current order and the name field after a successful submit.
     */
    void clearOrder()
    {
        currentOrder.getItems().clear();
        nameField.setText("");
        refreshCart();
    }


    // ----------------------------------------------------------
    /**
     * Redraws the cart list and the total from the current order.
     */
    void refreshCart()
    {
        cartModel.clear();
        double total = 0;
        for (Item item : currentOrder.getItems())
        {
            cartModel.addElement(item);
            total += item.getPrice();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
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
    }


    // ----------------------------------------------------------
    /**
     * Shows the customer a message in a pop-up dialog.
     *
     * @param message
     *            the text to show
     */
    void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }
}
