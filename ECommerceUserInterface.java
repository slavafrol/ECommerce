import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

//Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try { //A single try statement around the whole if elif else block
				String action = scanner.nextLine();
				
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks(); 
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();	
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();	
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();	
				}
				else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
				{
					String name = "";
					String address = "";
					
					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();
					
					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();
					
					amazon.createCustomer(name, address);
				}
				else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
				{
						String orderNumber = "";
			
						System.out.print("Order Number: ");
						// Get order number from scanner
						if (scanner.hasNextLine()){
							orderNumber = scanner.nextLine();
						}
						// Ship order to customer
						ProductOrder ord = amazon.shipOrder(orderNumber);
						ord.print();
				}
				else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);
				}
				else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
				// Get product Id from scanner
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
				// Get customer Id from scanner
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					// Order the product
					String ord = amazon.orderProduct(productId, customerId, "");
					// Print Order Number string returned from method in ECommerceSystem
					System.out.println("Order #" + ord);
				}
				else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get the product id
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get the customer id
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get the book format and store it in the options string
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
					String ord = amazon.orderProduct(productId, customerId, options);
					// Order product. Check for error mesage set in ECommerceSystem
					// Print order number string if order number is not null
					System.out.println("Order #" + ord);
				}
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
				{
					String productId = "";
					String customerId = "";
					String options = "";
					
					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options	
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine()){
						options += scanner.nextLine();
					}
					//order shoes
					String ord = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + ord);
				}	
				
				
				else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine()){
						orderNumber = scanner.nextLine();
					}
					// cancel order. Check for error
					amazon.cancelOrder(orderNumber);
				}
				else if (action.equalsIgnoreCase("PRINTBYPRICE")) // PRINT products by price
				{
					amazon.printByPrice();
				}
				else if (action.equalsIgnoreCase("PRINTBYNAME")) // PRINT products by name (alphabetic)
				{
					amazon.printByName();
				}
				else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();
				}
				else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) // sort books by author (alphabetic)
				{
					String author = "";
					System.out.print("Author: ");
					if (scanner.hasNextLine()){
						author = scanner.nextLine();
					}
					amazon.byAuthor(author);
				}

				else if (action.equalsIgnoreCase("ADDTOCART"))
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					System.out.print("\nProduct options (or leave empty): ");
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
					String add = amazon.addToCart(productId, customerId, options);
					System.out.println("Item " + add + " added succesfully");
				}

				else if (action.equalsIgnoreCase("REMCARTITEM"))
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}

					String rem = amazon.remCartItem(productId, customerId);
					System.out.println("Item " + rem + " removed succesfully");
				}

				else if (action.equalsIgnoreCase("PRINTCART"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");

					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}

					amazon.printCart(customerId);
				}

				else if (action.equalsIgnoreCase("ORDERITEMS"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");

					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}

					amazon.orderItems(customerId);
					System.out.println("Items for customer " + customerId + " succesfully ordered");
				}

				else if (action.equalsIgnoreCase("STATS"))
				{
					System.out.println("Popularity statistics: ");
					amazon.stats(); //Prints out the amount of times a product was ordered in a decreasing order for each product that was ordered at least once
				}

				else if (action.equalsIgnoreCase("RATE"))
				{
					String productId = "";
					String rating = "";
					System.out.print("Product Id: ");
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}
					System.out.print("\n Rating from 1 to 5: ");
					if (scanner.hasNextLine()){
						rating = scanner.nextLine();
					}
					amazon.rate(productId, rating); //Puts a rating (from 1 to 5 inclusive) for a product with productId specified by the user
				}

				else if (action.equalsIgnoreCase("RATINGS")){
					System.out.println("Ratings: ");
					amazon.printRatings(); //Prints out all the ratings for all the products
				}

				else if (action.equalsIgnoreCase("PRINTGOODONLY")){
					amazon.printGoodOnly(); //Prints out all the products with average rating >= 4 and their average rating
				}
				System.out.print("\n>");
			}
			//Exception catch blocks
			catch (NoCustomerException e){
				System.out.println(e);
			}
			catch (NoProductException e){
				System.out.println(e);
			}
			catch (NoOrderException e){
				System.out.println(e);
			}
			catch (InvalidAddressException e){
				System.out.println(e);
			}
			catch (InvalidNameException e){
				System.out.println(e);
			}
			catch (InvalidOptionsException e){
				System.out.println(e);
			}
			catch (OutOfStockException e){
				System.out.println(e);
			}
			catch (NullPointerException e){
				System.out.println("Integers only are allowed. Try again");
				//If a user tries to input a non-integer or an integer outside of [1, 5], a NullPointerException occurs during put operation, as such a key does not exist
			}
		}
	}
}
