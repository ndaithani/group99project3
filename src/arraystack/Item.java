package arraystack;

public class Item {
    private final String name;
    private final String category;
    private final double price;
    private int stock;

    public Item(String name, String category, double price, int stock) {
        if (name == null || name.trim().isEmpty() || price < 0 || stock < 0) {
            throw new IllegalArgumentException("Invalid item parameters.");
        }
        this.name = name;
        this.category = (category == null) ? "" : category;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, double price, int stock) {
        this(name, "", price, stock);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decrementStock() {
        if (stock <= 0) {
            throw new IllegalStateException("Item is out of stock.");
        }
        stock--;
    }

    public boolean inStock() {
        return stock > 0;
    }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}