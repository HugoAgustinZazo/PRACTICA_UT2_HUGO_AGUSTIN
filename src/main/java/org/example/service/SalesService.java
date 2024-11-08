package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.dao.ISalesDao;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.dao.impl.SalesDaoJdbc;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class SalesService {
    private static final Logger logger = LoggerFactory.getLogger(SalesService.class);
    private IProductDao productDao;
    private IClientDao clientDao;
    private ISalesDao iSalesDao;

    public void newSale(Product product, Client client, int quantity){
        logger.info("Inserting new Sale Id product: "+product.getId()+" ,CLientID: "+client.getId()+" ,Quantity: "+quantity);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iSalesDao = new SalesDaoJdbc(conn);
            //Sales sale = new Sales(product,client,quantity);
            //iSalesDao.insert(sale);
            logger.info("Product succesfully created. ProductID: ");

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

    }


}
