
package application;

/**
 * Represents an item available on the JavaHouse menu.
 *
 * @author Daana
 * @version Nov 12, 2026
 */
public class Item
{
    private String name;
    private String category;
    private double price;
    private int stock;

    /**
     * Creates a menu item.
     *
     * @param name the item's name
     * @param category the item's category
     * @param price the item's price
     * @param stock the available quantity
     */
    public Item(String name, String category, double price, int stock)
    {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getName()
    {
        return name;
    }

    public String getCategory()
    {
        return category;
    }

    public double getPrice()
    {
        return price;
    }

    public int getStock()
    {
        return stock;
    }

    /**
     * Decreases the available stock by one.
     */
    public void decrementStock()
    {
        if (stock <= 0)
        {
            throw new IllegalStateException("Item is out of stock.");
        }

        stock--;
    }

    /**
     * Checks whether the item is available.
     *
     * @return true if stock is greater than zero
     */
    public boolean inStock()
    {
        return stock > 0;
    }

    @Override
    public String toString()
    {
        return name + " - $" + String.format("%.2f", price)
            + " (" + stock + " available)";
    }
}
