package employee;

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


//    public Broker(Product product) {
//        this.soldProduct = product;
//    }

    @Override
    public void run() {
        auctionHouse.removeProduct(soldProduct);
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
