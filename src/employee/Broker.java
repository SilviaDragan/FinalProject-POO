package employee;

import auction.Auction;
import auction.AuctionHouse;
import client.Client;
import product.Product;

import java.util.List;

public class Broker extends Employee implements Runnable{
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private List<Client> clients;
    private Product soldProduct;

    public Broker(int employeeId) {
        super(employeeId);
    }


    @Override
    public void run() {
        auctionHouse.removeProduct(soldProduct);
    }

    public void clientPlacedBet(int auctionId, double sum) {
        Auction auction = findAuction(auctionId);
        if (auction != null) {
            auction.getBetsList().add(sum);
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


    public Product getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(Product soldProduct) {
        this.soldProduct = soldProduct;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }


}
