package product;

public class Painting extends Product{
    private String painterName;
    private ColorType colors;


    public Painting(String painterName, ColorType colors) {
        this.painterName = painterName;
        this.colors = colors;
    }
}
