package product;

import auction.Auction;
import client.Client;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id; // number that identifies the product
    private String name; // the name of the product
    private double sellPrice; // the price which the product sold for after the auction
    private double minimumPrice; // the minimum price
    private int year;
    private Auction auction;
    private List<Client> clientsCompeting = new ArrayList<>();
    private List<Double> maxSumPerCLient = new ArrayList<>();

    public Product(int id, String name, double minimumPrice, int year) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.year = year;
    }


    // metodele din subclase le definesti aici abstracte
    // sublasele trebuie sa implementeze toate metodele din clasa asta
    // vedem ce facem builder
    // constructorul ar trebui sa fie protected


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public double getSellPrice() { return sellPrice; }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public List<Client> getClientsCompeting() {
        return clientsCompeting;
    }

    public void setClientsCompeting(List<Client> clientsCompeting) {
        this.clientsCompeting = clientsCompeting;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public List<Double> getMaxSumPerCLient() {
        return maxSumPerCLient;
    }

    public void setMaxSumPerCLient(List<Double> maxSumPerCLient) {
        this.maxSumPerCLient = maxSumPerCLient;
    }
}
