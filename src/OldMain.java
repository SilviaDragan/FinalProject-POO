import auction.AuctionHouse;
import client.Client;
import client.LegalPerson;
import client.NaturalPerson;
import employee.Administrator;
import employee.Broker;
import employee.Employee;
import product.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class OldMain {

    public static void main(String[] args) {
//        auctionHouse.setCapacity(10);
//        Product painting = new Painting(1, "Painting by Picasso", 1200.00, 1000.00);
//        Product chair = new Furniture(2, "Desk Chair", 25.00, 20.00);
//
//        List<Product> products= new ArrayList<>();
//        auctionHouse.setProductList(products);
//
//        ExecutorService executor = Executors.newCachedThreadPool();
//        executor.execute(new Administrator(painting));
//        executor.execute(new Administrator(chair));
//
//        executor.execute(new Broker(chair));
//        executor.shutdown();

        try {
            File f = new File("commands.in");
            Scanner scanner = new Scanner(f);
            AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
            while(scanner.hasNextLine()) {
                String[] command = scanner.nextLine().split(" ");
                if(command[0].equals("addEmployee")) {
                    if(auctionHouse.getEmployees() == null) {
                        auctionHouse.setEmployees(new ArrayList<>());
                    }
                    if(command[1].equals("Broker"))
                    {
                        auctionHouse.getEmployees().add(new Broker(Integer.parseInt(command[2])));
                    }
                    else if(command[1].equals("Administrator")) {
                        auctionHouse.getEmployees().add(new Administrator(Integer.parseInt(command[2])));
                    }
                }
                else if(command[0].equals("addProduct")) {
                    if(auctionHouse.getProductList() == null) {
                        auctionHouse.setProductList(new ArrayList<>());
                    }
                    if(command[1].equals("Painting")) {
                        Product painting = new Painting(Integer.parseInt(command[2]), command[3],
                                Double.parseDouble(command[4]), Double.parseDouble(command[5]));
                        auctionHouse.getProductList().add(painting);
                    }
                    else if(command[1].equals("Jewelery")) {
                        Product jewelery = new Jewelery(Integer.parseInt(command[2]), command[3],
                                Double.parseDouble(command[4]), Double.parseDouble(command[5]));
                        auctionHouse.getProductList().add(jewelery);
                    }
                    else {
                        Product furniture = new Furniture(Integer.parseInt(command[2]), command[3],
                                Double.parseDouble(command[4]), Double.parseDouble(command[5]));
                        auctionHouse.getProductList().add(furniture);
                    }
                }
                else if(command[0].equals("addClient")) {
                    // do a lot of stuff
                    if(auctionHouse.getClientList() == null) {
                        auctionHouse.setClientList(new ArrayList<>());
                    }
                    Client client;
                    if(command[1].equals("Natural")) {
                        client = new NaturalPerson(Integer.parseInt(command[2]), command[3], command[4],
                                Integer.parseInt(command[4]), Integer.parseInt(command[5]));
                    }
                    else {
                        client = new LegalPerson(Integer.parseInt(command[2]), command[3], command[4],
                                Integer.parseInt(command[4]), Integer.parseInt(command[5]));
                    }
                    auctionHouse.getClientList().add(client);
                    // client sends request
                    // give client a broker
                    // broker takes care of client

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

