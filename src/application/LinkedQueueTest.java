package application;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the LinkedQueue class' methods
 * 
 *  @author Naomika
 *  @version Nov 12, 2026
 */
public class LinkedQueueTest extends TestCase
{
    private LinkedQueue<String> queue;
    
    public void setUp() {
        queue = new LinkedQueue<String>();
    }
    
    // ----------------------------------------------------------
    /**
     * tests isEmpty().
     */
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue("Item");
        assertFalse(queue.isEmpty());
    }
    
    // ----------------------------------------------------------
    /**
     * tests size().
     */
    public void testSize() {
        assertTrue(queue.isEmpty());
        queue.enqueue("Item");
        assertFalse(queue.isEmpty());
    }
    
    // ----------------------------------------------------------
    /**
     * tests enqueue().
     */
    public void testEnqueue() {
        assertTrue(queue.isEmpty());
        
        Exception e = null;
        try {
            queue.enqueue(null);
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        queue.enqueue("Item");
        queue.enqueue("Item");
        assertFalse(queue.isEmpty());
    }
    
    // ----------------------------------------------------------
    /**
     * tests dequeue().
     */
    public void testDequeue() {        
        Exception e = null;
        try {
            queue.dequeue();
        }
        catch (Exception err) {
            e = err;
        }
        assertNotNull(e);
        
        queue.enqueue("Item 1");
        queue.enqueue("Item 2");
        assertEquals("Item 1", queue.dequeue());
        assertEquals("Item 2", queue.dequeue());
    }
    
    // ----------------------------------------------------------
    /**
     * tests peek().
     */
    public void testPeek() {        
        assertNull(queue.peek());
        queue.enqueue("Item");
        assertEquals("Item", queue.peek());
    }
    
    // ----------------------------------------------------------
    /**
     * test toString().
     */
    public void testToString() {
        queue.enqueue("Item 1");
        queue.enqueue("Item 2"); 
        assertEquals("[Item 1, Item 2]", queue.toString());
    }
}
