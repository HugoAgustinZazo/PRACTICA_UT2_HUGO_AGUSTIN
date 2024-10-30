package org.example.dao.impl;

import org.example.dao.ISalesDao;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class SalesDaoJdbc implements ISalesDao {
    private static final Logger logger = LoggerFactory.getLogger(SalesDaoJdbc.class);
    Connection connection;

    public SalesDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Sales toCreate) {

    }

    @Override
    public Product getMostPurchasedProduct() {
        return null;
    }

    @Override
    public Client getTopPurchasingClient() {
        return null;
    }
}
