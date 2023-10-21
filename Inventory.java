import java.sql.*;

class Inventory {
	int id;
	String name;
	int quantity;
	double price;
}

class InventoryDao {
	Connection con = null;
	public InventoryDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "A@kash01");
			con.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void displayItems() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from inventory");
			
			System.out.println("ID   Name               Quantity  Price");
			
			while(rs.next()) {
				System.out.print("\n" + rs.getInt("id") +"    ");
				System.out.print(rs.getString("name"));
				for(int i=1; i<=19 - rs.getString("name").length(); i++) {
					System.out.print(" ");
				}
				System.out.print(rs.getInt("quantity") + "        ");
				System.out.print(rs.getDouble("price"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Inventory getItem(int id) {
		try {
			Inventory item = null;
			
			PreparedStatement st = con.prepareStatement("select * from inventory where id = ?");
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				item = new Inventory();
				item.id = rs.getInt("id");
				item.name = rs.getString("name");
				item.quantity = rs.getInt("quantity");
				item.price = rs.getDouble("price");
			}			
			return item;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Inventory getItem(String name) {
		try {
			Inventory item = null;
			
			PreparedStatement st = con.prepareStatement("select * from inventory where name = ?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				item = new Inventory();
				item.id = rs.getInt("id");
				item.name = rs.getString("name");
				item.quantity = rs.getInt("quantity");
				item.price = rs.getDouble("price");
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int addItem(Inventory item) {
		try {
			PreparedStatement st = con.prepareStatement("insert into inventory(name, quantity, price) values (?, ?, ?)");
			st.setString(1, item.name);
			st.setInt(2, item.quantity);
			st.setDouble(3, item.price);
			
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateQuantity(String name, int quantity) {
		try {
			PreparedStatement st = con.prepareStatement("update inventory set quantity = ? where name = ?");
			st.setInt(1, quantity);
			st.setString(2, name);
			
			return st.executeUpdate();
			//con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int deleteItem(String name) {
		try {
			PreparedStatement st = con.prepareStatement("delete from inventory where name = ?");
			st.setString(1, name);
			return st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int deleteItem(int id) {
		try {
			PreparedStatement st = con.prepareStatement("delete from inventory where id = ?");
			st.setInt(1, id);
			return st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void closeConnection() {
		try {
			if(con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}