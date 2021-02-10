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


/**
 * This class is implemented as a singleton
 * It starts and end the auctions
 */
public class AuctionHouse {
    private static AuctionHouse instance = null;
    private List<Product> productList = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private final List<Auction> auctionList = new ArrayList<>();
    private List<Broker> brokers = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();
    private int capacity; // capacity for product list
    Lock lock = new ReentrantLock();
    Condition notVid = lock.newCondition();
    Condition notFull = lock.newCondition();
    private Random rand;
    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private AuctionHouse() {}

    /**
     * @return the unique instance of the auction house
     */
    public static AuctionHouse auctionHouseInstance() {
        // if a class instance exists, it is returned
        // only create a new instance if it does not exist already
        if (instance == null)
            instance = new AuctionHouse();
        return instance;
    }


    /**
     * @param productNew the product to be added
     */
    // only administrators can do this
    public void addProduct(Product productNew) {
        lock.lock();
        try {
            while(productList.size() == capacity) {
                notFull.await();
            }
            System.out.println("Admin added product: " + productNew);
            int auctionID = productNew.getId() + 1000;
            Auction auction = new Auction(auctionID, productNew.getId());
            productNew.setAuction(auction);
            productList.add(productNew);
            auctionList.add(auction);
            notVid.signal();
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * @param soldProduct the product to be removed
     */
    // only brokers can do this
    public void removeProduct(Product soldProduct) {
        lock.lock();
        try {
            while (productList.isEmpty()) {
                notVid.await();
            }
            System.out.println("Broker removed product: " + soldProduct);
            productList.remove(soldProduct);
            notFull.signal();
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }finally {
            lock.unlock();
        }
    }

    /**
     * @return a random broker
     */
    public Broker assignBroker() {
        return brokers.get(rand.nextInt(brokers.size()));
    }

    /**
     * @param requestedProduct the product that the client wants
     * @param client the client that made the request
     * @param maxSum the maximum price the client is willing to pay for the product
     */
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

    /**
     * @param auction the auction to be started
     * @param product the product of the auction
     */
    private void startAuction(Auction auction, Product product) {
        // register all brokers as observers for this auction
        for (Broker b : brokers) {
            b.setCurrentAuction(auction);
            b.setSoldProduct(product);
            auction.registerObserver(b);
        }
        try {
            auction.start(product);
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * @param betsList the map of bets and clients
     * @return the maximum bet
     */
    public double giveMaxBet(Map<Double, Client> betsList) {
        Map.Entry<Double, Client> entry = betsList.entrySet().iterator().next();
        return entry.getKey();
    }

    /**
     * @param auction the auction to be stopped
     * @param winner the winner of the auction
     * @param sellPrice the price the product sold for
     */
    public void stopAuction(Auction auction, Client winner, double sellPrice) {
        // set the winner and price in auction
        if(winner != null) {
            auction.setWinner(winner);
            auction.setSellPrice(sellPrice);
            int productId = auction.getProductId();
            Product p = null;
            try {
                p = findProductById(productId);
                p.setSellPrice(sellPrice);
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            }
            displayWinner(winner, sellPrice, p);
            //notify all observers of the change of state of the auction
            try {
                auction.notifyObservers();
            } catch (InterruptedException e) {
                // Restore interrupted state
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Product did not sell");
        }

    }

    private void displayWinner(Client winner, double sellPrice, Product p) {
        String decorator = "~";
        String space = " ";
        System.out.println(decorator.repeat(35) + "winner" + decorator.repeat(35));
        System.out.println(space.repeat(30) +"for product "+ p.getName()+ space.repeat(30));
        System.out.println(space.repeat(35) + winner.getName() + space.repeat(35));
        System.out.println(space.repeat(25) + "for price:" + sellPrice + space.repeat(25));
        System.out.println(decorator.repeat(80));
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
