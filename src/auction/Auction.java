package auction;

import client.Client;
import product.Product;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private int id;
    private int participantsNo;
    private int productId;
    private int maxStepsNo;

    public Auction(int id, int productId) {
        this.id = id;
        this.productId = productId;
    }
    public void start(Product product) {
        double SumForCurrentStep = 0;
        for (int step = 0; step <= maxStepsNo; step++) {
            // for each step, the sum that each clients bet will be stored in this list
            // NOOOOOOO so the client will bet and THE BROKER will add the sum to the list !!!!!!!!
            // client can not communicate with the house directly


            // idk how to do this come back tomorrow and
            // DO THIS!!!! FIGURE IT OUT !!!!!
            List<Double> sumsList = new ArrayList<>();
            // clients choose the sum they desire to bet at each step and communicate
            // this sum to their assigned broker
            for (Client client : product.getClientsCompeting()) {
                client.informBrokerBet(SumForCurrentStep);
            }
        }
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
