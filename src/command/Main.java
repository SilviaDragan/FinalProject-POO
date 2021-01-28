package command;

import auction.AuctionHouse;
import product.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // create an instace of the House
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        // class reader will help read information about employees, clients and products
        Reader reader = new Reader();
        String employeesFile = "employees.csv";
        String clientsFile = "clients.csv";
        String productsFile = "products.csv";
        auctionHouse.setEmployees(reader.readEmployeesCSV(employeesFile));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        auctionHouse.setProductList(reader.readProductsCSV(productsFile));
//        for(Employee e: auctionHouse.getEmployees()) {
//            System.out.println(e);
//        }
//        for(Client c: auctionHouse.getClientList()) {
//            System.out.println(c.toString());
//        }
//        for(Product p : auctionHouse.getProductList()) {
//            System.out.println(p);
//        }
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
            }
            commandTaker.doCommands();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
