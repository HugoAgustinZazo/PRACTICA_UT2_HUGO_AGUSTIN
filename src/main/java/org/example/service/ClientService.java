package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.dao.impl.ClientDaoJdbc;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.exceptions.CannotDeleteException;
import org.example.exceptions.DuplicateClientException;
import org.example.model.Client;
import org.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private IClientDao iClientDao;


    public Client newClient(Client client){
        logger.info("Inserting new CLient: "+client);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iClientDao=new ClientDaoJdbc(conn);
            iClientDao.insert(client);
            logger.info("Product succesfully created. CLientID: "+client.getId());
        } catch (SQLException | DuplicateClientException e) {
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return client;
    }
    public Client updateClient(Client newInfo){
        logger.info("Updating new Client: "+newInfo);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iClientDao=new ClientDaoJdbc(conn);
            if(iClientDao.getById(newInfo.getId()) == null){
                System.out.println("El cliente no existe");
            }else {
                iClientDao.update(newInfo);
                logger.info("Product succesfully update. ProductID: " + newInfo.getId());
            }
            } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return newInfo;
    }
    public boolean deleteClient(Client client){
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iClientDao = new ClientDaoJdbc(conn);
            if (iClientDao.delete(client.getId())){
                logger.info("CLient succesfully delete. CLientID: "+client.getId());
                return true;
             }
        } catch (SQLException | CannotDeleteException e) {
            logger.error(e.getMessage());
        }

        return false;
    }
    public List<Client> getAllClients() {

        List<Client> clientes = new ArrayList<>();
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iClientDao=new ClientDaoJdbc(conn);
            clientes = iClientDao.getAll();
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return clientes;
    }

    public Client getClientByEmail(String email) {
        Client cliente = null;
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iClientDao=new ClientDaoJdbc(conn);
            cliente = iClientDao.getByemail(email);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return cliente;
    }
    public Client getById(int idClient) {
        Client cliente = null;

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iClientDao=new ClientDaoJdbc(conn);
            cliente = iClientDao.getById(idClient);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return cliente;
    }




}
