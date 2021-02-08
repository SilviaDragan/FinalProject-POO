package command;

import auction.AuctionHouse;
import client.Client;
import client.ClientNotFoundException;
import product.Product;
import product.ProductNotFoundException;

public class ProductRequest implements Command{
    private final int clientId;
    private final int productId;
    private final double maxSum;

    public ProductRequest(int clientId, int productId, double maxSum) {
        this.clientId = clientId;
        this.productId = productId;
        this.maxSum = maxSum;
    }

    public Client findClient(AuctionHouse auctionHouse, int id) throws ClientNotFoundException {
        //find the client in the client list
        for(Client c: auctionHouse.getClientList()) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new ClientNotFoundException();
    }

    public Product findProduct(AuctionHouse auctionHouse, int id) throws ProductNotFoundException {
        //find the product in the product list
        for(Product p : auctionHouse.getProductList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductNotFoundException();
    }

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        //find the client and the product in their lists
        try {
            Client client = findClient(auctionHouse, clientId);
            Product product = findProduct(auctionHouse, productId);
            auctionHouse.processRequest(product, client, maxSum);
        } catch (ClientNotFoundException | ProductNotFoundException e) {
            e.printStackTrace();
        }
    }
}
