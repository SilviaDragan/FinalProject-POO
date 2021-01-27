package product;

public class Furniture extends Product{
    private String type;
    private String material;

    public Furniture(int id, String name, double sellPrice, double minimumPrice) {
        super(id, name, sellPrice, minimumPrice);
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
}
