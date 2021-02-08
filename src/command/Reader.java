package command;

import auction.Auction;
import auction.AuctionHouse;
import client.Client;
import client.LegalPerson;
import client.NaturalPerson;
import employee.Administrator;
import employee.Broker;
import employee.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import product.*;

public class Reader {

    protected List<Employee> readEmployeesCSV(String filename) {
        List<Employee> employees = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Employee employee;
                String employeeType = csvRecord.get(0);
                int id = Integer.parseInt(csvRecord.get(1));
                if(employeeType.equals("Administrator")) {
                    employee = new Administrator(id);
                }
                else {
                    employee = new Broker(id);
                }
                employees.add(employee);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    protected List<Broker> giveBrokerList(List<Employee> employees) {
        List<Broker> brokers = new ArrayList<>();
        for (Employee e : employees) {
            if (e instanceof Broker) {
                brokers.add((Broker) e);
            }
        }
        return brokers;
    }

    protected List<Administrator> giveAdminsList(List<Employee> employees) {
        List<Administrator> administrators = new ArrayList<>();
        for (Employee e : employees) {
            if (e instanceof Administrator) {
                administrators.add((Administrator) e);
            }
        }
        return administrators;
    }

    protected List<Client> readClientsCSV(String filename) { 
        List<Client> clients = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Client client;
                String clientType = csvRecord.get(0);
                int id = Integer.parseInt(csvRecord.get(1));
                int tries = Integer.parseInt((csvRecord.get(4)));
                int wins = Integer.parseInt((csvRecord.get(5)));
                if(clientType.equals("Natural")) {
                    client = new NaturalPerson(id, csvRecord.get(2), csvRecord.get(3), tries, wins);
                    ((NaturalPerson) client).setBirthDate(csvRecord.get(6));
                }
                else {
                    client = new LegalPerson(id, csvRecord.get(2), csvRecord.get(3), tries, wins);
                    ((LegalPerson) client).setCompanyType(csvRecord.get(6));
                    ((LegalPerson) client).setSocialCapital(Double.parseDouble(csvRecord.get(7)));
                }
                clients.add(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    protected List<Product> readProductsCSV(String filename) {
        List<Product> products = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Product product;
                String productType = csvRecord.get(0);
                if(productType.equals("Painting")) {
                    product = buildPainting(csvRecord);
                }
                else if(productType.equals("Jewelery")){
                    product = buildJewelery(csvRecord);
                }
                else {
                    product = buildFurniture(csvRecord);
                }
                AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
                int auctionID;
                if(auctionHouse.getAuctionList().isEmpty()) {
                    auctionID = 5000;
                }
                else {
                    auctionID = auctionHouse.getAuctionList().get(auctionHouse.getAuctionList().size() - 1).getId() + 1;
                }
                Auction auction = new Auction(auctionID, product.getId());
                auctionHouse.getAuctionList().add(auction);
                product.setAuction(auction);
                products.add(product);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product buildFurniture(CSVRecord csvRecord) {
        Product product;
        FurnitureBuilder builder = new FurnitureBuilder()
                .withId(Integer.parseInt(csvRecord.get(1)))
                .withName(csvRecord.get(2))
                .withMinimumPrice(Double.parseDouble(csvRecord.get(3)))
                .withYear(Integer.parseInt(csvRecord.get(4)))
                .withType(csvRecord.get(5))
                .withMaterial(csvRecord.get(6));
        product = builder.build();
        return product;
    }

    private Product buildJewelery(CSVRecord csvRecord) {
        Product product;
        JeweleryBuilder builder = new JeweleryBuilder()
                .withId(Integer.parseInt(csvRecord.get(1)))
                .withName(csvRecord.get(2))
                .withMinimumPrice(Double.parseDouble(csvRecord.get(3)))
                .withYear(Integer.parseInt(csvRecord.get(4)))
                .withMetal(csvRecord.get(5))
                .withPrecious(csvRecord.get(6));
        product = builder.build();
        return product;
    }

    private Product buildPainting(CSVRecord csvRecord) {
        Product product;
        PaintingBuilder builder = new PaintingBuilder()
                .withId(Integer.parseInt(csvRecord.get(1)))
                .withName(csvRecord.get(2))
                .withMinimumPrice(Double.parseDouble(csvRecord.get(3)))
                .withYear(Integer.parseInt(csvRecord.get(4)))
                .withPainter(csvRecord.get(5))
                .withColor(csvRecord.get(6));
        product = builder.build();
        return product;
    }

}
