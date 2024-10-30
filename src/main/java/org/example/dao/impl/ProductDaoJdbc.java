package org.example.dao.impl;

import org.example.dao.IProductDao;
import org.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class ProductDaoJdbc implements IProductDao{
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    Connection connection;

    public ProductDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Product toCreate) {
        return 0;
    }

    @Override
    public boolean update(Product toModify) {
        return false;
    }

    @Override
    public boolean delete(int idToDelete) {
        return false;
    }

    @Override
    public Product getById(int producto) {
        return null;
    }

    @Override
    public List<Product> getALl() {
        return List.of();
    }

    @Override
    public List<Product> getALlByNameAlike(String name) {
        return List.of();
    }

    @Override
    public boolean substracStock(int idToSubstract) {
        return false;
    }
}
