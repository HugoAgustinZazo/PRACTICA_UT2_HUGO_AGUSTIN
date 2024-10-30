package org.example.dao.impl;

import org.example.dao.IClientDao;
import org.example.model.Client;

import java.sql.Connection;
import java.util.List;

public class ClientDaoJdbc implements IClientDao {
    Connection connection;

    public ClientDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Client tocreate) {
        return 0;
    }

    @Override
    public boolean update(Client toUpdate) {
        return false;
    }

    @Override
    public boolean delete(Client idtodelete) {
        return false;
    }

    @Override
    public int incrementPurchase(int amount) {
        return 0;
    }

    @Override
    public Client getById(int clientid) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        return List.of();
    }

    @Override
    public Client getByemail(String email) {
        return null;
    }
}
