package employee;

import auction.AuctionHouse;
import product.Product;

public class Administrator extends Employee implements Runnable{
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private Product currentProduct;

    public Administrator(int employeeId) {
        super(employeeId);
    }

    //    public Administrator(Product product) {
//        this.currentProduct = product;
//    }
    @Override
    public void run() {
        auctionHouse.addProduct(currentProduct);
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }



}

