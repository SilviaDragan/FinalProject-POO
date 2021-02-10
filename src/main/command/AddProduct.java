package main.command;

import employee.Administrator;
import product.*;
import product.furniture.FurnitureBuilder;
import product.jewelery.JeweleryBuilder;
import product.painting.PaintingBuilder;

import java.util.concurrent.Executor;

public class AddProduct implements Command {
    private final Administrator administrator;
    private final String[] command;
    private final Executor threadPool;

    public AddProduct(Administrator administrator, String[] command, Executor executor) {
        this.administrator = administrator;
        this.command = command;
        this.threadPool = executor;
    }

    @Override
    public void execute() throws InterruptedException {
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
        Thread.sleep(1000);

    }

}
