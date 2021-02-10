package product.furniture;

import product.Product;

/**
 * Implements the method in parent class
 * Can only be instanced using Painting Builder
 */
public class Furniture extends Product {
    private String type;
    private String material;

    protected Furniture(int id, String name, double minimumPrice, int year) {
        super(id, name, minimumPrice, year);
    }

    protected Furniture() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String toString(){
        return "Furniture Id:" + this.getId() + " name:" + this.getName();
    }
}
