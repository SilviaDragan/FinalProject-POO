package client;

import employee.Broker;

public class Client {
    private int id;
    private String name;
    private String address;
    private int participationNo;
    private int wonAuctionsNo;
    private Broker personalBroker;


    public Client(int id, String name, String address, int participationNo, int wonAuctionsNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.participationNo = participationNo;
        this.wonAuctionsNo = wonAuctionsNo;
    }

    public Broker getPersonalBroker() {
        return personalBroker;
    }

    public void setPersonalBroker(Broker personalBroker) {
        this.personalBroker = personalBroker;
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
