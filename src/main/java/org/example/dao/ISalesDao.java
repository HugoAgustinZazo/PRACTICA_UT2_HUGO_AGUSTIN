package org.example.dao;

import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;

public interface ISalesDao {
    void insert(Sales toCreate);
    Product getMostPurchasedProduct();
    Client getTopPurchasingClient();

}
