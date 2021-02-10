package auction;

import client.Client;
import employee.Broker;
import product.Product;
import java.util.*;

/**
 * The Auction class is a Subject observed by brokers
 */
public class Auction implements Subject{
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private int id;
    private int participantsNo;
    private int productId;
    private int maxStepsNo;
    private final List<Observer> observers = new ArrayList<>();
    private double maxBetPerStep = 0;
    private final Map<Double, Client> betsMap = new TreeMap<>(Collections.reverseOrder());
    private Client winner;
    private double sellPrice;

    /**
     * @param id auction's id
     * @param productId product's id
     */
    public Auction(int id, int productId) {
        this.id = id;
        this.productId = productId;
    }

    /**
     * @param o the observer
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     * @param o the observer
     */
    @Override
    public void removeObserver(Observer o) { }

    @Override
    public void notifyObservers() throws InterruptedException {
        for (Observer observer : observers) {
            observer.update(winner, sellPrice);
        }
    }

    /**
     * @param product auction for this product
     * @throws InterruptedException if a thread is interrupted
     */
    public void start(Product product) throws InterruptedException {
        for (int step = 0; step < maxStepsNo; step++) {
            // clients choose the sum they desire to bet at each step and communicate this sum to their assigned broker
            for (Broker broker : auctionHouse.getBrokers()) {
                if (broker.getClientsMap().containsKey(this)) {
                    broker.askNextBet(this, this.maxBetPerStep);
                }
            }
            // after clients place their bets, the house will calculate the max bet
            this.maxBetPerStep = auctionHouse.giveMaxBet(betsMap);
            // clear the bets list at every step, unless it is the last round
            if(step != this.maxStepsNo - 1) this.betsMap.clear();
        }
        // if product does not sell, notify clients through brokers that the auction is done
        if (this.maxBetPerStep < product.getMinimumPrice()) {
            // product does not sell in this case
            this.winner = null;
            this.sellPrice = 0;
            auctionHouse.stopAuction(this, this.winner, this.sellPrice);
            notifyObservers();
        }
        else {
            // get winner, notify all clients through brokers
            auctionHouse.stopAuction(this, getWinner(this.maxBetPerStep, (TreeMap<Double, Client>) this.betsMap),
                    this.maxBetPerStep);
        }

    }

    /**
     * @param winnerBet the bet that the winning client gave
     * @param betsList a map of clients and their bets
     * @return the client that won
     */
    private Client getWinner(double winnerBet, TreeMap<Double, Client> betsList) {
        Client finalWinner = null;
        // get the clients that gave the same maximum bet
        NavigableMap<Double, Client> winnersMap= betsList.headMap(winnerBet, true);
        // a list of potential winners
        List<Client> winners = new ArrayList<>(winnersMap.values());
        if(winners.size() == 1) finalWinner =  winners.get(0);
        // determine the winner by the max number of participation
        else {
            int maxWins = 0;
            for (Client c : winners) {
                if(c.getWonAuctionsNo() >= maxWins) {
                    finalWinner = c;
                }
            }
        }
        return finalWinner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipantsNo() { return participantsNo; }

    public void setParticipantsNo(int participantsNo) {
        this.participantsNo = participantsNo;
    }

    public Map<Double, Client> getBetsMap() {
        return betsMap;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setMaxStepsNo(int maxStepsNo) {
        this.maxStepsNo = maxStepsNo;
    }

    public void setWinner(Client winner) {
        this.winner = winner;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

}
