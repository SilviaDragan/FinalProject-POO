package employee;

import auction.Auction;
import auction.AuctionHouse;
import auction.Observer;
import client.Client;
import product.Product;
import product.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class Broker extends Employee implements Runnable, Observer {
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private final Map<Auction, Map<Client, Double>> clientsMap = new HashMap<>();
    private Product soldProduct;
    private Auction currentAuction = null;
    private Executor threadPool;
    private double income;

    public Broker(int employeeId) {
        super(employeeId);
    }

    @Override
    public void update(Client winner, double sellPrice) {
        if(     winner != null
                && this.clientsMap.containsKey(this.currentAuction)
                && this.clientsMap.get(this.currentAuction).containsKey(winner) ) {
            winner.wonAuction();
            try {
                this.soldProduct = auctionHouse.findProductById(this.currentAuction.getProductId());
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            }
            threadPool.execute(this);
        }
        if(clientsMap.get(currentAuction) != null) {
            for (Map.Entry<Client, Double> clientDoubleEntry : clientsMap.get(currentAuction).entrySet()) {
                Client client = ( clientDoubleEntry).getKey();
                double transaction = clientDoubleEntry.getValue();
                this.income += client.payCommission(transaction);
                client.setPersonalBroker(null);
            }
        }
        this.currentAuction = null;

    }

    @Override
    public void run() {
        auctionHouse.removeProduct(soldProduct);
    }

    public void askNextBet(Auction auction, double maxBetPerStep) {
        for (Client c : clientsMap.get(auction).keySet()) {
            c.placeBet(auction.getId(), maxBetPerStep, clientsMap.get(auction).get(c));
        }
    }

    public void clientPlacedBet(Client client, int auctionId, double sum) {
        Auction auction = findAuction(auctionId);
        if (auction != null) {
            auction.getBetsMap().put(sum, client);
        }
    }

    private Auction findAuction(int auctionId) {
        for(Auction a : auctionHouse.getAuctionList()) {
            if (a.getId() == auctionId) {
                return a;
            }
        }
        return null;
    }

    public Map<Auction, Map<Client, Double>> getClientsMap() {
        return clientsMap;
    }

    public void setSoldProduct(Product soldProduct) {
        this.soldProduct = soldProduct;
    }

    public void setCurrentAuction(Auction currentAuction) {
        this.currentAuction = currentAuction;
    }

    public void setThreadPool(Executor threadPool) {
        this.threadPool = threadPool;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
