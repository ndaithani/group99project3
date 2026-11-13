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
    public void setUp() {
        menu = new Item[10];
        menu.push(new Item("Hot Coffee", "Hot Drink", 4.00, 100));
        menu.push(new Item("Hot Latte", "Hot Drink", 6.00, 100));
        menu.push(new Item("Hot Chocolate", "Hot Drink", 3.00, 100));
        menu.push(new Item("Iced Coffee", "Cold Drink", 4.00, 100));
        menu.push(new Item("Iced Latte", "Cold Drink", 6.00, 100));
        menu.push(new Item("Blended Coffee", "Cold Drink", 5.50, 100));
        menu.push(new Item("Croissant", "Snack", 5.00, 75));
        menu.push(new Item("Chocolate Croissant", "Snack", 5.00, 50));
        menu.push(new Item("Donut", "Snack", 5.00, 75));
        menu.push(new Item("Donut Hole (12)", "Snack", 7.00, 40));
        model = new JavaHouseModel(menu);
    }
    
    public void testGetMenu() {
        assertEquals(menu, model.getMenu());
        menu.push(new Item("Item", "Hot Drink", 10.00, 10));
        assertNotEqual(menu, model.getMenu());
    }
    
    public void testGetOrderQueue() {
        model.submitOrder("Naomika", items);
    }
    
    
}
