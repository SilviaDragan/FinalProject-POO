package client;

public class LegalPerson extends Client{
    private CompanyType company;
    private double socialCapital;

    public LegalPerson(int id, String name, String address, int participationNo, int wonAuctionsNo) {
        super(id, name, address, participationNo, wonAuctionsNo);
    }

    @Override
    public void placeBet(int auctionId, double maxPreviousSum, double maxSumForMe) {
        // client calculates the sum he is willing to bet for the product at every
        // step of the auction, and informs the broker about it.

        // the client needs to bet a larger sum than the max sum from the step before
        // but if the sum is bigger than the max sum he is willing to bet...
        if(maxPreviousSum > maxSumForMe) {
            System.out.println(this.getName() + " nextBet:" + maxSumForMe);
            getPersonalBroker().clientPlacedBet(auctionId, maxSumForMe);
        }
        else {
            // the sum a natural person will bet is calculated by subtracting from the maximum sum
            // this client is willing to bet the maximum bet at previous step
            // then divide that by 3
            // add it to max bet at previous step
            double nextBet = maxPreviousSum + (maxSumForMe - maxPreviousSum) / 3;
//            System.out.println(this.getName() + " nextBet:" + nextBet);
            getPersonalBroker().clientPlacedBet(auctionId, nextBet);
        }
    }

    public CompanyType getCompany() {
        return company;
    }

    public void setCompanyType(String companyType) {
        if(companyType.equals("SRL")){
            this.company = CompanyType.SRL;
        }
        else {
            this.company = CompanyType.SA;
        }
    }

    public double getSocialCapital() {
        return socialCapital;
    }

    public void setSocialCapital(double socialCapital) {
        this.socialCapital = socialCapital;
    }


    public String toString() {
        String mystr = "";
        int id = this.getId();
        int tries = this.getParticipationNo();
        int wins = this.getWonAuctionsNo();
        mystr += "LegalPerson Id:" + id + " Name:" + this.getName() + " Address:" + this.getAddress() +
                " NumberOfParticipation:" + tries + " WonAuctions:" + wins + " CompanyType:" + getCompany()
                + " SocialCapital:" + getSocialCapital();
        return mystr;
    }

}
