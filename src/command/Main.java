package command;

import auction.AuctionHouse;
import employee.Administrator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        // create an instance of the House
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        // class reader will help read information about employees, clients and products
        Reader reader = new Reader();
        String employeesFile = "employees.csv";
        String clientsFile = "clients.csv";
        String productsFile = "products.csv";
        auctionHouse.setEmployees(reader.readEmployeesCSV(employeesFile));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        auctionHouse.setProductList(reader.readProductsCSV(productsFile));

        ExecutorService threadPool = Executors.newCachedThreadPool();

        // read the commands clients, brokers and administrators write in this file
        try {
            File inputFile = new File("commands.in");
            Scanner scanner = new Scanner(inputFile);
            CommandTaker commandTaker = new CommandTaker();
            while(scanner.hasNextLine()) {
                String[] command = scanner.nextLine().split(" ");
                if(command[0].equals("requestProduct")) {
                    int clientId = Integer.parseInt(command[1]);
                    int productId = Integer.parseInt(command[2]);
                    double maxPrice = Double.parseDouble(command[3]);
                    ProductRequest productRequest = new ProductRequest(clientId, productId, maxPrice);
                    commandTaker.takeCommand(productRequest);
                }
                if(command[0].equals("addProduct")) {
                    Administrator administrator = auctionHouse.getAdministrator(Integer.parseInt(command[1]));
                    AddProduct addProduct = new AddProduct(administrator, command, threadPool);
                    commandTaker.takeCommand(addProduct);
                }
                if(command[0].equals("setAuctionData")){
                    Administrator administrator = auctionHouse.getAdministrator(Integer.parseInt(command[1]));
                    SetAuctionData setAuctionData = new SetAuctionData(Integer.parseInt(command[2]),
                            Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                }
            }
            commandTaker.doCommands();
            threadPool.shutdown();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
