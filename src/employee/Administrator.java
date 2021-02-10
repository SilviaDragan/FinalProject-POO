package employee;

import auction.AuctionHouse;
import product.Product;

/**
 * Administrators add products to the list of products in the auction house
 */
public class Administrator extends Employee implements Runnable{
    private final AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
    private Product currentProduct;

    public Administrator(int employeeId) {
        super(employeeId);
    }

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

