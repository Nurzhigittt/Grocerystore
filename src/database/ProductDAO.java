package database;

import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    public void insertProduct(Product product){
        String sql = "INSERT INTO product(name, price, stock_quantity) VALUES (?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStockQuantity());

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0){
                System.out.println("Product inserted successfully!");
            }
            statement.close();

        }
        catch(SQLException e){
            System.out.println("Insert failed!");
            e.printStackTrace();
        }
        finally{
            DatabaseConnection.closeConnection(connection);
        }

    }
    public void getAllProduct() {
        String sql = "SELECT * FROM product";
        Connection connection = DatabaseConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n ---ALL PRODUCT FROM DATABASE ---");

            while(resultSet.next()){
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock_quantity = resultSet.getInt("stock_quantity");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Stock Quantity: " + stock_quantity);
                System.out.println("---");
            }
            resultSet.close();
            statement.close();

        }
        catch(SQLException e){
            System.out.println("Select failed!");
            e.printStackTrace();
        }finally{
            DatabaseConnection.closeConnection(connection);
        }

    }
}