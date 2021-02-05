package product;

import auction.Auction;

public abstract class Product {
    private int id; // number that identifies the product
    private String name; // the name of the product
    private double sellPrice; // the price which the product sold for after the auction
    private double minimumPrice; // the minimum price
    private int year;
    private Auction auction;

    protected Product(int id, String name, double minimumPrice, int year) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.year = year;
    }

    protected Product() {}

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

}
