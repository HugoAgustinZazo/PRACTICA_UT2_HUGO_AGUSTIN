package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.dao.ISalesDao;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.dao.impl.SalesDaoJdbc;
import org.example.exceptions.GeneralErrorException;
import org.example.exceptions.InventoryException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.model.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesService {
    private static final Logger logger = LoggerFactory.getLogger(SalesService.class);
    private IProductDao productDao;
    private IClientDao clientDao;
    private ISalesDao iSalesDao;

    public void newSale(Product product, Client client, int quantity){
        logger.info("Inserting new Sale Id product: "+product.getId()+" ,CLientID: "+client.getId()+" ,Quantity: "+quantity);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            if(product.getStock() < quantity) throw new InventoryException("No hay suficiente producto para esta venta");
            iSalesDao = new SalesDaoJdbc(conn);
            Sales sale = new Sales();
            sale.setCustomer(client);
            sale.setProduct(product);
            sale.setQuantity(quantity);
            sale.setDateofsale(LocalDateTime.now());
            iSalesDao.insert(sale);
            logger.info("Product succesfully created. ProductID: ");

        } catch (SQLException | InventoryException e) {
            logger.error(e.getMessage());
        }

    }
    public Product getMostPurchasedProduct() throws GeneralErrorException {

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iSalesDao = new SalesDaoJdbc(conn);
            return iSalesDao.getMostPurchasedProduct();
        }catch (SQLException | GeneralErrorException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public Client getTopPurchasingClient() throws GeneralErrorException {
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iSalesDao = new SalesDaoJdbc(conn);
            return iSalesDao.getTopPurchasingClient();
        } catch (SQLException | GeneralErrorException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    public List<Sales> getAllSales(){
        List<Sales> ventas = new ArrayList<>();
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iSalesDao = new SalesDaoJdbc(conn);
            ventas = iSalesDao.getAllSales();
        } catch (SQLException | GeneralErrorException e) {
            logger.error(e.getMessage());
        }
        return ventas;
    }

}
