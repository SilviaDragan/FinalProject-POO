package main.command;

public class AuctionNotFoundException extends Exception {
    public AuctionNotFoundException() {super("Auction not found");}
}
