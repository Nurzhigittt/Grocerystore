package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean insertProduct(Product product) {
        String sql = """
            INSERT INTO product (name, price, stock_quantity, product_type, expiration_date, category)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        if (product instanceof FoodProduct fp) {
            return runUpdate(sql,
                    fp.getName(), fp.getPrice(), fp.getStockQuantity(),
                    "FOOD", fp.getExpirationDate(), null
            ) > 0;
        } else if (product instanceof NonFoodProduct nfp) {
            return runUpdate(sql,
                    nfp.getName(), nfp.getPrice(), nfp.getStockQuantity(),
                    "NON_FOOD", null, nfp.getCategory()
            ) > 0;
        }
        return false;
    }

    public void getAllProduct() {
        getAllProductsList().forEach(System.out::println);
    }

    public List<Product> getAllProductsList() {
        return runSelect("SELECT * FROM product ORDER BY product_id ");
    }

    public Product getProductById(int id) {
        List<Product> list = runSelect(" SELECT * FROM product WHERE product_id = ? ", id);
        return list.isEmpty() ? null : list.getFirst();
    }

    public List<Product> getProductsByType(String type) {
        return runSelect("SELECT * FROM product WHERE product_type = ? ORDER BY product_id", type);
    }

    public boolean updateFoodProduct(FoodProduct p) {
        String sql = """
            UPDATE product
            SET name = ?, price = ?, stock_quantity = ?, expiration_date = ?
            WHERE product_id = ? AND product_type = 'FOOD'
        """;
        return runUpdate(sql,
                p.getName(), p.getPrice(), p.getStockQuantity(), p.getExpirationDate(),
                p.getProductId()
        ) > 0;
    }

    public boolean updateNonFoodProduct(NonFoodProduct p) {
        String sql = """
            UPDATE product
            SET name = ?, price = ?, stock_quantity = ?, category = ?
            WHERE product_id = ? AND product_type = 'NON_FOOD'
        """;
        return runUpdate(sql,
                p.getName(), p.getPrice(), p.getStockQuantity(), p.getCategory(),
                p.getProductId()
        ) > 0;
    }


    public boolean deleteProduct(int productId) {
        return runUpdate("DELETE FROM product WHERE product_id = ?", productId) > 0;
    }

    public boolean applyDiscount(int productId, double percent) {
        String sql = "UPDATE product SET price = price - (price * ? / 100) WHERE product_id = ?";
        return runUpdate(sql, percent, productId) > 0;
    }

    public List<Product> searchByName(String name) {
        return runSelect("SELECT * FROM product WHERE name ILIKE ? ORDER BY name", "%" + name + "%");
    }

    public List<Product> searchByPriceRange(double min, double max) {
        return runSelect("SELECT * FROM product WHERE price BETWEEN ? AND ? ORDER BY price DESC ", min, max);
    }


    private int runUpdate(String sql, Object... params) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(ps, params);
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            return 0;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    private List<Product> runSelect(String sql, Object... params) {
        List<Product> result = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return result;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(ps, params);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractProduct(rs));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return result;
    }

    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object v = params[i];

            if (v == null) {
                ps.setNull(i + 1, Types.VARCHAR);
            } else {
                ps.setObject(i + 1, v);
            }
        }
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
}
