package command;

import auction.Auction;
import auction.AuctionHouse;
import employee.Administrator;
import employee.Employee;
import product.Furniture;
import product.Jewelery;
import product.Painting;
import product.Product;

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
            Jewelery jewelery = new Jewelery(Integer.parseInt(command[3]), command[4],
                    Double.parseDouble(command[5]), Integer.parseInt(command[6]));
            jewelery.setMetal(command[7]);
            jewelery.setIfPrecious(command[8]);
            product = jewelery;
        }
        else if (command[2].equals("Painting")) {
            Painting painting = new Painting(Integer.parseInt(command[3]), command[4],
                    Double.parseDouble(command[5]), Integer.parseInt(command[6]));
            painting.setPainterName(command[7]);
            painting.setColorType(command[8]);
            product = painting;
        }
        else {
            Furniture furniture = new Furniture(Integer.parseInt(command[3]), command[4],
                    Double.parseDouble(command[5]), Integer.parseInt(command[6]));
            furniture.setType(command[7]);
            furniture.setType(command[8]);
            product = furniture;
        }
        administrator.setCurrentProduct(product);
        threadPool.execute(administrator);

    }

}
