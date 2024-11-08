package org.example.principal;

import org.example.exceptions.CannotDeleteException;
import org.example.exceptions.DuplicateClientException;
import org.example.model.Client;
import org.example.model.Product;
import org.example.service.ClientService;
import org.example.service.ProductService;
import org.example.service.SalesService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.exceptions;

public class Main {
    public static void main(String[] args) throws DuplicateClientException, SQLException {
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
            System.out.println("4. Ver Producto Más Vendido");
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
                    clientService.newCLient(newClient);
                    System.out.println("Cliente creado: " + newClient);
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

                    Client client = clientService.getAllClients().get(clientId);
                    Product product = productService.getById(productId);

                    if (client != null && product != null) {
                        salesService.newSale(product, client, quantity);
                        System.out.println("Venta registrada: " + quantity + " unidades de " + product.getName() + " para " + client.getName());
                    } else {
                        System.out.println("Cliente o producto no encontrado.");
                    }
                    break;

                case 4:
                    // Ver Producto Más Vendido
                    Product mostPurchasedProduct = salesService.getMostPurchasedProduct();
                    System.out.println("Producto más vendido: " + (mostPurchasedProduct != null ? mostPurchasedProduct.getName() : "No hay ventas registradas."));
                    break;

                case 5:
                    // Ver Cliente que Más Compra
                    Client topPurchasingClient = salesService.getTopPurchasingClient();
                    System.out.println("Cliente que más compra: " + (topPurchasingClient != null ? topPurchasingClient.getName() : "No hay ventas registradas."));
                    break;

                case 6:
                    // Borrar Cliente
                    System.out.println("Ingrese el ID del cliente a eliminar:");
                    int clientIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    Client cLient = null;
                    List<Client> clientes = new ArrayList<>();

                    clientes = clientService.getAllClients();
                    for(Client cl : clientes){
                        if(cl.getId() == clientIdToDelete){
                            cLient = cl;
                        }
                        System.out.println(cl);
                    }
                    System.out.println(clientService.deleteClient(cLient));

                        try {
                            if (clientService.deleteClient(cLient)) {
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
                    scanner.nextLine(); // Consumir nueva línea

                    Product productToDelete = productService.getById(productIdToDelete);
                    if (productToDelete != null) {
                        try {
                            if (productService.deleteProduct(productToDelete)) {
                                System.out.println("Producto eliminado con éxito.");
                            } else {
                                throw new CannotDeleteException("El producto no puede ser borrado");

                            }
                        } catch (CannotDeleteException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 8:
                    // Buscar Clientes
                    System.out.println("1. Buscar por ID de cliente");
                    System.out.println("2. Mostrar todos los clientes");
                    System.out.println("3. Buscar por email del cliente");
                    System.out.println("Ingrese una opción:");
                    int option2 = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea

                    switch (option2) {
                        case 1:
                            System.out.println("Ingrese el ID del cliente a buscar:");
                            clientId = scanner.nextInt();
                            scanner.nextLine(); // Consumir nueva línea

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

                            Client clientByEmail = clientService.getCLientByEmail(email);
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