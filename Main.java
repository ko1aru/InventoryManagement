import java.util.*;
import java.sql.*;

public class Main {
	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		
		while(running) {
			System.out.println("**********Select an option**********");
			try {
				System.out.println("1. Display all items\n"
								 + "2. Add an item\n"
								 + "3. Update the quantity of an item\n"
								 + "4. Search for an item\n"
								 + "5. Delete an item\n"
								 + "6. Exit\n");
				int option = sc.nextInt();
				
				if(option > 5) {
					throw new Exception("Enter a valid response!");
				}
			
				switch(option) {
				case 1:
					new InventoryDao().displayItems();
					System.out.println("\n\n");
					break;
					
				case 2: //ADD
					Inventory item = new Inventory();
					
					System.out.println("Enter the name");
					item.name = sc.next();
					
					System.out.println("Enter the quantity");
					item.quantity = sc.nextInt();
					
					System.out.println("Enter the price");
					item.price = sc.nextDouble();
					
					new InventoryDao().addItem(item);
					
					System.out.println("Item added!");
					System.out.println("\n\n");
					break;
					
				case 3: //UPDATE
					System.out.println("Enter the item's name for which quantity to be updated");
					String name = sc.next();
					System.out.println("Enter the quantity");
					int quantity = sc.nextInt();
					
					System.out.println(new InventoryDao().updateQuantity(name, quantity) + " row(s) affected");
					
					System.out.println("Quantity updated!");
					System.out.println("\n\n");
					break;
					
				case 4: //SEARCH
					try {
						System.out.println("Select an option\n"
								+ "1. Name\n"
								+ "2. ID");
						int searchOption = sc.nextInt();
						sc.nextLine();
						Inventory searchedItem = null;
						
						switch(searchOption) {
						case 1:
							System.out.println("Enter the name");
							String itemName = sc.next();
							searchedItem = new InventoryDao().getItem(itemName);
							break;
							
						case 2:
							System.out.println("Enter the ID");
							int itemId = sc.nextInt();
							searchedItem = new InventoryDao().getItem(itemId);
							break;
						}
						
						if(searchedItem != null) {
							System.out.println("ID   Name               Quantity  Price\n");
							System.out.print(searchedItem.id +"    ");
							System.out.print(searchedItem.name);
							for(int i=1; i<=19 - searchedItem.name.length(); i++) {
								System.out.print(" ");
							}
							System.out.print(searchedItem.quantity + "        ");
							System.out.print(searchedItem.price);
						} else {
							throw new NullPointerException();
						}
						
					} catch(NullPointerException np) {
						System.out.println("Enter a valid name or ID!");
					}
					
					System.out.println("\n\n");
					break;
					
				case 5: //DELETE
					System.out.println("Select an option\n"
							+ "1. Name\n"
							+ "2. ID");
					int searchOption = sc.nextInt();
					
					switch(searchOption) {
					case 1:
						System.out.println("Enter the name");
						String itemName = sc.next();
						System.out.println(new InventoryDao().deleteItem(itemName) + " row(s) affected");
						break;
						
					case 2:
						System.out.println("Enter the ID");
						int itemId = sc.nextInt();
						System.out.println(new InventoryDao().deleteItem(itemId) + " row(s) affected");
						break;
					}
					
					System.out.println("Item deleted!");
					System.out.println("\n\n");
					break;
					
				case 6:
					new InventoryDao().closeConnection();
					//System.out.println("**********APPLICATION CLOSED SUCCESSFULLY**********");
					running = false;
					break;
				}
				
			} catch (Exception e) {
			}
		}		
	}
}
