package org.example.dao.impl;

import org.example.dao.ISalesDao;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SalesDaoJdbc implements ISalesDao {
    private static final Logger logger = LoggerFactory.getLogger(SalesDaoJdbc.class);
    Connection connection;

    public SalesDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Sales toCreate) throws SQLException {
        String sql = "INSERT INTO SALES (PRODUCT_ID, CLIENT_ID, QUANTITY, DATE_OF_SALE) VALUES(?, ?, ?, ?)";
        try(PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stm.setInt(1,toCreate.getProduct().getId());
            stm.setInt(2,toCreate.getCustomer().getId());
            stm.setInt(3,toCreate.getQuantity());
            stm.setTimestamp(4, Timestamp.valueOf(toCreate.getDateofsale()));
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product getMostPurchasedProduct() throws SQLException {
        Product producto = null;
        String sql = "SELECT PRODUCT_ID, SUM(QUANTITY) AS total_quantity FROM SALES GROUP BY PRODUCT_ID ORDER BY total_quantity DESC LIMIT 1";
        try(Statement stm = connection.createStatement()){
        ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                int idProducto = rs.getInt("PRODUCT_ID");
                ProductDaoJdbc productDaoJdbc = new ProductDaoJdbc(connection);
                producto = productDaoJdbc.getById(idProducto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public Client getTopPurchasingClient() {
        Client cliente = null;
        String sql = "SELECT CLIENT_ID, SUM(QUANTITY) AS total_quantity FROM SALES GROUP BY CLIENT_ID ORDER BY total_quantity DESC LIMIT 1";
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                int clientId = rs.getInt("CLIENT_ID");
                ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc(connection);
                cliente = clientDaoJdbc.getById(clientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}

