package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IProductDao;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private IProductDao productDao;

    public Product newProduct(Product toCreate){
        logger.info("Inserting new Product: "+toCreate);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            productDao.insert(toCreate);
            logger.info("Product succesfully created. ProductID: "+toCreate.getId());

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return toCreate;
    }
    public Product updateProduct(Product newInfo){
        logger.info("Updating new Product: "+newInfo);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            productDao.update(newInfo);
            logger.info("Product succesfully update. ProductID: "+newInfo.getId());


        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return newInfo;
    }
    public boolean deleteProduct(Product prod){
        logger.info("Deleting Product: "+prod);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            if(productDao.delete(prod.getId())){
                return true;
            }
            logger.info("Product succesfully delete. ProductID: "+prod.getId());
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return false;
    }
    public Product getById(int productId) {
        Product producto = null;

        logger.info("Product: "+productId);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            producto = productDao.getById(productId);
            logger.info("Product succesfully getting. ProductID: "+productId);


        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return producto;
    }

    public List<Product> getAllProducts() {
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            productDao=new ProductDaoJdbc(conn);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return productDao.getALl();
    }

    public List<Product> getProductsByNameAlike(String name) {
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            productDao=new ProductDaoJdbc(conn);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return productDao.getALlByNameAlike(name);
    }


}
