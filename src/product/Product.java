package product;

import java.util.List;

public class Product {
    private int id; // number that identifies the product
    private String name; // the name of the product
    private double sellPrice; // the price which the product sold for after the auction
    private double minimumPrice; // the minimum price
    private int year;

    public Product(int id, String name, double minimumPrice, int year) {
        this.id = id;
        this.name = name;
//        this.sellPrice = sellPrice;
        this.minimumPrice = minimumPrice;
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

}
