package client;

/**
 *  * Implements the methods in Client
 */
public class NaturalPerson extends Client{
    private String birthDate;

    /**
     * @param id client's id
     * @param name client's name
     * @param address client's address
     * @param participationNo client's no of times he took part in an auction
     * @param wonAuctions client's no of times won an auction
     */
    public NaturalPerson(int id, String name, String address, int participationNo, int wonAuctions) {
        super(id, name, address, participationNo, wonAuctions);
    }

    /**
     * @param transaction the sum the clients initially offered for the product
     * @return the sum that has to be paid to the broker
     */
    @Override
    public double payCommission(double transaction) {
        double commission;
        if(this.getParticipationNo() < 5) {
            commission = (20 * transaction)/100;
        }
        else {
            commission = (15 * transaction)/100;
        }
        return commission;
    }

    /**
     * @param auctionId the auction id
     * @param maxPreviousSum the max bet at the previous step
     * @param maxSumForMe the max sum the client is willing to pay
     */
    @Override
    public void placeBet(int auctionId, double maxPreviousSum, double maxSumForMe) {
        // client calculates the sum he is willing to bet for the product at every
        // step of the auction, and informs the broker about it.

        // the client needs to bet a larger sum than the max sum from the step before
        // but if the sum is bigger than the max sum he is willing to bet...
        if(maxPreviousSum > maxSumForMe) {
            getPersonalBroker().clientPlacedBet(this, auctionId, maxSumForMe);
        }
        else {
            // the sum a natural person will bet is calculated by subtracting from the maximum sum
            // this client is willing to bet the maximum bet at previous step
            // then divide that by 4
            // add it to max bet at previous step
            double nextBet = maxPreviousSum + (maxSumForMe - maxPreviousSum) / 4;
            getPersonalBroker().clientPlacedBet(this, auctionId, nextBet);
        }
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String toString() {
        String mystr = "";
        int id = this.getId();
        int tries = this.getParticipationNo();
        int wins = this.getWonAuctionsNo();
        mystr += "NaturalPerson Id:" + id + " Name:" + this.getName() + " Address:" + this.getAddress()
                + " NumberOfParticipation:" + tries + " WonAuctions:" + wins + " Birthdate:" + getBirthDate();
        return mystr;
    }


}
