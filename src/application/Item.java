package application;

// -------------------------------------------------------------------------
/**
 *  Item of JavaHouse
 * 
 *  @author Naomika
 *  @version Nov 13, 2026
 */
public class Item {
    private final String name;
    private final String category;
    private final double price;
    private int stock;

    // ----------------------------------------------------------
    /**
     * Create a new Item object.
     * @param name
     * @param category
     * @param price
     * @param stock
     */
    public Item(String name, String category, double price, int stock) {
        if (name == null || name.trim().isEmpty() || price < 0 || stock < 0) {
            throw new IllegalArgumentException("Invalid item parameters.");
        }
        this.name = name;
        this.category = (category == null) ? "" : category;
        this.price = price;
        this.stock = stock;
    }

    // ----------------------------------------------------------
    /**
     * Gets the name().
     * @return name
     */
    public String getName() {
        return name;
    }

    // ----------------------------------------------------------
    /**
     * Gets the category.
     * @return category
     */
    public String getCategory() {
        return category;
    }

    // ----------------------------------------------------------
    /**
     * Gets prices.
     * @return price
     */
    public double getPrice() {
        return price;
    }

    // ----------------------------------------------------------
    /**
     * Gets stock.
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    // ----------------------------------------------------------
    /**
     * Decrements stock if not already zero.
     */
    public void decrementStock() {
        if (stock <= 0) {
            throw new IllegalStateException("Item is out of stock.");
        }
        stock--;
    }

    // ----------------------------------------------------------
    /**
     * Returns if Item is in stock
     * @return stock > 0
     */
    public boolean inStock() {
        return stock > 0;
    }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}