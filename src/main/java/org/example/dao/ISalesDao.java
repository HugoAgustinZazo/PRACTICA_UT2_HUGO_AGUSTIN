package org.example.dao;

import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;

import java.sql.SQLException;

public interface ISalesDao {
    void insert(Sales toCreate) throws SQLException;
    Product getMostPurchasedProduct() throws SQLException;
    Client getTopPurchasingClient();

}
