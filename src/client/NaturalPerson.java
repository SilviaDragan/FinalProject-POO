package client;

public class NaturalPerson extends Client{
    private String birthDate;

    public NaturalPerson(int id, String name, String address, int participationNo, int wonAuctions) {
        super(id, name, address, participationNo, wonAuctions);
    }

    @Override
    public void placeBet(int auctionId, double maxPreviousSum, double maxSumForMe) {
        // client calculates the sum he is willing to bet for the product at every
        // step of the auction, and informs the broker about it.

        // the client needs to bet a larger sum than the max sum from the step before
        // but if the sum is bigger than the max sum he is willing to bet...
        if(maxPreviousSum > maxSumForMe) {
//            System.out.println(this.getName() + " nextBet:" + maxSumForMe);
            getPersonalBroker().clientPlacedBet(auctionId, maxSumForMe);
        }
        else {
            // the sum a natural person will bet is calculated by subtracting from the maximum sum
            // this client is willing to bet the maximum bet at previous step
            // then divide that by 4
            // add it to max bet at previous step
            double nextBet = maxPreviousSum + (maxSumForMe - maxPreviousSum) / 4;
//            System.out.println(this.getName() + " nextBet:" + nextBet);
            getPersonalBroker().clientPlacedBet(auctionId, nextBet);
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

    @Override
    public void update() {

    }
}
