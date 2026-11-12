package application;
// -------------------------------------------------------------------------
/**
 *  Manages the data of the JavaHouse App
 * 
 *  @author Naomika Aithani
 *  @version Nov 6, 2026
 */
public class JavaHouseModel
{
    //~ Fields ................................................................
    private Item[] menu;
    private LinkedQueue<Order> queue;
    private DoublyLinkedList<Order> pickup;
    private int lastID = 0;

    //~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Creates a new JavaHouseModel object.
     * @param menu -- app menu array
     */
    JavaHouseModel(Item[] menu) {
        this.menu = menu;
        this.queue = new LinkedQueue<Order>();
        this.pickup = new DoublyLinkedList<Order>();
    }

    // ----------------------------------------------------------
    /**
     * Returns the menu array.
     * @return -- menu
     */
    //~Public  Methods ........................................................
    public Item[] getMenu() {
        return menu;
    }

    // ----------------------------------------------------------
    /**
     * Returns the order queue.
     * @return -- queue
     */
    public LinkedQueue<Order> getOrderQueue() {
        return queue;
    }

    // ----------------------------------------------------------
    /**
     * Returns the pickup list.
     * @return -- pickup
     */
    public DoublyLinkedList<Order> getPickupList() {
        return pickup;
    }

    // ----------------------------------------------------------
    /**
     * Adds a new order to the queue.
     * @param name -- name of customer
     * @param items -- item array for order
     */
    public void submitOrder(String name, Item[] items) {
        if (name == null || items == null) {
            throw new IllegalArgumentException("Please complete your order!");
        }
        for (int i = 0; i < items.length; i++) {
            if (!items[i].inStock()) {
                throw new IllegalStateException("Item is out of stock!");
            }
        }
        Order newOrder = new Order(name, lastID, items);
        queue.enqueue(newOrder);
        lastID++;
    }
    
    // ----------------------------------------------------------
    /**
     * Sets the status of the Order at the head of the queue to “complete”,
        dequeues it, and adds it to the pick-up list.
     */
    public void completeOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException();
        }
        queue.getHead().setStatus("COMPLETE");
        pickup.add(queue.dequeue());
    }
    
    // ----------------------------------------------------------
    /**
     * Sets the head of the queue's status to "preparing"
     */
    public void nextOrder() {
        if (queue.isEmpty()) {
            throw new IllegalStateException();
        }
        queue.getHead().setStatus("PREPARING");
    }
    
    // ----------------------------------------------------------
    /**
     * Searches for an order matching inputs and removes it if it exists
     * @param id
     * @param name
     * @return -- true if both inputs are correct, false if name is wrong,
     *      and IllegalArgumentException if ID doesn't exist.
     */
    public boolean pickUpOrder(int id, String name) {
        Order o = pickup.withID(id);
        if (o != null) {
            if (o.getName().equals(name.trim().toLowerCase())) {
                pickup.remove(o);
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Order does not exist!");
    }
}
