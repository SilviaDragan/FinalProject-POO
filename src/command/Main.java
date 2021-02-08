package command;

import auction.AuctionHouse;
import employee.Administrator;
import employee.Broker;
import employee.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        // create an instance of the House
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        auctionHouse.setCapacity(20);
        // class reader will help read information about employees, clients and products
        Reader reader = new Reader();
        String employeesFile = "employees.csv";
        String clientsFile = "clients.csv";
        String productsFile = "products.csv";
        List<Employee> employees = reader.readEmployeesCSV(employeesFile);
        auctionHouse.setBrokers(reader.giveBrokerList(employees));
        auctionHouse.setAdministrators(reader.giveAdminsList(employees));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        auctionHouse.setProductList(reader.readProductsCSV(productsFile));

//        for (Product p : auctionHouse.getProductList()) {
//            System.out.print(p.getName() + " ");
//        }
        System.out.println();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(Broker broker : auctionHouse.getBrokers()) {
            broker.setThreadPool(threadPool);
        }

        // read the commands clients, brokers and administrators write in this file
        try {
            File inputFile = new File("commands.in");
            CommandTaker commandTaker;
            try (Scanner scanner = new Scanner(inputFile)) {
                commandTaker = new CommandTaker();
                while (scanner.hasNextLine()) {
                    String[] command = scanner.nextLine().split(" ");
                    if (command[0].equals("requestProduct")) {
                        int clientId = Integer.parseInt(command[1]);
                        int productId = Integer.parseInt(command[2]);
                        double maxPrice = Double.parseDouble(command[3]);
                        ProductRequest productRequest = new ProductRequest(clientId, productId, maxPrice);
                        commandTaker.takeCommand(productRequest);
                    }
                    if (command[0].equals("addProduct")) {
                        Administrator administrator = auctionHouse.findAdministratorById(Integer.parseInt(command[1]));
                        AddProduct addProduct = new AddProduct(administrator, command, threadPool);
                        commandTaker.takeCommand(addProduct);
                    }
                    if (command[0].equals("setAuctionData")) {
                        SetAuctionData setAuctionData;
                        setAuctionData = new SetAuctionData(Integer.parseInt(command[2]),
                                Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                        commandTaker.takeCommand(setAuctionData);
                    }
                }
            }
            commandTaker.doCommands();
            threadPool.shutdown();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
