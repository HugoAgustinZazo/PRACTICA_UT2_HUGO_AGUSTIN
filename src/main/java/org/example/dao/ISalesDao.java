package org.example.dao;

import org.example.exceptions.GeneralErrorException;
import org.example.exceptions.InventoryException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;

import java.sql.SQLException;
import java.util.List;

public interface ISalesDao {
    void insert(Sales toCreate) throws InventoryException,SQLException;
    Product getMostPurchasedProduct() throws GeneralErrorException;
    Client getTopPurchasingClient()throws GeneralErrorException;
    List<Sales> getAllSales()throws GeneralErrorException;

}
