package application;
// -------------------------------------------------------------------------
/**
 * Tests methods of the DoublyLinkedList class
 * 
 *  @author naomi
 *  @version Nov 13, 2026
 */
public class DoublyLinkedListTest extends student.TestCase
{
    private DoublyLinkedList<String> list;
    public void setUp() {
        list = new DoublyLinkedList<String>();
    }
    
    // ----------------------------------------------------------
    /**
     * Tests isEmpty()
     */
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("Item");
        assertFalse(list.isEmpty());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests size()
     */
    public void testSize() {
        assertEquals(0, list.size());
        list.add("Item");
        assertEquals(1, list.size());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests add(T element)
     */
    public void testAdd() {
        assertEquals(0, list.size());
        
        Exception e = null;
        
        try {
            list.add(null);
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        list.add("Item");
        assertEquals(1, list.size());
        
        list.add("Item");
        assertEquals(2, list.size());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests get(int index)
     */
    public void testGet() {
        list.add("Item 1");
        list.add("Item 2");
        
        Exception e = null;
        
        try {
            list.get(-1);
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        e = null;
        
        try {
            list.get(2);
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        assertEquals("Item 1", list.get(0));
        assertEquals("Item 2", list.get(1));
    }
    
    // ----------------------------------------------------------
    /**
     * Tests remove(T element).
     */
    public void testRemove() {
        assertFalse(list.remove(null));
        assertFalse(list.remove("Item 1"));
        list.add("Item 1");
        list.add("Item 2");
        assertTrue(list.remove("Item 2"));
        assertTrue(list.remove("Item 1"));
    }
    
    // ----------------------------------------------------------
    /**
     * Tests toString().
     */
    public void testToString() {
        list.add("Item 1");
        list.add("Item 2");
        assertEquals("[Item 1, Item 2]", list.toString());
    }
}
