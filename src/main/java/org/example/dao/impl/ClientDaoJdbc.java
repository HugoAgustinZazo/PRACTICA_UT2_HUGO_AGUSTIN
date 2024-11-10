package org.example.dao.impl;

import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.exceptions.CannotDeleteException;
import org.example.exceptions.DuplicateClientException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJdbc implements IClientDao {
    Connection connection;

    public ClientDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Client tocreate) throws DuplicateClientException, SQLException {
        if (getByemail(tocreate.getEmail()) != null) {
            throw new DuplicateClientException("El cliente con el email " + tocreate.getEmail() + " ya existe");
        } else{
            String sql = "INSERT INTO CLIENT (NAME, SURNAME, EMAIL, PURCHASES, CREATE_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, tocreate.getName());
        stm.setString(2, tocreate.getSurname());
        stm.setString(3, tocreate.getEmail());
        stm.setInt(4, tocreate.getPurchase());
        stm.setTimestamp(5, Timestamp.valueOf(tocreate.getCreateDate()));
        stm.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        stm.executeUpdate();

        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
    }

    @Override
    public boolean update(Client toUpdate) {
        String sql = "UPDATE CLIENT SET NAME=?,SURNAME=?,  UPDATE_DATE=? WHERE ID =?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1,toUpdate.getName());
            stm.setString(2, toUpdate.getSurname());
            stm.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
            stm.setInt(4,toUpdate.getId());
                return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int idtodelete) throws CannotDeleteException,SQLException {
        List<Sales> ventas = new ArrayList<>();
        IProductDao iProductDao = new ProductDaoJdbc(connection);
        String verificarCliente = "SELECT * FROM SALES WHERE CLIENT_ID=?";
        String sql = "DELETE FROM CLIENT WHERE ID =?";
               PreparedStatement stm1 = connection.prepareStatement(verificarCliente);
                   stm1.setInt(1,idtodelete);
                   ResultSet rs = stm1.executeQuery();
                   while(rs.next()){

                       int id_venta = rs.getInt("SALES_ID");
                       Product producto = iProductDao.getById(rs.getInt("PRODUCT_ID"));
                       Client cliente = getById(rs.getInt("CLIENT_ID"));
                       int quantity = rs.getInt("QUANTITY");
                       LocalDateTime fechaventa = rs.getTimestamp("DATE_OF_SALE").toLocalDateTime();

                       Sales venta = new Sales(id_venta,cliente,producto,quantity,fechaventa);
                       ventas.add(venta);
                   }
                    if(ventas!=null){
                        throw new CannotDeleteException("No se puede borrar el cliente ya que esta registrado en las ventas");
                    }else{
                   PreparedStatement stm = connection.prepareStatement(sql);
                       stm.setInt(1, idtodelete);
                       return stm.executeUpdate() > 0;
                   }
    }

    @Override
    public boolean incrementPurchase(int clientid ,int amount) {
        String sql = "UPDATE CLIENT SET PURCHASES + ? WHERE ID =?";
            try(PreparedStatement stm = connection.prepareStatement(sql)){
                    stm.setInt(1,amount);
                    stm.setInt(2,clientid);
                    return stm.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }




        return false;
    }

    @Override
    public Client getById(int clientid) {
        String sql = "SELECT * FROM CLIENT WHERE ID = ?";
            try(PreparedStatement stm = connection.prepareStatement(sql)){
                stm.setInt(1,clientid);
                ResultSet rs = stm.executeQuery();
                while (rs.next()){
                    return new Client(
                            rs.getInt("ID"),
                            rs.getString("NAME"),
                            rs.getString("SURNAME"),
                            rs.getString("EMAIL"),
                            rs.getInt("PURCHASES"),
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
    public List<Client> getAll() {
        List<Client> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENT";
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Client(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("SURNAME"),
                        rs.getString("EMAIL"),
                        rs.getInt("PURCHASES"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getTimestamp("UPDATE_DATE").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public Client getByemail(String email) {
        String sql = "SELECT * FROM CLIENT WHERE EMAIL=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("SURNAME"),
                        rs.getString("EMAIL"),
                        rs.getInt("PURCHASES"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getTimestamp("UPDATE_DATE").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
