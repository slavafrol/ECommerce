import java.util.HashMap;
import java.util.Map;

/*
 * class Product defines a product for sale by the system. 
 * 
 * A product belongs to one of the 5 categories below. 
 * 
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods  
 * 
 * Some products
 */
public class Product
{
	public static enum Category {GENERAL, CLOTHING, BOOKS, FURNITURE, COMPUTERS, SHOES};
	
	private String name;
	private String id;
	private Category category;
	private double price;
	private int stockCount;
	private Map<String, Integer> ratings = new HashMap<String, Integer>(); //Map for ratings with keys being 1 to 5 stars, and values being the times a certain rating was put
	public Product()
	{
		this.name = "Product";
		this.id = "001";
		this.category = Category.GENERAL;
		this.stockCount = 0;
		for (int i = 1; i<=5; i++){ //Every time we create a product object, fill in the ratings with "1":0, "2":0, "3":0, "4":0, and "5":0
			ratings.put(Integer.toString(i), 0);
		}
	}
	
	public Product(String id)
	{
		this("Product", id, 0, 0, Category.GENERAL);
		for (int i = 1; i<=5; i++){
			ratings.put(Integer.toString(i), 0);
		}
	}

	public Product(String name, String id, double price, int stock, Category category)
	{
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stock;
		this.category = category;
		for (int i = 1; i<=5; i++){
			ratings.put(Integer.toString(i), 0);
		}
	}

	public boolean validOptions(String productOptions)
	{
		//Fix for it to be impossible to order nonbook/nonshoes by using orderbook and ordershoes commands
		if (!(productOptions.equals(""))){
			return false;
		}
		return true;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	/*
	 * Return the number of items currently in stock for this product
	 */
	public int getStockCount(String productOptions)
	{
		return stockCount;
	}
	/*
	 * Set (or replenish) the number of items currently in stock for this product
	 */
	public void setStockCount(int stockCount, String productOptions)
	{
		this.stockCount = stockCount;
	}
	/*
	 * Reduce the number of items currently in stock for this product by 1 (called when a product has
	 * been ordered by a customer)
	 */
	public void reduceStockCount(String productOptions)
	{
		stockCount--;
	}
	
	public void print()
	{
		System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f", id, category, name, price);
	}

	public void setRating(String rating)
	{
		ratings.put(rating, ratings.get(rating)+1); //increments a rating count by 1
	}

	public Map<String, Integer> getRatings()
	{
		return ratings; //returns the map of ratings
	}
	
	public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.id.equals(otherP.id);
	}
}
