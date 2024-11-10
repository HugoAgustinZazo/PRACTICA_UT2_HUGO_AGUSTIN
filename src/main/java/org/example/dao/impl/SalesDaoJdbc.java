package org.example.dao.impl;

import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.dao.ISalesDao;
import org.example.exceptions.GeneralErrorException;
import org.example.exceptions.InventoryException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDaoJdbc implements ISalesDao {
    private static final Logger logger = LoggerFactory.getLogger(SalesDaoJdbc.class);
    Connection connection;

    public SalesDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Sales toCreate) throws InventoryException,SQLException {
        String sql = "INSERT INTO SALES (PRODUCT_ID, CLIENT_ID, QUANTITY, DATE_OF_SALE) VALUES(?, ?, ?, ?)";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1,toCreate.getProduct().getId());
            stm.setInt(2,toCreate.getCustomer().getId());
            stm.setInt(3,toCreate.getQuantity());
            stm.setTimestamp(4, Timestamp.valueOf(toCreate.getDateofsale()));
            stm.executeUpdate();

            String sqlProduct = "UPDATE PRODUCT SET STOCK = STOCK - ? WHERE ID = ?";
            try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct)) {
                stmtProduct.setInt(1, toCreate.getQuantity());
                stmtProduct.setInt(2, toCreate.getProduct().getId());
                int rowsAffected = stmtProduct.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    throw new InventoryException("No se puede quedar el stock en negativo" + toCreate.getProduct().getId());
                }
            }
            String sqlClient = "UPDATE CLIENT SET PURCHASES = PURCHASES + 1 WHERE ID = ?";
            try (PreparedStatement stmtClient = connection.prepareStatement(sqlClient)) {
                stmtClient.setInt(1, toCreate.getCustomer().getId());
                int rowsAffected = stmtClient.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                }
            }
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Product getMostPurchasedProduct() throws GeneralErrorException {
        Product producto = null;
        String sql = "{CALL getMostPurchasedProduct()}";
        try(Statement stm = connection.createStatement()){
        ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                int idProducto = rs.getInt("PRODUCT_ID");
                ProductDaoJdbc productDaoJdbc = new ProductDaoJdbc(connection);
                producto = productDaoJdbc.getById(idProducto);
            }
        } catch (SQLException e) {
           throw new GeneralErrorException(e.getMessage());
        }
        return producto;
    }

    @Override
    public Client getTopPurchasingClient()throws GeneralErrorException {
        Client cliente = null;
        String sql = "{CALL getTopPurchasingClient()}";
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                int clientId = rs.getInt("CLIENT_ID");
                ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc(connection);
                cliente = clientDaoJdbc.getById(clientId);
            }
        } catch (SQLException e) {
            throw new GeneralErrorException(e.getMessage());
        }
        return cliente;
    }

    @Override
    public List<Sales> getAllSales() throws GeneralErrorException {
        List<Sales> ventas = new ArrayList<>();
        String sql = "SELECT * FROM SALES";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            IProductDao productDao = new ProductDaoJdbc(connection);
            IClientDao clientDao = new ClientDaoJdbc(connection);
            while (rs.next()) {
                Product product = productDao.getById(rs.getInt("PRODUCT_ID"));
                Client client = clientDao.getById(rs.getInt("CLIENT_ID"));
                ventas.add(new Sales(rs.getInt("SALES_ID"), client, product, rs.getInt("QUANTITY"), rs.getTimestamp("DATE_OF_SALE").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new GeneralErrorException(e.getMessage());
        }
        return ventas;
    }
}

