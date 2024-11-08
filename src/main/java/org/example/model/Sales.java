package org.example.model;

import java.time.LocalDateTime;

public class Sales {
    int salesid;
    Client customer;
    Product product;
    int quantity;
    LocalDateTime dateofsale;

    public Sales(int salesid, Client customer, Product product, int quantity, LocalDateTime dateofsale) {
        this.salesid = salesid;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.dateofsale = dateofsale;
    }

    public Sales() {

    }

    public int getSalesid() {
        return salesid;
    }

    public void setSalesid(int salesid) {
        this.salesid = salesid;
    }

    public Client getCustomer() {
        return customer;
    }

    public void setCustomer(Client customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateofsale() {
        return dateofsale;
    }

    public void setDateofsale(LocalDateTime dateofsale) {
        this.dateofsale = dateofsale;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "salesid=" + salesid +
                ", customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", dateofsale=" + dateofsale +
                '}';
    }
}
