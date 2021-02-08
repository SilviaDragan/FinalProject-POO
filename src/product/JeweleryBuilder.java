package product;

public class JeweleryBuilder extends ProductBuilder<Jewelery, JeweleryBuilder>{

    public JeweleryBuilder withMetal(String metal) {
        specificProduct.setMetal(metal);
        return this;
    }

    public JeweleryBuilder withPrecious(String decision) {
        specificProduct.setIfPrecious(decision);
        return this;
    }

    @Override
    protected Jewelery getSpecificProduct() {
        return new Jewelery();
    }

    @Override
    protected JeweleryBuilder getSpecificBuilder() {
        return this;
    }
}
