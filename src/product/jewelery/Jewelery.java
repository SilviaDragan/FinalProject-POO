package product.jewelery;

import product.Product;

/**
 * Implements the method in parent class
 * Can only be instanced using Painting Builder
 */
public class Jewelery extends Product {
    private String metal;
    private boolean preciousStone;

    protected Jewelery(int id, String name, double minimumPrice, int year) {
        super(id, name, minimumPrice, year);
    }

    protected Jewelery() { }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public boolean isPreciousStone() {
        return preciousStone;
    }

    public void setIfPrecious(String decision) {
        this.preciousStone = decision.equals("Precious");
    }

    public String toString(){
        return "Jewelery Id:" + this.getId() + " Name:" + this.getName() + " made of:" + this.getMetal()
                + " precious:" + this.isPreciousStone();
    }
}
