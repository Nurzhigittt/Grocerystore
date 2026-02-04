package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insertProduct(Product product) {
        String sql = """
            INSERT INTO product (name, price, stock_quantity, product_type, expiration_date, category)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStockQuantity());

            if (product instanceof FoodProduct fp) {
                ps.setString(4, "FOOD");
                ps.setDate(5, Date.valueOf(fp.getExpirationDate()));
                ps.setNull(6, Types.VARCHAR);
            } else if (product instanceof NonFoodProduct nfp) {
                ps.setString(4, "NON_FOOD");
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, nfp.getCategory());
            }

            ps.executeUpdate();
            ps.close();
            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void getAllProduct() {
        String sql = "SELECT * FROM product ORDER BY product_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                System.out.println(product);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE product_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = extractProduct(rs);
                rs.close();
                ps.close();
                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    public boolean updateFoodProduct(FoodProduct product) {
        String sql = """
            UPDATE product
            SET name = ?, price = ?, stock_quantity = ?, expiration_date = ?
            WHERE product_id = ? AND product_type = 'FOOD'
        """;

        return executeUpdate(product, sql, product.getExpirationDate(), null);
    }

    public boolean updateNonFoodProduct(NonFoodProduct product) {
        String sql = """
            UPDATE product
            SET name = ?, price = ?, stock_quantity = ?, category = ?
            WHERE product_id = ? AND product_type = 'NON_FOOD'
        """;

        return executeUpdate(product, sql, null, product.getCategory());
    }

    private boolean executeUpdate(Product product, String sql, String expiration, String category) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStockQuantity());

            if (expiration != null) ps.setString(4, expiration);
            else ps.setString(4, category);

            ps.setInt(5, product.getProductId());

            int rows = ps.executeUpdate();
            ps.close();

            if (rows > 0) {
                System.out.println("Product updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);

            int rows = ps.executeUpdate();
            ps.close();

            if (rows > 0) {
                System.out.println("Product deleted.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public boolean applyDiscount(int productId, double percent) {
        String sql = "UPDATE product SET price = price - (price * ? / 100) WHERE product_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, percent);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();
            ps.close();

            if (rows > 0) {
                System.out.println("Discount applied!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Product> searchByName(String name) {
        String sql = "SELECT * FROM product WHERE name ILIKE ? ORDER BY name";
        List<Product> result = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return result;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractProduct(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        String sql = "SELECT * FROM product WHERE price BETWEEN ? AND ? ORDER BY price DESC";
        List<Product> result = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return result;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, min);
            ps.setDouble(2, max);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractProduct(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return result;
    }

    public List<Product> searchByMinPrice(double min) {
        String sql = "SELECT * FROM product WHERE price >= ? ORDER BY price DESC";
        List<Product> result = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return result;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, min);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractProduct(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return result;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("product_id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock_quantity");
        String type = rs.getString("product_type");

        if ("FOOD".equals(type)) {
            return new FoodProduct(id, name, price, stock, rs.getString("expiration_date"));
        } else {
            return new NonFoodProduct(id, name, price, stock, rs.getString("category"));
        }
    }
    public List<Product> getProductsByType(String type){
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE product_type = ? ORDER BY product_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return products;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return products;

    }
    public List<Product> getAllProductsList(){
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product ORDER BY product_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return products;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(extractProduct(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return products;
    }
}
