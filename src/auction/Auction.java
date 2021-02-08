package auction;

import client.Client;
import employee.Broker;
import product.Product;
import java.util.*;

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

    public Auction(int id, int productId) {
        this.id = id;
        this.productId = productId;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) { }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(winner, sellPrice);
        }
    }

    public void start(Product product) {
        for (int step = 0; step < maxStepsNo; step++) {
            // clients choose the sum they desire to bet at each step and communicate this sum to their assigned broker
            for (Broker broker : auctionHouse.getBrokers()) {
                if (broker.getClientsMap().containsKey(this)) {
                    broker.askNextBet(this, this.maxBetPerStep);
                }
            }
            // after clients place their bets, the house will calculate the max bet
            this.maxBetPerStep = auctionHouse.giveMaxBet(betsMap);
//            System.out.println("step:" +step + " max bet:" + maxBetPerStep);

            // clear the bets list at every step, unless it is the last round
            if(step != this.maxStepsNo - 1) this.betsMap.clear();
        }
        // if product does not sell, notify clients through brokers that the auction is done
        if (this.maxBetPerStep < product.getMinimumPrice()) {
            // product does not sell in this case
            this.winner = null;
            this.sellPrice = 0;
            notifyObservers();
        }
        // get winner, notify all clients through brokers
        auctionHouse.stopAuction(this, getWinner(this.maxBetPerStep, (TreeMap<Double, Client>) this.betsMap),
                this.maxBetPerStep);

    }

    private Client getWinner(double winnerBet, TreeMap<Double, Client> betsList) {
        Client finalWinner = null;
        NavigableMap<Double, Client> winnersMap= betsList.headMap(winnerBet, true);
        List<Client> winners = new ArrayList<>(winnersMap.values());
        if(winners.size() == 1) finalWinner =  winners.get(0);
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
