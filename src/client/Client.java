package client;

import employee.Broker;

/**
 * Classes NaturalPerson and LegalPerson implement the abstract method of this class
 */
public abstract class Client{
    private int id;
    private String name;
    private String address;
    private int participationNo;
    private int wonAuctionsNo;
    private Broker personalBroker;

    /**
     * @param id client's id
     * @param name client's name
     * @param address client's address
     * @param participationNo client's no of times he took part in an auction
     * @param wonAuctionsNo client's no of times won an auction
     */
    protected Client(int id, String name, String address, int participationNo, int wonAuctionsNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.participationNo = participationNo;
        this.wonAuctionsNo = wonAuctionsNo;
    }

    public abstract double payCommission(double transaction);

    public abstract void placeBet(int auctionId, double maxPreviousSum, double maxSumForMe);

    public Broker getPersonalBroker() {
        return personalBroker;
    }

    public void setPersonalBroker(Broker personalBroker) {
        this.personalBroker = personalBroker;
    }

    public void wonAuction() {
        this.wonAuctionsNo ++;
    }

    public void newParticipation() {
        this.participationNo ++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getParticipationNo() {
        return participationNo;
    }

    public void setParticipationNo(int participationNo) {
        this.participationNo = participationNo;
    }

    public int getWonAuctionsNo() {
        return wonAuctionsNo;
    }

    public void setWonAuctionsNo(int wonAuctionsNo) {
        this.wonAuctionsNo = wonAuctionsNo;
    }

}
