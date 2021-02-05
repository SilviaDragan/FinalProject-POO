package command;

import auction.Auction;
import auction.AuctionHouse;
import employee.Administrator;
import employee.Employee;
import product.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddProduct implements Command{
    private Administrator administrator;
    private String[] command;
    private Executor threadPool;
    public AddProduct(Administrator administrator, String[] command, Executor executor) {
        this.administrator = administrator;
        this.command = command;
        this.threadPool = executor;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        Administrator administrator = auctionHouse.getAdministrator(Integer.parseInt(command[1]));
        Product product;
        if ( command[2].equals("Jewelery")) {
            JeweleryBuilder builder = new JeweleryBuilder()
                    .withId(Integer.parseInt(command[3]))
                    .withName(command[4])
                    .withMinimumPrice(Double.parseDouble(command[5]))
                    .withYear(Integer.parseInt(command[6]))
                    .withMetal(command[7])
                    .withPrecious(command[8]);
            product = builder.build();
        }
        else if (command[2].equals("Painting")) {
            PaintingBuilder builder = new PaintingBuilder()
                    .withId(Integer.parseInt(command[3]))
                    .withName(command[4])
                    .withMinimumPrice(Double.parseDouble(command[5]))
                    .withYear(Integer.parseInt(command[6]))
                    .withPainter(command[7])
                    .withColor(command[8]);
            product = builder.build();
        }
        else {
            FurnitureBuilder builder = new FurnitureBuilder()
                    .withId(Integer.parseInt(command[3]))
                    .withName(command[4])
                    .withMinimumPrice(Double.parseDouble(command[5]))
                    .withYear(Integer.parseInt(command[6]))
                    .withType(command[7])
                    .withMaterial(command[8]);
            product = builder.build();
        }
        administrator.setCurrentProduct(product);
        threadPool.execute(administrator);

    }

}
