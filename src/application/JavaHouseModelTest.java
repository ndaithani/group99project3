package application;

// -------------------------------------------------------------------------
/**
 *  Tests the methods within JavaHouseModel
 * 
 *  @author Naomika Aithani
 *  @version Nov 12, 2026
 */
public class JavaHouseModelTest extends student.TestCase
{
    private Item[] menu;
    private JavaHouseModel model;
    
    public void setUp() {
        menu = new Item[10];
        menu[0] = new Item("Hot Coffee", "Hot Drink", 4.00, 100);
        menu[1] = new Item("Hot Latte", "Hot Drink", 6.00, 100);
        menu[2] = new Item("Hot Chocolate", "Hot Drink", 3.00, 100);
        menu[3] = new Item("Iced Coffee", "Cold Drink", 4.00, 100);
        menu[4] = new Item("Iced Latte", "Cold Drink", 6.00, 100);
        menu[5] = new Item("Blended Coffee", "Cold Drink", 5.50, 100);
        menu[6] = new Item("Croissant", "Snack", 5.00, 75);
        menu[7] = new Item("Chocolate Croissant", "Snack", 5.00, 50);
        menu[8] = new Item("Donut", "Snack", 5.00, 75);
        menu[9] = new Item("Donut Hole (12)", "Snack", 7.00, 40);
        model = new JavaHouseModel(menu);
    }
    
    // ----------------------------------------------------------
    /**
     * tests getMenu().
     */
    public void testGetMenu() {
        for (int i = 0; i < 10; i++) {
            assertEquals(menu[i], model.getMenu()[i]);
        }
    }
    
    // ----------------------------------------------------------
    /**
     * tests getOrderQueue().
     */
    public void testGetOrderQueue() {
        assertEquals(model.getOrderQueue().size(), 0);
        Item[] items = { menu[0] };
        model.submitOrder("Naomika", items);
        assertEquals(model.getOrderQueue().size(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * tests getPickupList().
     */
    public void testGetPickupList() {
        assertEquals(model.getPickupList().size(), 0);
        Item[] items = { menu[0] };
        model.submitOrder("Naomika", items);
        model.completeOrder();
        assertEquals(model.getPickupList().size(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * tests submitOrder().
     */
    public void testSubmitOrder() {
        Exception e = null;
        try {
            model.submitOrder(null, null);
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);

        e = null;
        try {
            model.submitOrder("Naomika", null);
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);

        for (int i = 0; i < 50; i++) {
            menu[0].decrementStock();
            menu[0].decrementStock();
        }
        
        e = null;
        try {
            Item[] items = new Item[0];
            model.submitOrder("Naomika", items);
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        e = null;
        try {
            Item[] items = { menu[0] };
            model.submitOrder("Naomika", items);
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);

        for (int i = 0; i < 99; i++) {
            menu[2].decrementStock();
        }
        e = null;
        try {
            Item[] overStockItems = { menu[2], menu[2] };
            model.submitOrder("Naomika", overStockItems);
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        assertEquals(1, menu[2].getStock());

        assertEquals(model.getOrderQueue().size(), 0);
        Item[] items = { menu[1], menu[3], menu[6] };
        model.submitOrder("Naomika", items);
        assertEquals(model.getOrderQueue().size(), 1);
        assertEquals(3, model.getOrderQueue().peek().getItems().size());
    }
    
    // ----------------------------------------------------------
    /**
     * tests completeOrder().
     */
    public void testCompleteOrder() {
        Exception e = null;
        try {
            model.completeOrder();
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);

        assertEquals(model.getOrderQueue().size(), 0);
        Item[] items = { menu[1] };
        model.submitOrder("Naomika", items);
        assertEquals(model.getOrderQueue().size(), 1);
        model.completeOrder();
        assertEquals(model.getOrderQueue().size(), 0);
    }
    
    // ----------------------------------------------------------
    /**
     * tests nextOrder().
     */
    public void testNextOrder() {
        Exception e = null;
        try {
            model.nextOrder();
        } 
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        assertEquals(model.getOrderQueue().size(), 0);
        Item[] items = { menu[1] };
        model.submitOrder("Naomika", items);
        model.nextOrder();
        assertEquals("PREPARING", model.getOrderQueue().peek().getStatus());
    }
    
    // ----------------------------------------------------------
    /**
     * tests pickUpOrder().
     */
    public void testPickUpOrder() {
        Item[] items = { menu[1] };
        model.submitOrder("Naomika", items);
        model.completeOrder();
        assertFalse(model.pickUpOrder(10, null));
        assertFalse(model.pickUpOrder(10, "Naomika"));
        assertTrue(model.pickUpOrder(0, "Naomika"));
    }
}