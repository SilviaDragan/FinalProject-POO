package auction;

import client.Client;
import employee.Administrator;
import employee.Broker;
import employee.Employee;
import product.Product;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//singleton
public class AuctionHouse {
    private static AuctionHouse instance = null;
    private List<Product> productList = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private List<Auction> auctionList = new ArrayList<>();
    private List<Broker> brokers = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();
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
            Employee employee = brokers.get(random.nextInt(brokers.size()));
            if ( employee instanceof Broker) {
                broker = (Broker) employee;
                break;
            }
        }
        return broker;
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
        auction.start(product);
    }


    public double giveMaxBet(Map<Double, Client> betsList) {
        Map.Entry<Double, Client> entry = betsList.entrySet().iterator().next();
        double maxBet = entry.getKey();
        return maxBet;
    }

    public void winner(Auction auction, Client winner, double sellPrice) {
        // winner for auction is found
        auction.setState(1);
        auction.notifyObservers();
        winner.wonAuction();
    }

    public Administrator getAdministrator(int adminId) {
        for(Employee e : brokers) {
            if(e.getEmployeeId() == adminId) {
                return (Administrator) e;
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

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public int getCapacity() {
        return capacity;
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
