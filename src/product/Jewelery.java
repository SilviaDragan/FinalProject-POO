package product;

public class Jewelery extends Product{
    private String metal;
    private boolean preciousStone;

    public Jewelery(int id, String name, double sellPrince, double minimumPrice) {
        super(id, name, sellPrince, minimumPrice);
    }
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
        if(decision.equals("Precious")){
            this.preciousStone = true;
        }
        else {
            this.preciousStone = false;
        }
    }
}
