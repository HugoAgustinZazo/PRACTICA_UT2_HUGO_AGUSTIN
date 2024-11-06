package org.example.dao;

import org.example.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface IClientDao {
    int insert(Client tocreate) throws SQLException;
    boolean update(Client toUpdate);
    boolean delete(int idtodelete);
    boolean incrementPurchase(int clientid, int amount);
    Client getById(int clientid);
    List<Client> getAll();
    Client getByemail(String email);
}
