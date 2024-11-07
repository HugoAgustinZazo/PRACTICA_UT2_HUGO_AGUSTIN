package org.example.dao;

import org.example.model.Product;

import java.util.List;

public interface IProductDao {
    int insert(Product toCreate);
    boolean update(Product toModify);
    boolean delete(int idToDelete);
    Product getById(int producto);
    List<Product> getALl();
    List<Product> getALlByNameAlike(String name);
    boolean substracStock(int idToSubstract, int amount);
}
