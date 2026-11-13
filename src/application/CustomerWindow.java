package application;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/** 
 * @author lquezadr
 * @version Nov 6, 2026
 */
public class CustomerWindow extends JFrame
{
    JavaHouse controller;

    final ArrayList<Integer> cartIndices = new ArrayList<>();
    final DefaultListModel<String> menuModel = new DefaultListModel<>();
    final DefaultListModel<String> cartModel = new DefaultListModel<>();
    final DefaultListModel<String> pickupModel = new DefaultListModel<>();

    JList<String> menuDisplay;
    JList<String> cartDisplay;
    JList<String> pickupDisplay;
    JLabel totalLabel;
    JTextField nameField;
    JButton addButton;
    JButton removeButton;
    JButton submitButton;
    JButton pickupButton;

    public CustomerWindow(String[] menuLabels)
    {
        super("JavaHouse(Customer)");
        for (String label : menuLabels)
        {
            menuModel.addElement(label);
        }
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setController(JavaHouse controller)
    {
        this.controller = controller;
    }

    private void initComponents()
    {
        setLayout(new BorderLayout());

        menuDisplay = new JList<>(menuModel);
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
        pickupPane.setBorder(BorderFactory.createTitledBorder("Ready for Pickup"));
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

    void addItemToOrder(int menuIndex, String itemLabel, double newTotal)
    {
        cartIndices.add(menuIndex);
        cartModel.addElement(itemLabel);
        totalLabel.setText(String.format("Total: $%.2f", newTotal));
    }

    void removeItemFromOrder()
    {
        int index = cartDisplay.getSelectedIndex();
        if (index >= 0)
        {
            cartIndices.remove(index);
            cartModel.remove(index);
            totalLabel.setText(
                String.format("Total: $%.2f", controller.calculateCartTotal(cartIndices)));
        }
    }

    void submitOrder()
    {
        if (cartIndices.isEmpty())
        {
            return;
        }
        String name = nameField.getText().trim();
        controller.handleSubmitOrder(name.isEmpty() ? null : name, cartIndices);
    }

    void pickUpOrder()
    {
        int index = pickupDisplay.getSelectedIndex();
        if (index >= 0)
        {
            controller.handlePickUpOrderAtIndex(index);
        }
    }

    void clearOrder()
    {
        cartIndices.clear();
        cartModel.clear();
        nameField.setText("");
        totalLabel.setText("Total: $0.00");
    }

    public void refreshPickup(String[] pickupLabels)
    {
        pickupModel.clear();
        for (String label : pickupLabels)
        {
            pickupModel.addElement(label);
        }
    }

    void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }
}