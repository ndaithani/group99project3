package application;

import javax.swing.*;
import java.awt.*;
// -------------------------------------------------------------------------
/** 
 * @author lquezadr
 * @version Nov 6, 2026
 */

public class BaristaWindow extends JFrame
{
    JavaHouse controller;

    final DefaultListModel<String> pickupModel = new DefaultListModel<>();

    JTextArea currentOrderDisplay;
    JTextArea selectedOrderDisplay;
    JList<String> pickupDisplay;
    JButton nextOrderButton;
    JButton completeOrderButton;
    JButton removeOrderButton;

    public BaristaWindow()
    {
        super("JavaHouse (Barista)");
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
        currentPane.setBorder(BorderFactory.createTitledBorder("Current Order"));
        JScrollPane pickupPane = new JScrollPane(pickupDisplay);
        pickupPane.setBorder(BorderFactory.createTitledBorder("Ready for Pickup"));
        JScrollPane selectedPane = new JScrollPane(selectedOrderDisplay);
        selectedPane.setBorder(BorderFactory.createTitledBorder("Selected Order"));

        JPanel center = new JPanel(new GridLayout(3, 1));
        center.add(currentPane);
        center.add(pickupPane);
        center.add(selectedPane);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(removeOrderButton);
        add(bottom, BorderLayout.SOUTH);
    }

    void nextOrder() { controller.handleNextOrder(); }

    void completeOrder() { controller.handleCompleteOrder(); }

    void removeOrder()
    {
        int index = pickupDisplay.getSelectedIndex();
        if (index >= 0)
        {
            controller.handlePickUpOrderAtIndex(index);
        }
    }

    public void refreshQueue(int count)
    {
        nextOrderButton.setText("Next Order (" + count + ")");
        nextOrderButton.setEnabled(count > 0);
    }

    public void refreshCurrentOrder(String currentText)
    {
        currentOrderDisplay.setText(currentText);
    }

    public void refreshPickup(String[] pickupSummaries)
    {
        pickupModel.clear();
        for (String s : pickupSummaries)
        {
            pickupModel.addElement(s);
        }
        showSelectedOrder();
    }

    void showSelectedOrder()
    {
        String selected = pickupDisplay.getSelectedValue();
        selectedOrderDisplay.setText(selected == null ? "" : selected);
    }

    void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }
}