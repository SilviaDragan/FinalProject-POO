package auction;

import client.Client;
import product.Product;
import java.util.ArrayList;
import java.util.List;

// comment for yourself:
// try to implement observer pattern here

public class Auction {
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private int id;
    private int participantsNo;
    private int productId;
    private int maxStepsNo;
    private List<Double> betsList = new ArrayList<>();

    public Auction(int id, int productId) {
        this.id = id;
        this.productId = productId;
    }
    public void start(Product product) {
        double maxBetPerStep = 0;
        for (int step = 0; step < maxStepsNo; step++) {
            // clients choose the sum they desire to bet at each step and communicate this sum to their assigned broker
            for( int i  = 0; i < participantsNo; i++) {
                product.getClientsCompeting().get(i)
                        .placeBet(id, maxBetPerStep, product.getMaxSumPerClient().get(i) );
            }
            // after clients place their bets, the house will calculate the max bet
            maxBetPerStep = auctionHouse.giveMaxBet(betsList);
            System.out.println("step:" +step + " max bet:" + maxBetPerStep);
            // clear the bets list at every step, unless it is the last round
            if ( step == maxStepsNo - 1) {
                if(maxBetPerStep < product.getMinimumPrice()) return; // product does not sell in this case
                // get winner
                Client winner = getWinner(maxBetPerStep, betsList, product);
                product.setSellPrice(maxBetPerStep);
                System.out.println("winner is:" + winner.getName());
            }
            else {
                betsList.clear();
            }
        }
    }

    private Client getWinner(double winnerBet, List<Double> betsList, Product product) {
        List<Client> winners = new ArrayList<>();
        Client finalWinner = null;
        for ( int i = 0; i < betsList.size() - 1; i++) {
            if (betsList.get(i) == winnerBet) {
                winners.add(product.getClientsCompeting().get(i));
            }
        }
        if(winners.size() == 1) finalWinner =  winners.get(0);
        else {
            int maxWins = 0;
            for (Client c : product.getClientsCompeting()) {
                if(c.getWonAuctionsNo() > maxWins) {
                    finalWinner = c;
                }
            }

        }
        return finalWinner;
    }

    public List<Double> getBetsList() {
        return betsList;
    }

    public void setBetsList(List<Double> betsList) {
        this.betsList = betsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipantsNo() {
        return participantsNo;
    }

    public void setParticipantsNo(int participantsNo) {
        this.participantsNo = participantsNo;
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


}
