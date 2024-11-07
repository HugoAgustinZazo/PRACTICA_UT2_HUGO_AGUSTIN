package org.example.dao;

import org.example.model.Client;

import java.util.List;

public interface IClientDao {
    int insert(Client tocreate);
    boolean update(Client toUpdate);
    boolean delete(int idtodelete);
    boolean incrementPurchase(int clientid,int amount);
    Client getById(int clientid);
    List<Client> getAll();
    Client getByemail(String email);
}
