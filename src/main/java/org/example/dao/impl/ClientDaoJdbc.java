package org.example.dao.impl;

import org.example.dao.IClientDao;
import org.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJdbc implements IClientDao {
    Connection connection;

    public ClientDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Client tocreate) throws SQLException {
        String sql ="INSERT INTO CLIENT (NAME, SURNAME, EMAIL, PURCHASES, CREATE_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            stm.setString(1,tocreate.getName());
            stm.setString(2,tocreate.getSurname());
            stm.setString(3,tocreate.getEmail());
            stm.setInt(4,tocreate.getPurchase());
            stm.setTimestamp(5, Timestamp.valueOf(tocreate.getCreateDate()));
            stm.setTimestamp(6, Timestamp.valueOf(tocreate.getUpdateDate()));
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            while(rs.next()){
                return rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Client toUpdate) {
        String sql = "UPDATE CLIENT SET NAME=?, SURNAME=?, EMAIL=?, PURCHASES=?, UPDATE_DATE=? WHERE ID=?";
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, toUpdate.getName());
            stm.setString(2, toUpdate.getSurname());
            stm.setString(3, toUpdate.getEmail());
            stm.setInt(4, toUpdate.getPurchase());
            stm.setTimestamp(5, Timestamp.valueOf(toUpdate.getUpdateDate()));
            stm.setInt(6, toUpdate.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int idtodelete) {
        String sql = "DELETE FROM CLIENT WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idtodelete);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean incrementPurchase(int clientid, int amount) {
        String sql = "UPDATE CLIENT SET PURCHASES = PURCHASES + ? WHERE ID= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setInt(2, clientid);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Client getById(int clientid) {
        String sql = "SELECT * FROM CLIENT WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientid);
            ResultSet rs = stmt.executeQuery();
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

    @Override
    public List<Client> getAll() {
        List<Client> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENT";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
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
