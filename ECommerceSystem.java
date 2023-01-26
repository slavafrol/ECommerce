import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private Map<String, Product>  products = new HashMap<String, Product>(); //Map for products
    //When I need to iterate over products, I either create a set out with products.values() as a source
    //Or iterate over products.entrySet()
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    private Map<Product, Integer> stats = new HashMap<Product, Integer>(); //Map for stats
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    RuntimeException e; //Variable of type RuntimeException that stores the last exception
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
      try{ //Loads products from a file into the map "products"
        products = fromFile("products.txt");
      }
      catch (IOException e){ //Catch statement for IOException
        System.out.println(e.getMessage());
      }
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
    }
    
    private Map<String, Product> fromFile(String filename) throws IOException
    {
      Map<String, Product> prods = new HashMap<String, Product>();
      Scanner in = new Scanner(new File(filename));
      while (in.hasNextLine()){
        String id = generateProductId();
        Product.Category category = Product.Category.valueOf(in.nextLine());
        String name = in.nextLine();
        Double price = Double.parseDouble(in.nextLine());
        //Books and shoes have their 4th line non empty with multiple variables and their 5th line is non-empty
        if (category == Product.Category.BOOKS){
          int paperback = in.nextInt();
          int hardcover = in.nextInt();
          in.nextLine();
          Scanner additional = new Scanner(in.nextLine()); //Separate scanner that accepts the last line as its source
          additional.useDelimiter(":"); //Delimiter used
          String title = additional.next();
          String author = additional.next();
          int year = additional.nextInt();
          prods.put(id, new Book(name, id, price, paperback, hardcover, title, author, year));
        }
        else if (category == Product.Category.SHOES){ //Had to add SHOES to the enum variable in product for this to work
          int[] blackstockcount = new int[5];
          int[] brownstockcount = new int[5];
          for (int i = 0; i<5; i++){ //4th line for shoes would contain 10 different stock counts, for 2 different colors and 5 different sizes
            blackstockcount[i] = in.nextInt();
          }
          for (int i = 0; i<5; i++){
            brownstockcount[i] = in.nextInt();
          }
          in.nextLine();
          Scanner additional2 = new Scanner(in.nextLine());
          additional2.useDelimiter(":");
          String brand = additional2.next(); //5th line contains brand and type separated by a ":"
          String type = additional2.next();
          prods.put(id, new Shoes(name, id, price, blackstockcount, brownstockcount, brand, type));
        }
        else{
          int stockcount = Integer.parseInt(in.nextLine());
          prods.put(id, new Product(name, id, price, stockcount, category));
          in.nextLine();
        }
      }
      return prods;
    }

    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public void getErrorMessage()
    {
    	throw e;
    }
    
    public void printAllProducts()
    {
    Set<Product> set = new HashSet<Product>(products.values());
    	for (Product p : set)
    		p.print();
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
      Set<Product> set = new HashSet<Product>(products.values());
    	for (Product p : set){
        if (p.getCategory()==Product.Category.BOOKS){
          p.print();
        }
      }
    }
    
    // Print all current orders
    public void printAllOrders()
    {
      for (ProductOrder po : orders){
        System.out.println("Order # " + po.getOrderNumber() + " Customer ID: " + po.getCustomer().getId() + " Product ID: " + po.getProduct().getId() + " Product Name: " + po.getProduct().getName());
      }
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
      for (ProductOrder so : shippedOrders){
        System.out.println("Order # " + so.getOrderNumber() + " Customer ID: " + so.getCustomer().getId() + " Product ID: " + so.getProduct().getId() + " Product Name: " + so.getProduct().getName());
      }
    }
    
    // Print all customers
    public void printCustomers()
    {
    	for (Customer c : customers){
        c.print();
      }
    }

    public void printOrderHistory(String customerId)
    {
      Boolean exists = false;
    	for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          break;
        }
      }
      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }
    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	for (ProductOrder order : orders){
        if (order.getCustomer().getId().equals(customerId)){
          order.print();
        }
      }
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here
      for (ProductOrder order : shippedOrders){
        if (order.getCustomer().getId().equals(customerId)){
          order.print();
        }
      }
    }
    
    public String orderProduct(String productId, String customerId, String productOptions)
    {
      Customer cust = null;
      Product prod = null;
      Boolean exists = false;
    	for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          cust = customer;
          break;
        }
      }
      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }
      exists = false;
      Set<Product> set = new HashSet<Product>(products.values());
    	for (Product product : set){
        if (product.getId().equals(productId)){
          exists = true;
          prod = product;
          break;
        }
      }
      if (!(exists)){
        e = new NoProductException("Product does not exist");
        throw e;
      }
      if (prod.getCategory()!=Product.Category.BOOKS && prod.getCategory()!=Product.Category.SHOES){
        if (!(prod.validOptions(productOptions))){
          e = new InvalidOptionsException("The product is not a book/shoes");
          throw e;
        }
      }
      else{
        if (!(prod.validOptions(productOptions))){
          e = new InvalidOptionsException("The product option is invalid");
          throw e;
        }
      }
    	if (prod.getStockCount(productOptions)==0){
        e = new OutOfStockException("The product is out of stock");
        throw e;
      }
    	ProductOrder prodorder = new ProductOrder(generateOrderNumber(), prod, cust, productOptions);
      prod.reduceStockCount(productOptions);
      orders.add(prodorder);
      if (stats.get(prod)==null) {
        stats.put(prod, 1);
      }
      else {
        stats.put(prod, stats.get(prod)+1);
      }
    	return prodorder.getOrderNumber();
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address)
    {
    	if (name == null || name.equals("")){
        e = new InvalidNameException("Invalid name");
        throw e;
      }
      if (address == null || address.equals("")){
        e = new InvalidAddressException("Invalid address");
        throw e;
      }
      Customer cust = new Customer(generateCustomerId(), name, address);
      customers.add(cust);

    }
    
    public ProductOrder shipOrder(String orderNumber)
    {
      ProductOrder ord = null;
      Boolean exists = false;
    	for (ProductOrder order : orders){
        if (order.getOrderNumber().equals(orderNumber)){
          ord = order;
          exists = true;
          break;
        }
      }
      if (!(exists)){
        e = new NoOrderException("Order does not exist");
        throw e;
      }
      orders.remove(ord);
      shippedOrders.add(ord);
    	return ord;
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber)
    {
      ProductOrder ord = null;
      Boolean exists = false;
    	for (ProductOrder order : orders){
        if (order.getOrderNumber().equals(orderNumber)){
          ord = order;
          exists = true;
          break;
        }
      }
      if (!(exists)){
        e = new NoOrderException("Order does not exist");
        throw e;
      }
      orders.remove(ord);
    }
    
    //Now it's print by price and print by name instead of just sorting
    public void printByPrice() //Bubble sort algorithm (this was my first year project, I'm sorry)
    {
      ArrayList<Product> list = new ArrayList<Product>(products.values());
      Product temp = null;
      for (int i = 0; i<list.size()-1; i++){
        for (int j = 0; j<list.size()-1-i; j++){
          Product a = list.get(j);
          Product b = list.get(j+1);
          if (a.getPrice()>b.getPrice()){
            temp = a;
            list.set(j, b);
            list.set(j+1, temp);
          }
        }
      }
      for (Product p : list){
        p.print();
      }
    }

    // Sort products alphabetically by product name
    public void printByName()
    {
      ArrayList<Product> list = new ArrayList<Product>(products.values());
      Product temp = null;
      for (int i = 0; i<list.size()-1; i++){
        for (int j = 0; j<list.size()-1-i; j++){
          Product a = list.get(j);
          Product b = list.get(j+1);
          if (a.getName().compareTo(b.getName())>0){
            temp = a;
            list.set(j, b);
            list.set(j+1, temp);
          }
        }
      }
      for (Product p : list){
        p.print();
      }
    }

    // Sort customers alphabetically by name
    public void sortCustomersByName()
    {
      Collections.sort(customers); //Customers has Comparable implemented, so we don't have to create our own sorting algorithm
    }

    //Sort books by author
    public void byAuthor(String author)
    {
      ArrayList<Book> books = new ArrayList<Book>(); //List to store books and later sort it
      Set<Product> set = new HashSet<Product>(products.values());
      for (Product p : set){
        if (p.getCategory()==Product.Category.BOOKS){
          books.add((Book)p);
          if (!(books.get(books.size()-1).isFromAuthor(author))){
            books.remove(books.size()-1);
          }
        }
      }
      Collections.sort(books); //Sorting the list (class books also implements Comparable)
      for (Book b : books){
        b.print();
      }
    }

    public String addToCart(String productId, String customerId, String productOptions)
    {
      Customer cust = null;
      Product prod = null;
      Boolean exists = false;
    	for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          cust = customer;
          break;
        }
      }

      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }

      exists = false;
      Set<Product> set = new HashSet<Product>(products.values());
    	for (Product product : set){
        if (product.getId().equals(productId)){
          exists = true;
          prod = product;
          break;
        }
      }

      if (!(exists)){
        e = new NoProductException("Product does not exist");
        throw e;
      }
    	
      if (prod.getCategory()!=Product.Category.BOOKS && prod.getCategory()!=Product.Category.SHOES){
        if (!(prod.validOptions(productOptions))){
          e = new InvalidOptionsException("The product is not a book / shoes");
          throw e;
        }
      }

      else {
        if (!(prod.validOptions(productOptions))){
          e =  new InvalidOptionsException("The product option is invalid");
          throw e;
        }
      }

    	if (prod.getStockCount(productOptions)==0){
        e = new OutOfStockException("The product is out of stock");
        throw e;
      }
      CartItem item = new CartItem(prod, productOptions);
      Cart custCart = cust.getCart();
      String add = custCart.addItem(item);
      prod.reduceStockCount(productOptions);
      return add;
    }
    public String remCartItem(String productId, String customerId)
    {
      Customer cust = null;
      Product prod = null;
      Boolean exists = false;
    	for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          cust = customer;
          break;
        }
      }

      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }

      exists = false;
      Set<Product> set = new HashSet<Product>(products.values());
    	for (Product product : set){
        if (product.getId().equals(productId)){
          exists = true;
          prod = product;
          break;
        }
      }

      if (!(exists)){
        e = new NoProductException("Product does not exist");
        throw e;
      }
      Cart custCart= cust.getCart();
      String rem = custCart.remItem(productId);
      return rem;
    }

    public void printCart(String customerId)
    {
      Customer cust = null;
      Boolean exists = false;
      for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          cust = customer;
          break;
        }
      }

      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }

      Cart custCart = cust.getCart();

      for (CartItem item : custCart.getItems()){
        item.getProduct().print();
      }
    }

    public void orderItems(String customerId)
    {
      Customer cust = null;
      Boolean exists = false;
      for (Customer customer : customers){
        if (customer.getId().equals(customerId)){
          exists = true;
          cust = customer;
          break;
        }
      }

      if (!(exists)){
        e = new NoCustomerException("Customer does not exist");
        throw e;
      }

      Cart custCart = cust.getCart();
      for (CartItem it : custCart.getItems()){
        Product p = it.getProduct();
        orderProduct(p.getId(), customerId, it.getOption());
        }
      cust.getCart().emptyCart();
    }

    public void stats()
    {
      //Comparator to sort a list consisting of entries from a map. Compares items based on the amount of times they were ordered
      Comparator<Map.Entry<Product, Integer>> comp = new Comparator<Map.Entry<Product, Integer>>() {
        public int compare(Map.Entry<Product, Integer> entry1, Map.Entry<Product,Integer> entry2){
          return Integer.compare(entry1.getValue(), entry2.getValue()) * (-1); //* (-1) so that the comparison is reversed, as we want to get decreasing order during sorting
        }
      };
      List<Map.Entry<Product, Integer>> list = new ArrayList<>(stats.entrySet()); //Create a new list with the entry set from stats map as its source
      Collections.sort(list, comp);
      for (Map.Entry<Product, Integer> entry : list){
        System.out.print("Product: " + entry.getKey().getName() + " ID: " + entry.getKey().getId() + " Times ordered: ");
        System.out.println(entry.getValue());
      }
    }

    public void rate(String productId, String rating)
    {
      Product prod = null;
      Boolean exists = false;
      Set<Product> set = new HashSet<Product>(products.values());
    	for (Product product : set){
        if (product.getId().equals(productId)){
          exists = true;
          prod = product;
          break;
        }
      }
      if (!(exists)){
        e = new NoProductException("Product does not exist");
        throw e;
      }
      prod.setRating(rating);
    }

    public void printRatings()
    {
      Set<Product> set = new HashSet<Product>(products.values());
      for (Product product : set){
        if (product.getCategory()==Product.Category.BOOKS){
          System.out.println(((Book) product).getTitle() + ":");
        }
        else{
          System.out.println(product.getName() + ":");
        }
        Map<String, Integer> ratings = product.getRatings();
        for (Map.Entry<String, Integer> entry : ratings.entrySet()){
          System.out.println(entry.getKey() + "*: " + entry.getValue());
        }
      }
    }

    public void printGoodOnly()
    {
      Set<Product> set = new HashSet<Product>(products.values());
      for (Product product : set){
        Double sum = 0.0;
        int amount = 0;
        Map<String, Integer> ratings = product.getRatings();
        for (Map.Entry<String, Integer> entry : ratings.entrySet()){
          amount += entry.getValue();
          sum += Integer.parseInt(entry.getKey()) * entry.getValue(); //For example, f there are 2 4* ratings, sum would increase by 8
        }
        Double average = sum / amount;
        if (average >= 4){
          product.print();
          System.out.println(" Average rating: " + average);
        }
      }
    }
}

class NoCustomerException extends RuntimeException
{
  public NoCustomerException(){}

  public NoCustomerException(String message)
  {
    super(message);
  }
}

class NoProductException extends RuntimeException
{
  public NoProductException(){}

  public NoProductException(String message)
  {
    super(message);
  }
}

class NoOrderException extends RuntimeException
{
  public NoOrderException(){}

  public NoOrderException(String message)
  {
    super(message);
  }
}

class InvalidAddressException extends RuntimeException
{
  public InvalidAddressException(){}

  public InvalidAddressException(String message)
  {
    super(message);
  }
}

class InvalidNameException extends RuntimeException
{
  public InvalidNameException(){}

  public InvalidNameException(String message)
  {
    super(message);
  }
}

class InvalidOptionsException extends RuntimeException
{
  public InvalidOptionsException(){}

  public InvalidOptionsException(String message)
  {
    super(message);
  }
}

class OutOfStockException extends RuntimeException
{
  public OutOfStockException(){}

  public OutOfStockException(String message)
  {
    super(message);
  }
}
