package org.example.principal;



import org.example.exceptions.CannotDeleteException;
import org.example.exceptions.DuplicateClientException;
import org.example.exceptions.GeneralErrorException;
import org.example.exceptions.InventoryException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.service.ClientService;
import org.example.service.ProductService;
import org.example.service.SalesService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DuplicateClientException, SQLException, InventoryException, GeneralErrorException {
        // Crear instancias de los servicios
        ClientService clientService = new ClientService();
        ProductService productService = new ProductService();
        SalesService salesService = new SalesService();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            // Mostrar menú
            System.out.println("Menú:");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Crear Producto");
            System.out.println("3. Registrar Venta");
            System.out.println("4. Modificar producto");
            System.out.println("5. Ver Cliente que Más Compra");
            System.out.println("6. Borrar cliente");
            System.out.println("7. Borrar producto");
            System.out.println("8. Consulta de clientes");
            System.out.println("9. Consulta de productos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (option) {
                case 1:
                    // Crear Cliente
                    System.out.println("Ingrese el nombre del cliente:");
                    String clientName = scanner.nextLine();
                    System.out.println("Ingrese el apellido del cliente:");
                    String clientSurname = scanner.nextLine();
                    System.out.println("Ingrese el email del cliente:");
                    String clientEmail = scanner.nextLine();

                    Client newClient = new Client();
                    newClient.setName(clientName);
                    newClient.setSurname(clientSurname);
                    newClient.setEmail(clientEmail);
                    newClient.setCreateDate(LocalDateTime.now());
                    newClient.setUpdateDate(LocalDateTime.now());
                    clientService.newClient(newClient);
                    break;

                case 2:
                    // Crear Producto
                    System.out.println("Ingrese el nombre del producto:");
                    String productName = scanner.nextLine();
                    System.out.println("Ingrese la descripción del producto:");
                    String productDescription = scanner.nextLine();
                    System.out.println("Ingrese el stock del producto:");
                    int productStock = scanner.nextInt();
                    System.out.println("Ingrese el precio del producto:");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consumir nueva línea

                    Product newProduct = new Product();
                    newProduct.setName(productName);
                    newProduct.setDescription(productDescription);
                    newProduct.setStock(productStock);
                    newProduct.setPrice(productPrice);
                    newProduct.setCreateDate(LocalDateTime.now());
                    newProduct.setUpdateDate(LocalDateTime.now());
                    productService.newProduct(newProduct);
                    System.out.println("Producto creado: " + newProduct);
                    break;

                case 3:
                    // Registrar Venta
                    System.out.println("Ingrese el ID del cliente:");
                    int clientId = scanner.nextInt();
                    System.out.println("Ingrese el ID del producto:");
                    int productId = scanner.nextInt();
                    System.out.println("Ingrese la cantidad:");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    Client client = clientService.getById(clientId);
                    Product product = productService.getById(productId);

                        salesService.newSale(product, client, quantity);
                        System.out.println("Venta registrada: " + quantity + " unidades de " + product.getName() + " para " + client.getName());

                    break;

                case 4:
                    System.out.println("Ingrese el ID del producto a actualizar:");
                    int productIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    Product productToUpdate = productService.getById(productIdToUpdate);
                    if (productToUpdate != null) {
                        System.out.println("Ingrese el nuevo nombre del producto:");
                        String newProductName = scanner.nextLine();
                        System.out.println("Ingrese la nueva descripción del producto:");
                        String newProductDescription = scanner.nextLine();
                        System.out.println("Ingrese el nuevo stock del producto:");
                        int newProductStock = scanner.nextInt();
                        System.out.println("Ingrese el nuevo precio del producto:");
                        double newProductPrice = scanner.nextDouble();
                        scanner.nextLine(); // Consumir nueva línea

                        productToUpdate.setName(newProductName);
                        productToUpdate.setDescription(newProductDescription);
                        productToUpdate.setStock(newProductStock);
                        productToUpdate.setPrice(newProductPrice);
                        productToUpdate.setUpdateDate(LocalDateTime.now());
                        productService.updateProduct(productToUpdate);
                        System.out.println("Producto actualizado: " + productToUpdate);
                    } else {
                        System.out.println("No existe el producto con ID " + productIdToUpdate);
                    }
                    break;

                case 5:
                    // Actualizar Cliente
                    System.out.println("Ingrese el ID del cliente a actualizar:");
                    int clientIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    Client clientToUpdate = new Client();
                        System.out.println("Ingrese el nuevo nombre del cliente:");
                        String newClientName = scanner.nextLine();
                        System.out.println("Ingrese el nuevo apellido del cliente:");
                        String newClientSurname = scanner.nextLine();
                        clientToUpdate.setName(newClientName);
                        clientToUpdate.setSurname(newClientSurname);
                        clientToUpdate.setUpdateDate(LocalDateTime.now());
                        clientService.updateClient(clientToUpdate);
                    break;

                case 6:
                    // Borrar Cliente
                    System.out.println("Ingrese el ID del cliente a eliminar:");
                    int clientIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    Client clientToDelete = clientService.getById(clientIdToDelete);
//                    Client clientToDelete = clientService.getAllClients().stream()
//                            .filter(c -> c.getId() == clientIdToDelete)
//                            .findFirst()
//                            .orElse(null);

                        try {
                            if (clientService.deleteClient(clientToDelete)) {
                                System.out.println("Cliente eliminado con éxito.");
                            } else {
                                throw new CannotDeleteException("El cliente no existe.");
                            }
                        } catch (CannotDeleteException e) {
                            System.out.println(e.getMessage());
                        }
                    break;

                case 7:
                    // Borrar Producto
                    System.out.println("Ingrese el ID del producto a eliminar:");
                    int productIdToDelete = scanner.nextInt();
                    scanner.nextLine();

                    Product productToDelete = productService.getById(productIdToDelete);
                        productService.deleteProduct(productToDelete);
                    break;

                case 8:
                    // Buscar Clientes
                    System.out.println("1. Buscar por ID de cliente");
                    System.out.println("2. Mostrar todos los clientes");
                    System.out.println("3. Buscar por email del cliente");
                    System.out.println("Ingrese una opción:");
                    int option2 = scanner.nextInt();
                    scanner.nextLine();

                    switch (option2) {
                        case 1:
                            System.out.println("Ingrese el ID del cliente a buscar:");
                            clientId = scanner.nextInt();
                            scanner.nextLine();

                            Client clientById = clientService.getById(clientId);
                            if (clientById != null) {
                                System.out.println(clientById);
                            } else {
                                System.out.println("No se encontró el cliente con id " + clientId);
                            }
                            break;

                        case 2:
                            List<Client> allClients = clientService.getAllClients();
                            for (Client c : allClients) {
                                System.out.println(c);
                            }
                            break;

                        case 3:
                            System.out.println("Ingrese el email del cliente a buscar:");
                            String email = scanner.nextLine();

                            Client clientByEmail = clientService.getClientByEmail(email);
                            if (clientByEmail != null) {
                                System.out.println(clientByEmail);
                            } else {
                                System.out.println("No se encontró el cliente con email " + email);
                            }
                            break;

                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");

                    }
                    break;

                case 9:
                    // Buscar Productos
                    System.out.println("1. Buscar por ID de producto");
                    System.out.println("2. Mostrar todos los productos");
                    System.out.println("3. Buscar por nombre de producto");
                    System.out.println("Ingrese una opción:");
                    int option3 = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    switch (option3) {
                        case 1:
                            System.out.println("Ingrese el ID del producto a buscar:");
                            int productIdToSearch = scanner.nextInt();
                            scanner.nextLine(); // Consumir nueva línea

                            Product productById = productService.getById(productIdToSearch);
                            if (productById != null) {
                                System.out.println(productById);
                            } else {
                                System.out.println("No se encontró el producto con id " + productIdToSearch);
                            }
                            break;

                        case 2:
                            List<Product> allProducts = productService.getAllProducts();
                            for (Product p : allProducts) {
                                System.out.println(p);
                            }
                            break;

                        case 3:
                            System.out.println("Ingrese el nombre del producto a buscar:");
                            String productNamee = scanner.nextLine();

                            List<Product> productsByName = productService.getProductsByNameAlike(productNamee);
                            if (!productsByName.isEmpty()) {
                                System.out.println("Productos encontrados:");
                                for (Product p : productsByName) {
                                    System.out.println(p);
                                }
                            } else {
                                System.out.println("No se encontraron productos con nombre " + productNamee);
                            }
                            break;

                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");

                    }
                    break;

                case 10:
                    // Ventas
                    System.out.println("1. Mostrar el cliente que más ha comprado");
                    System.out.println("2. Mostrar el producto más vendido");
                    System.out.println("3. Realizar una venta");
                    System.out.println("Ingrese una opción:");
                    int option4 = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    switch (option4) {
                        case 1:
                            Client topClient = salesService.getTopPurchasingClient();
                            if (topClient != null) {
                                System.out.println(topClient);
                            } else {
                                System.out.println("No se encontraron ventas");
                            }
                            break;

                        case 2:
                            Product mostPurchasedProduct = salesService.getMostPurchasedProduct();
                            if (mostPurchasedProduct != null) {
                                System.out.println(mostPurchasedProduct);
                            } else {
                                System.out.println("No se encontraron ventas");
                            }
                            break;

                        case 3:
                            List<Product> allProductsToSell = productService.getAllProducts();
                            if (!allProductsToSell.isEmpty()) {
                                System.out.println("Productos disponibles:");
                                for (int i = 0; i < allProductsToSell.size(); i++) {
                                    System.out.println((i + 1) + ". " + allProductsToSell.get(i));
                                }
                                System.out.println("Ingrese el número del producto a vender:");
                                int indexToSell = scanner.nextInt() - 1;
                                Product productToSell = allProductsToSell.get(indexToSell);
                                System.out.println("Ingrese la cantidad a vender:");
                                int quantityToSell = scanner.nextInt();
                                scanner.nextLine(); // Consumir nueva línea

                                List<Client> allClients = clientService.getAllClients();
                                if (!allClients.isEmpty()) {
                                    System.out.println("Clientes disponibles:");
                                    for (int i = 0; i < allClients.size(); i++) {
                                        System.out.println((i + 1) + ". " + allClients.get(i));
                                    }
                                    System.out.println("Ingrese el número del cliente:");
                                    int indexClient = scanner.nextInt() - 1;
                                    Client clientToSell = allClients.get(indexClient);
                                    salesService.newSale(productToSell, clientToSell, quantityToSell);
                                } else {
                                    System.out.println("No se encontraron clientes");
                                }
                            } else {
                                System.out.println("No se encontraron productos");
                            }
                            break;

                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");

                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            System.out.println();
        } while (option != 0);

        scanner.close();
    }
}