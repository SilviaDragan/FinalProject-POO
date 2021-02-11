package unit_testing;
import auction.Auction;
import main.Reader;
import main.command.AddProduct;
import main.command.CommandTaker;
import auction.AuctionHouse;
import client.Client;
import client.LegalPerson;
import client.NaturalPerson;
import employee.Employee;
import main.command.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import product.Product;
import product.furniture.FurnitureBuilder;
import product.jewelery.JeweleryBuilder;
import product.painting.PaintingBuilder;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AuctionHouseTest {
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    @BeforeEach
    public void setUp(){
        Reader reader = new Reader();
        String employeesFile = "D:\\POO\\UnitTesting\\FinalProject-POO-master\\input/employees.csv";
        String clientsFile = "D:\\POO\\UnitTesting\\FinalProject-POO-master\\src\\unit_testing\\clients_test.csv";
        String productsFile = "D:\\POO\\UnitTesting\\FinalProject-POO-master\\src\\unit_testing\\products_test.csv";
        List<Employee> employees = reader.readEmployeesCSV(employeesFile);
        auctionHouse.setBrokers(reader.giveBrokerList(employees));
        auctionHouse.setAdministrators(reader.giveAdminsList(employees));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        auctionHouse.setProductList(reader.readProductsCSV(productsFile));
        auctionHouse.setCapacity(4);

    }
    @Test
    @DisplayName("Test read products")
    @Order(1)
    public void testReadingProducts(){
        List<Product> productList = new ArrayList<>();
        productList.add(new PaintingBuilder().withId(4000).withName("Painting1").withMinimumPrice(150.00)
                .withYear(2020).withPainter("Picasso").withColor("Oil").build());
        productList.add(new JeweleryBuilder().withId(4001).withName("Necklace").withMinimumPrice(140.00)
                .withYear(2021).withMetal("Gold").withPrecious("Precious").build());
        productList.add(new FurnitureBuilder().withId(4002).withName("Chair").withMinimumPrice(30.00)
                .withYear(2020).withType("Kitchen Furniture").withMaterial("Wood").build());
        assertEquals(productList, auctionHouse.getProductList());
    }

    @Test
    @DisplayName("Test read clients")
    @Order(2)
    public void testReadClients() {
        List<Client> clients = new ArrayList<>();
        NaturalPerson c1 = new NaturalPerson(3000, "Florin Dumitrescu","London Street No 7",
                3,1);
        c1.setBirthDate("12.05.1998");
        LegalPerson c2 = new LegalPerson(3001,"Dedeman","5th Street",7,5);
        c2.setCompanyType("SRL");
        c2.setSocialCapital(20000);
        NaturalPerson c3 = new NaturalPerson(3002,"Silvia Dragan","Bucegi Street No 100",6,4);
        c3.setBirthDate("11.07.2000");
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);
        assertEquals(clients, auctionHouse.getClientList());
    }

    @Test
    @DisplayName("Test read brokers")
    @Order(3)
    public void testReadBrokers() {
        assertEquals(auctionHouse.getBrokers().size(), 3);
    }

    @Test
    @DisplayName("Test read administrators")
    @Order(4)
    public void testReadAdministrators() {
        assertEquals(auctionHouse.getAdministrators().size(), 1);
    }

    @Test
    @DisplayName("Test add product")
    @Order(4)
    public void testAddProduct() {
        CommandTaker commandTaker = new CommandTaker();
        File inputFile = new File("D:\\POO\\UnitTesting\\FinalProject-POO-master\\src\\unit_testing\\commands_test.in");
        try {
            Scanner scanner = new Scanner(inputFile);
            String[] command = scanner.nextLine().split(" ");
            ExecutorService threadPool = Executors.newCachedThreadPool();
            AddProduct addProduct = new AddProduct(auctionHouse.getAdministrators().get(0), command, threadPool);
            commandTaker.takeCommand(addProduct);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Product newProduct = new JeweleryBuilder().withId(4020).withName("Ring").withMinimumPrice(380.00)
                .withYear(2021).withMetal("Gold").withPrecious("Precious").build();
        assertNotEquals(newProduct, auctionHouse.getProductList().get(auctionHouse.getProductList().size() -1));
    }

    @Test
    @DisplayName("Test Create Client")
    @Order(5)
    public void testCreateClient() {
        Client client = new NaturalPerson(3003,"Silvia Daniela","Bucegi Street No 100",2,0);
        auctionHouse.getClientList().add(client);
        assertEquals(client, auctionHouse.getClientList().get(auctionHouse.getClientList().size() - 1));
    }

    @Test
    @DisplayName("Test commission for legal person")
    @Order(6)
    public void testCommissionLegal() {
        Client client = new LegalPerson(3004,"Leroy Merlin","West Street No 15",4,1);
        double transaction = 100.00;
        assertEquals(25.00, client.payCommission(transaction));
    }

    @Test
    @DisplayName("Test commission for natural person")
    @Order(7)
    public void testCommissionNatural() {
        Client client = new NaturalPerson(3005,"Lucian Mihai","West Street No 15",4,1);
        double transaction = 100.00;
        assertEquals(20.00, client.payCommission(transaction));
    }

    @Test
    @DisplayName("Test Auction")
    @Order(8)
    public void testAuction() {
        Auction auction = auctionHouse.getAuctionList().get(0);
        Product p = auctionHouse.getProductList().get(0);
        auction.setMaxStepsNo(5);
        auction.setParticipantsNo(3);
        Client c1 = new NaturalPerson(3002,"Silvia Dragan","Bucegi Street No 100",6,4);
        Client c2 = new LegalPerson(3001,"Dedeman","5th Street",7,5);
        Client c3 = new NaturalPerson(3000, "Florin Dumitrescu","London Street No 7",
                3,1);
//        auctionHouse.processRequest(p, c1, 200.00);
//        auctionHouse.processRequest(p, c2, 180.00);
//        auctionHouse.processRequest(p, c3, 175.00);
//        Client winner = auction.getWinner();
//        assertEquals(c1, winner);
    }

}
