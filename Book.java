
/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/

public class Book extends Product implements Comparable
{
  private String author;
  private String title;
  private int year;
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS 
     super(name, id, price, 2147483647, Product.Category.BOOKS);
     this.paperbackStock = paperbackStock;
     this.hardcoverStock = hardcoverStock;
     this.title = title;
     this.author = author;
     this.year = year;
  }
    
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
    if (productOptions.equals("Paperback") || productOptions.equals("Hardcover") || productOptions.equals("EBook")){
      return true;
    }
    return false;
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
    if (productOptions.equals("Paperback")){
      return paperbackStock;
    }
    else if (productOptions.equals("Hardcover")){
      return hardcoverStock;
    }
    else if (productOptions.equals("EBook")){
      return super.getStockCount(productOptions);
    }
  	return 1;
	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    if (productOptions.equals("EBook")){
      super.setStockCount(stockCount, productOptions);
    }
    else if (productOptions.equals("Paperback")){
      paperbackStock=stockCount;
    }
    else if (productOptions.equals("Hardcover")){
      hardcoverStock=stockCount;
    }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
    if (productOptions.equals("EBook")){
      super.reduceStockCount(productOptions);
    }
    else if (productOptions.equals("Paperback")){
      paperbackStock--;
    }
    else if (productOptions.equals("Hardcover")){
      hardcoverStock--;
    }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	super.print();
    System.out.print(" Book title: " + title + " Author: " + author + " Year: " + year);
  }

  public boolean isFromAuthor(String author)
  {
    if (author.equals(this.author)){
      return true;
    }
    return false;
  }

  public String getTitle()
  {
    return title; //method that returns a title, created for "RATINGS" action (for a better UI)
  }

  //Method compareTo of Comparable to be able to sort the list of books
  public int compareTo(Object other)
  {
    if (year > ((Book) other).year){ //Casting to book so that Java recognizes it has the year variable
      return 1;
    }
    else if (year == ((Book) other).year){
      return 0;
    }
    return -1;
  }
}
