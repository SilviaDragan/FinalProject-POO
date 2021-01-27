package product;

public class Painting extends Product{
    private String painterName;
    private ColorType colors;

    public Painting(int id, String name, double sellPrice, double minimumPrice) {
        super(id, name, sellPrice, minimumPrice);
    }

    public String getPainterName() {
        return painterName;
    }

    public void setPainterName(String painterName) {
        this.painterName = painterName;
    }

    public void setColorType(String colorType) {
        if(colorType.equals("Oil")) {
            this.colors = ColorType.OIL;
        }
        else if(colorType.equals("Tempera")) {
            this.colors = ColorType.TEMPERA;
        }
        else {
            this.colors = ColorType.ACRYLIC;
        }
    }

}
