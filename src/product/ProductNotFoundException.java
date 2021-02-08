package product;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException() {super("Product does not exist");}
}
