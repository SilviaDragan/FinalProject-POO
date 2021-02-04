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
    private List<Observer> observers = new ArrayList<>();
    private double maxBetPerStep = 0;
    private Map<Double, Client> betsMap = new TreeMap<>(Collections.reverseOrder());
    private int state ; // 0 = started auction, 1 = done, 2 = product not sold
    private Client winner;

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
            observer.update(state);
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public void start(Product product) {
        this.state = 0;
        notifyObservers();
        for (int step = 0; step < maxStepsNo; step++) {
            // clients choose the sum they desire to bet at each step and communicate this sum to their assigned broker
            for (Broker broker : auctionHouse.getBrokers()) {
                if (broker.getClientsMap().containsKey(this)) {
                    broker.askNextBet(this, maxBetPerStep);
                }
            }
            // after clients place their bets, the house will calculate the max bet
            maxBetPerStep = auctionHouse.giveMaxBet(betsMap);
            System.out.println("step:" +step + " max bet:" + maxBetPerStep);

            // clear the bets list at every step, unless it is the last round
            if(step != maxStepsNo - 1) betsMap.clear();
        }
        // if product does not sell, notify clients through brokers that the auction is done
        if (maxBetPerStep < product.getMinimumPrice()) {
            // product does not sell in this case
            this.state = 2;
            notifyObservers();
        }
        // get winner, notify all clients through brokers
        auctionHouse.winner(this, getWinner(maxBetPerStep, (TreeMap<Double, Client>) betsMap), maxBetPerStep);
        Client winner = getWinner(maxBetPerStep, (TreeMap<Double, Client>) betsMap);
        notifyObservers();
//        product.setSellPrice(maxBetPerStep);
        System.out.println("winner is:" + winner.getName());
    }

    private Client getWinner(double winnerBet, TreeMap<Double, Client> betsList) {
        List<Client> winners = new ArrayList<>();
        Client finalWinner = null;
        NavigableMap<Double, Client> winnersMap= betsList.headMap(winnerBet, true);
        for(Client c : winnersMap.values()) {
//            System.out.println("winner " + c.getName());
            winners.add(c);
        }
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

    public List<Observer> getObservers() { return observers; }

    public void setParticipantsNo(int participantsNo) {
        this.participantsNo = participantsNo;
    }


    public Map<Double, Client> getBetsMap() {
        return betsMap;
    }

    public void setBetsMap(Map<Double, Client> betsMap) {
        this.betsMap = betsMap;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMaxStepsNo() {
        return maxStepsNo;
    }

    public void setMaxStepsNo(int maxStepsNo) {
        this.maxStepsNo = maxStepsNo;
    }

    public void setWinner(Client winner) {
        this.winner = winner;
    }


}
