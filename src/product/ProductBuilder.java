package product;

/**
 * This class is a builder for class Products
 */
public class ProductBuilder {
    private final Product product = new Product();

    public Product build() { return product; }

    public ProductBuilder withId(int id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withSellPrice(double sellPrice) {
        product.setSellPrice(sellPrice);
        return this;
    }

    public ProductBuilder withMinimumPrice(double minimumPrice) {
        product.setMinimumPrice(minimumPrice);
        return this;
    }
}
