package org.example.dao;

import org.example.exceptions.CannotDeleteException;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    int insert(Product toCreate);
    boolean update(Product toModify);
    boolean delete(int idToDelete)throws CannotDeleteException, SQLException;
    Product getById(int producto);
    List<Product> getALl();
    List<Product> getALlByNameAlike(String name);
    boolean substracStock(int idToSubstract, int amount);
}
