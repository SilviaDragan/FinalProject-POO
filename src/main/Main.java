package main;

import auction.AuctionHouse;
import employee.Administrator;
import employee.Broker;
import employee.Employee;
import main.command.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Main class contains the main method in which the input data is read
 * Here, I implemented the Commander design pattern
 */
public class Main {
    public static void main(String[] args) {
        // create an instance of the House
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        auctionHouse.setCapacity(20);
        // class reader will help read information about employees, clients and products
        Reader reader = new Reader();
        String employeesFile = "input/employees.csv";
        String clientsFile = "input/clients.csv";
        String productsFile = "input/products.csv";
        List<Employee> employees = reader.readEmployeesCSV(employeesFile);
        // the the information for the auction house
        auctionHouse.setBrokers(reader.giveBrokerList(employees));
        auctionHouse.setAdministrators(reader.giveAdminsList(employees));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        auctionHouse.setProductList(reader.readProductsCSV(productsFile));
        // the threads will be executed using a thread pool
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(Broker broker : auctionHouse.getBrokers()) {
            broker.setThreadPool(threadPool);
        }
        // read the commands clients, brokers and administrators write in this file
        try {
            File inputFile = new File("input/commands.in");
            CommandTaker commandTaker;
            try (Scanner scanner = new Scanner(inputFile)) {
                commandTaker = new CommandTaker();
                while (scanner.hasNextLine()) {
                    String[] command = scanner.nextLine().split(" ");
                    switch (command[0]) {
                        case "requestProduct" -> productRequest(commandTaker, command);
                        case "addProduct" -> productAddition(auctionHouse.findAdministratorById
                                (Integer.parseInt(command[1])), threadPool, commandTaker, command);
                        case "setAuctionData" -> setData(commandTaker, command);
                        case "listProducts" -> listProducts(commandTaker);
                        default -> throw new IllegalStateException("Unexpected value: " + command[0]);
                    }
                }
            }
            commandTaker.doCommands();
            threadPool.shutdown();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

    }

    /**
     * @param commandTaker takes the command to be executed
     */
    private static void listProducts(CommandTaker commandTaker) {
        ListProducts listProducts = new ListProducts();
        commandTaker.takeCommand(listProducts);
    }

    /**
     * @param commandTaker takes the command to be executed
     * @param command the input data
     */
    private static void setData(CommandTaker commandTaker, String[] command) {
        SetAuctionData setAuctionData;
        setAuctionData = new SetAuctionData(Integer.parseInt(command[2]),
                Integer.parseInt(command[3]), Integer.parseInt(command[4]));
        commandTaker.takeCommand(setAuctionData);
    }

    /**
     * @param administratorById the administrator that wants to add the product
     * @param threadPool the thread pool that will start the thread
     * @param commandTaker takes the command to be executed
     * @param command the input data
     */
    private static void productAddition(Administrator administratorById, ExecutorService threadPool, CommandTaker commandTaker, String[] command) {
        AddProduct addProduct = new AddProduct(administratorById, command, threadPool);
        commandTaker.takeCommand(addProduct);
    }

    /**
     * @param commandTaker takes the command to be executed
     * @param command the input data
     */
    private static void productRequest(CommandTaker commandTaker, String[] command) {
        int clientId = Integer.parseInt(command[1]);
        int productId = Integer.parseInt(command[2]);
        double maxPrice = Double.parseDouble(command[3]);
        ProductRequest productRequest = new ProductRequest(clientId, productId, maxPrice);
        commandTaker.takeCommand(productRequest);
    }
}
