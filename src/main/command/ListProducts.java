package main.command;

import auction.AuctionHouse;
import product.Product;

import java.util.List;

public class ListProducts implements Command{
    @Override
    public void execute() {
        AuctionHouse house = AuctionHouse.auctionHouseInstance();
        List<Product> products = house.getProductList();
        printProducts(products);
    }

    private void printProducts(List<Product> products) {
        String str = "-";
        System.out.println(str.repeat(30) + "Products available" + str.repeat(30));
        products.forEach(System.out::println);
        System.out.println(str.repeat(80));
    }
}
