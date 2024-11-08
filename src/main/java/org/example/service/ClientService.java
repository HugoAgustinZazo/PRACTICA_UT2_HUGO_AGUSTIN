package org.example.service;

import org.example.dao.ConnectionManager;
import org.example.dao.IClientDao;
import org.example.dao.IProductDao;
import org.example.dao.impl.ClientDaoJdbc;
import org.example.dao.impl.ProductDaoJdbc;
import org.example.model.Client;
import org.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private IClientDao iClientDao;


    public Client newCLient(Client client){
        logger.info("Inserting new CLient: "+client);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iClientDao=new ClientDaoJdbc(conn);
            iClientDao.insert(client);
            logger.info("Product succesfully created. CLientID: "+client.getId());
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
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
            iClientDao.update(newInfo);
            logger.info("Product succesfully update. ProductID: "+newInfo.getId());
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return newInfo;
    }
    public boolean deleteClient(Client client){
        logger.info("Deleting Client: "+client);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            iClientDao = new ClientDaoJdbc(conn);
            logger.info("Product succesfully delete. CLientID: "+client.getId());
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return iClientDao.delete(client.getId());
    }
    public List<Client> getAllClients() {
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iClientDao=new ClientDaoJdbc(conn);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return iClientDao.getAll();
    }

    public Client getCLientByEmail(String email) {
        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){
            iClientDao=new ClientDaoJdbc(conn);
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }
        return iClientDao.getByemail(email);
    }




}
