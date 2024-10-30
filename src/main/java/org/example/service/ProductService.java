package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IProductDao;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private IProductDao productDao;

    public Product newProduct(Product toCreate){
        logger.info("Inserting new Product: "+toCreate);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            productDao.insert(toCreate);
            logger.info("Product succesfully created. ProductID: ");

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return toCreate;
    }


}
