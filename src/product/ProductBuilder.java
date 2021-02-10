package product;

/**
 * This class is a builder for class Products
 */
public abstract class ProductBuilder<T extends Product, B extends ProductBuilder> {
    protected T specificProduct;
    protected B specificBuilder;

    protected abstract T getSpecificProduct();
    protected abstract B getSpecificBuilder();

    /**
     * class constructor
     */
    protected ProductBuilder () {
        specificProduct = getSpecificProduct();
        specificBuilder = getSpecificBuilder();
    }

    public B withId(int id) {
        specificProduct.setId(id);
        return specificBuilder;
    }

    public B withName(String name) {
        specificProduct.setName(name);
        return specificBuilder;
    }

    public B withMinimumPrice(double minimumPrice) {
        specificProduct.setMinimumPrice(minimumPrice);
        return specificBuilder;
    }

    public B withYear(int year) {
        specificProduct.setYear(year);
        return specificBuilder;
    }

    public T build() {
        return specificProduct;
    }
}
