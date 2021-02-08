package auction;

import client.Client;
import employee.Administrator;
import employee.Broker;
import product.Product;
import product.ProductNotFoundException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//singleton
public class AuctionHouse {
    private static AuctionHouse instance = null;
    private List<Product> productList = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private final List<Auction> auctionList = new ArrayList<>();
    private List<Broker> brokers = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();
    // make a command to set this capacity
    private int capacity; // capacity for product list
    private Random rand;
    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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
            System.out.print("Before removing: ");
            for(Product p : auctionHouseInstance().getProductList()) {
                System.out.print(p.getName() + " ");
            }
            productList.remove(soldProduct);
            System.out.println();
            System.out.print("After removing: ");
            for(Product p : auctionHouseInstance().getProductList()) {
                System.out.print(p.getName() + " ");
            }
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }finally {
            lock.unlock();
        }
    }

    public Broker assignBroker() {
        return brokers.get(rand.nextInt(brokers.size()));
    }

    public void processRequest(Product requestedProduct, Client client, double maxSum) {
        // house gives client a broker
        Broker broker = assignBroker();
        Auction auction = requestedProduct.getAuction();
        // check if it's the first client that requested this product
        if (! broker.getClientsMap().containsKey(auction)) {
             broker.getClientsMap().put(auction, new HashMap<>());
        }
        broker.getClientsMap().get(auction).put(client, maxSum);
        // set the client's personal broker and add a participation
        client.setPersonalBroker(broker);
        client.newParticipation();

        // check if the number of participants in auction is reached
        // if yes, start auction
        int currentParticipantsNo = 0;
        for (Broker b : brokers) {
            if (b.getClientsMap().containsKey(auction)) {
                currentParticipantsNo += b.getClientsMap().get(auction).size();
            }
        }
        if (currentParticipantsNo == auction.getParticipantsNo()) {
            startAuction(auction, requestedProduct);
        }

    }

    private void startAuction(Auction auction, Product product) {
        for (Broker b : brokers) {
            b.setCurrentAuction(auction);
            b.setSoldProduct(product);
            auction.registerObserver(b);
        }
        auction.start(product);
    }

    public double giveMaxBet(Map<Double, Client> betsList) {
        Map.Entry<Double, Client> entry = betsList.entrySet().iterator().next();
        return entry.getKey();
    }

    public void stopAuction(Auction auction, Client winner, double sellPrice) {
        auction.setWinner(winner);
        auction.setSellPrice(sellPrice);
        int productId = auction.getProductId();
        try {
            Product p = findProductById(productId);
            p.setSellPrice(sellPrice);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
        auction.notifyObservers();
        System.out.println("winner is:" + winner.getName() + " for price:" + sellPrice);
    }

    public Product findProductById(int productId) throws ProductNotFoundException {
        for(Product p : productList) {
            if (p.getId() == productId)
                return p;
        }
        throw new ProductNotFoundException();
    }

    public Administrator findAdministratorById(int adminId) {
        for(Administrator a : administrators) {
            if(a.getEmployeeId() == adminId) {
                return a;
            }
        }
        return null;
    }

    public List<Broker> getBrokers() { return brokers; }

    public void setBrokers(List<Broker> brokers) { this.brokers = brokers; }

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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<Administrator> administrators) {
        this.administrators = administrators;
    }

}
