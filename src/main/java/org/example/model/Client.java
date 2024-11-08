package org.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Client {
    int id;
    String name;
    String surname;
    String email;
    int purchase;
    LocalDateTime createDate;
    LocalDateTime updateDate;

    public Client(int id, String name, String surname, String email, int purchase, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.purchase = purchase;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", purchase=" + purchase +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && purchase == client.purchase && Objects.equals(name, client.name) && Objects.equals(surname, client.surname) && Objects.equals(email, client.email) && Objects.equals(createDate, client.createDate) && Objects.equals(updateDate, client.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, purchase, createDate, updateDate);
    }
}
