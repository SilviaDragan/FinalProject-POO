package product.furniture;

import product.ProductBuilder;

/**
 * This class builds a product that also has properties of a painting
 */
public class FurnitureBuilder extends ProductBuilder<Furniture, FurnitureBuilder> {

    public FurnitureBuilder withType(String type) {
        specificProduct.setType(type);
        return this;
    }

    public FurnitureBuilder withMaterial(String material) {
        specificProduct.setMaterial(material);
        return this;
    }

    @Override
    public Furniture getSpecificProduct() {
        return new Furniture();
    }

    @Override
    protected FurnitureBuilder getSpecificBuilder() {
        return this;
    }
}
