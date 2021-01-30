package auction;

import client.Client;
import employee.Administrator;
import employee.Broker;
import employee.Employee;
import product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//singleton
public class AuctionHouse {
    private static AuctionHouse instance = null;
    private List<Product> productList = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private List<Auction> auctionList = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private int capacity; // capacity for product list

    private AuctionHouse() {}

    public static AuctionHouse auctionHouseInstance() {
        // if a class instance exists, it is returned
        // only create a new instance if it does not exist already
        if (instance == null)
            instance = new AuctionHouse();
        return instance;
    }

    Lock lock = new ReentrantLock();
    Condition notVid = lock.newCondition();
    Condition notFull = lock.newCondition();

    // only administrators can do this
    public void addProduct(Product productNew) {
        lock.lock();
        try {
            while(productList.size() == capacity) {
                notFull.await();
            }
//            System.out.println(productList);
            System.out.println("add " + productNew.getName());
            productList.add(productNew);
//            System.out.println(productList);
            notVid.signal();
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // only brokers can do this
    public void removeProduct(Product soldProduct) {
        lock.lock();
        try {
            while (productList.isEmpty()) {
                notVid.await();
            }
            System.out.println("remove " + soldProduct.getName());
            productList.remove(soldProduct);
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }finally {
            lock.unlock();
        }
    }

    public Broker assignBroker() {
        Random random = new Random();
        Broker broker;
        while(true) {
            Employee employee = employees.get(random.nextInt(employees.size()));
            if ( employee.getEmployeeId() / 100 == 2 ) {
                broker = (Broker) employee;
                break;
            }
        }
        return broker;
    }

    public void processRequest(Product requestedProduct, Client client, double maxSum) {
        // house gives client a broker
        client.setPersonalBroker(assignBroker());
        // add client to the list of clients that requested this product (each product has this list)
        // if no clients have yet requested this product, instace the auction for this product
        if (requestedProduct.getClientsCompeting().isEmpty()) {
            int auctionID;
            if(auctionList.isEmpty()) {
                auctionID = 5000;
            }
            else {
                auctionID = auctionList.get(auctionList.size() - 1).getId();
            }
            Auction auction = new Auction(auctionID, requestedProduct.getId());
            requestedProduct.setAuction(auction);
        }
        requestedProduct.getClientsCompeting().add(client);
        requestedProduct.getMaxSumPerClient().add(maxSum);
        // check if the number of participants in auction is reached
        // if yes, start auction
        if (requestedProduct.getClientsCompeting().size() == requestedProduct.getAuction().getParticipantsNo()) {
            startAuction(requestedProduct.getAuction(), requestedProduct);
        }

    }

    private void startAuction(Auction auction, Product product) {
        auction.start(product);
    }


    public double giveMaxBet(List<Double> betsList) {
        return Collections.max(betsList);
    }


    public List<Employee> getEmployees() { return employees; }

    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Administrator getAdministrator(int adminId) {
        for(Employee e : employees) {
            if(e.getEmployeeId() == adminId) {
                return (Administrator) e;
            }
        }
        return null;
    }

}
