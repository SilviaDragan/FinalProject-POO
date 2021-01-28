package command;

import auction.Auction;
import auction.AuctionHouse;
import client.Client;
import product.Product;

public class ProductRequest implements Command{
    private int clientId;
    private int productId;
    private double maxPrice;

    public ProductRequest(int clientId, int productId, double maxPrice) {
        this.clientId = clientId;
        this.productId = productId;
        this.maxPrice = maxPrice;
    }

    public Client findClient(AuctionHouse auctionHouse, int id) {
        //find the client in the client list
        for(Client c: auctionHouse.getClientList()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Product findProduct(AuctionHouse auctionHouse, int id) {
        //find the product in the product list
        for(Product p : auctionHouse.getProductList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        //find the client and the product in their lists
        Client client = findClient(auctionHouse, clientId);
        Product product = findProduct(auctionHouse, productId);
        auctionHouse.processRequest(product, client);
    }
}
