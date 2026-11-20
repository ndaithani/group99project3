package application;

import java.util.ArrayList;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the LinkedQueue class' methods
 * 
 *  @author Naomika
 *  @version Nov 12, 2026
 */
public class OrderTest extends TestCase
{
    private Order order; 
    private ArrayList<Item> items;
    
    public void setUp() {
        items = new ArrayList<Item>();
        items.add(new Item("Iced Latte", "Cold Drink", 6.00, 100));
        items.add(new Item("Croissant", "Snack", 5.00, 75));
        order = new Order("Naomika", items);
    }
    
    // ----------------------------------------------------------
    /**
     * tests getName().
     */
    public void testGetName() {
        assertEquals("Naomika", order.getName());
    }
    
    // ----------------------------------------------------------
    /**
     * tests getID().
     */
    public void testGetID() {
        assertEquals(1, order.getID());
    }
    
    // ----------------------------------------------------------
    /**
     * tests getItems().
     */
    public void testGetItems() {
        assertEquals(items, order.getItems());
    }
    
    // ----------------------------------------------------------
    /**
     * tests getItemsArray().
     */
    public void testGetItemsArray() {
        Item[] itemsArray = new Item[2];
        itemsArray[0] = new Item("Iced Latte", "Cold Drink", 6.00, 100);
        itemsArray[1] = new Item("Croissant", "Snack", 5.00, 75);
        assertEquals(itemsArray.length, order.getItemsArray().length);
    }
    
    // ----------------------------------------------------------
    /**
     * tests getStatus().
     */
    public void testGetStatus() {
        assertEquals("NOT READY", order.getStatus());
    }
    
    // ----------------------------------------------------------
    /**
     * tests setStatus().
     */
    public void testSetStatus() {
        order.setStatus("PREPARING");
        assertEquals("PREPARING", order.getStatus());
        order.setStatus("COMPLETE");
        assertEquals("COMPLETE", order.getStatus());
        
        Exception e = null;
        try {
            order.setStatus(null);
        } catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        e = null;
        try {
            order.setStatus("NOT COMPLETE");
        } catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
    }
    
    // ----------------------------------------------------------
    /**
     * tests toString().
     */
    public void testToString() {
        assertEquals(order.toString(), "Order #6 - Naomika [NOT READY]");
    }    
}
