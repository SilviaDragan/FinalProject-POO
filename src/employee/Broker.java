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

/**
 * Brokers can delete products after they are sold
 * Brokers are also observers of the Auction
 */
public class Broker extends Employee implements Runnable, Observer {
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private final Map<Auction, Map<Client, Double>> clientsMap = new HashMap<>();
    private Product soldProduct;
    private Auction currentAuction = null;
    private Executor threadPool;
    private double income;

    /**
     * @param employeeId the id of the employee
     */
    public Broker(int employeeId) {
        super(employeeId);
    }

    /**
     * @param winner the winner of the auction
     * @param sellPrice the price the product sold for
     * @throws InterruptedException thrown if a thread is interrupted
     */
    @Override
    public void update(Client winner, double sellPrice) throws InterruptedException {
        // when an auction's state is changed, it means it has ended
        // the product is either sold or not
        // if the products is sold, and the broker is in charge of the winning client
        // the broker must inform the client and remove the sold prduct
        if(     winner != null
                && this.clientsMap.containsKey(this.currentAuction)
                && this.clientsMap.get(this.currentAuction).containsKey(winner) ) {
            winner.wonAuction();
            try {
                this.soldProduct = auctionHouse.findProductById(this.currentAuction.getProductId());
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            }
            // remove the product
            threadPool.execute(this);
            Thread.sleep(1000);
        }
        // get commission from client
        if(clientsMap.get(currentAuction) != null) {
            for (Map.Entry<Client, Double> clientDoubleEntry : clientsMap.get(currentAuction).entrySet()) {
                Client client = ( clientDoubleEntry).getKey();
                double transaction = clientDoubleEntry.getValue();
                this.income += client.payCommission(transaction);
                client.setPersonalBroker(null);
            }
        }
        // the broker is no longer an observer of this auction
        if(currentAuction != null) currentAuction.removeObserver(this);
        this.currentAuction = null;
    }

    @Override
    public void run() {
        auctionHouse.removeProduct(soldProduct);
    }

    /**
     * @param auction the auction
     * @param maxBetPerStep the previous maximum bet
     */
    public void askNextBet(Auction auction, double maxBetPerStep) {
        // ask each client for this auction what is the next bet for them
        for (Client c : clientsMap.get(auction).keySet()) {
            c.placeBet(auction.getId(), maxBetPerStep, clientsMap.get(auction).get(c));
        }
    }

    /**
     * @param client the client that place the bet
     * @param auctionId the auction
     * @param sum the sum the client want to bet for the next step
     */
    public void clientPlacedBet(Client client, int auctionId, double sum) {
        // when a client informs the broker what bet he wants to give for the next step,
        // the broker puts the bet in the bets map
        Auction auction = findAuction(auctionId);
        if (auction != null) {
            auction.getBetsMap().put(sum, client);
        }
    }

    /**
     * @param auctionId the id of the auction
     * @return the auction with this id
     */
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
