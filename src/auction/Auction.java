package auction;

public class Auction {
    private int id;
    private int participantsNo;
    private int productId;
    private int maxStepsNo;

    public Auction(int id, int productId) {
        this.id = id;
        this.productId = productId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipantsNo() {
        return participantsNo;
    }

    public void setParticipantsNo(int participantsNo) {
        this.participantsNo = participantsNo;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMaxStepsNo() {
        return maxStepsNo;
    }

    public void setMaxStepsNo(int maxStepsNo) {
        this.maxStepsNo = maxStepsNo;
    }

}
