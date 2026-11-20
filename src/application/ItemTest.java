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
public class ItemTest extends TestCase
{
    private Item item;
    
    public void setUp() {
        item = new Item("Iced Latte", "Cold Drink", 6.00, 100);
    }
    
    // ----------------------------------------------------------
    /**
     * tests getName()
     */
    public void testGetName() {
        assertEquals("Iced Latte", item.getName());
    }
    
    // ----------------------------------------------------------
    /**
     * tests getCategory()
     */
    public void testGetCategory() {
        assertEquals("Cold Drink", item.getCategory());
    }
    
    // ----------------------------------------------------------
    /**
     * tests getPrice()
     */
    public void testGetPrice() {
        assertEquals(String.valueOf(6.00), String.valueOf(item.getPrice()));
    }
    
    // ----------------------------------------------------------
    /**
     * tests getStock()
     */
    public void testGetStock() {
        assertEquals(100, item.getStock());
    }
    
    // ----------------------------------------------------------
    /**
     * tests decrementStock()
     */
    public void testDecrementStock() {
        item.decrementStock();
        assertEquals(99, item.getStock());
    }
    
    // ----------------------------------------------------------
    /**
     * tests inStock()
     */
    public void testInStock() {
        assertTrue(item.inStock());
        for (int i = 0; i < 100; i++) {
            item.decrementStock();
        }
        assertFalse(item.inStock());
    }
    
    // ----------------------------------------------------------
    /**
     * tests toString()
     */
    public void testToString() {
        assertEquals("Iced Latte ($6.00)", item.toString());
    }
}