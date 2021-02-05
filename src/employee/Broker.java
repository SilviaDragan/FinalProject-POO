package employee;

import auction.Auction;
import auction.AuctionHouse;
import auction.Observer;
import client.Client;
import product.Product;
import java.util.HashMap;
import java.util.Map;

public class Broker extends Employee implements Runnable, Observer {
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private Map<Auction, Map<Client, Double>> clientsMap = new HashMap<>();
    private Product soldProduct;

    public Broker(int employeeId) {
        super(employeeId);
    }

    @Override
    public void update(int state) {
        // all brokers are observers
        // idk
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

    public void setClientsMap(Map<Auction, Map<Client, Double>> clientsMap) {
        this.clientsMap = clientsMap;
    }

    public Product getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(Product soldProduct) {
        this.soldProduct = soldProduct;
    }


}
