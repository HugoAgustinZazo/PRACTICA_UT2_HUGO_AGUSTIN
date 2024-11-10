package org.example.dao.impl;

import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.exceptions.CannotDeleteException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements IProductDao{
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    Connection connection;

    public ProductDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Product toCreate) {
        String sql = "INSERT INTO PRODUCT (NAME, DESCRIPTION, STOCK, PRICE, AVAILABLE, CREATE_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stm.setString(1,toCreate.getName());
            stm.setString(2,toCreate.getDescription());
            stm.setInt(3,toCreate.getStock());
            stm.setDouble(4,toCreate.getPrice());
            stm.setBoolean(5,toCreate.isAvailable());
            stm.setTimestamp(6, Timestamp.valueOf(toCreate.getCreateDate()));
            stm.setTimestamp(7, Timestamp.valueOf(toCreate.getUpdateDate()));
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            while(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return -1;
    }

    @Override
    public boolean update(Product toModify) {
        String sql = "UPDATE PRODUCT SET NAME=?, DESCRIPTION=?, STOCK=?, PRICE=?, AVAILABLE=?, UPDATE_DATE=? WHERE ID=?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1,toModify.getName());
            stm.setString(2,toModify.getDescription());
            stm.setInt(3,toModify.getStock());
            stm.setDouble(4,toModify.getPrice());
            stm.setBoolean(5,toModify.isAvailable());
            stm.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stm.setInt(7,toModify.getId());
            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int idToDelete)throws CannotDeleteException, SQLException {
        List<Sales> ventas = new ArrayList<>();
        IClientDao iClientDao = new ClientDaoJdbc(connection);
        String verificarCliente = "SELECT * FROM SALES WHERE PRODUCT_ID=?";
        String sql = "DELETE FROM PRODUCT WHERE ID =?";
            PreparedStatement stm1 = connection.prepareStatement(verificarCliente);
            stm1.setInt(1,idToDelete);
            ResultSet rs = stm1.executeQuery();
            while(rs.next()){

                int id_venta = rs.getInt("SALES_ID");
                Product producto = getById(rs.getInt("PRODUCT_ID"));
                Client cliente = iClientDao.getById(rs.getInt("CLIENT_ID"));
                int quantity = rs.getInt("QUANTITY");
                LocalDateTime fechaventa = rs.getTimestamp("DATE_OF_SALE").toLocalDateTime();

                Sales venta = new Sales(id_venta,cliente,producto,quantity,fechaventa);
                ventas.add(venta);
            }
            if(ventas!=null){
                throw new CannotDeleteException("No se puede borrar el producto ya que esta registrado en las ventas");
            }else {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, idToDelete);
            return stm.executeUpdate() > 0;
        }
    }

    @Override
    public Product getById(int producto) {
        String sql = "SELECT * FROM PRODUCT WHERE ID=?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1,producto);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                return new Product(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("STOCK"),
                        rs.getDouble("PRICE"),
                        rs.getBoolean("AVAILABLE"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getTimestamp("UPDATE_DATE").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getALl() {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT";
        try(Statement stm = connection.createStatement()){
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
               productos.add(new Product(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("STOCK"),
                        rs.getDouble("PRICE"),
                        rs.getBoolean("AVAILABLE"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getTimestamp("UPDATE_DATE").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Product> getALlByNameAlike(String name) {
        List<Product> productosPorNombre = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT WHERE NAME = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1,name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                productosPorNombre.add(new Product(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("STOCK"),
                        rs.getDouble("PRICE"),
                        rs.getBoolean("AVAILABLE"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getTimestamp("UPDATE_DATE").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productosPorNombre;
    }

    @Override
    public boolean substracStock(int idToSubstract, int amount) {
        String sql = "UPDATE PRODUCT SET STOCK = STOCK - ? WHERE ID = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1,amount);
            stm.setInt(2,idToSubstract);
            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
