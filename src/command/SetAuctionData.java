package command;

import auction.Auction;
import auction.AuctionHouse;

public class SetAuctionData implements Command{
    private final int auctionId;
    private final int participants;
    private final int maxSteps;
    public SetAuctionData(int auctionId, int participants, int maxSteps) {
        this.auctionId = auctionId;
        this.participants = participants;
        this.maxSteps = maxSteps;
    }

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        Auction auction = findAuction(auctionHouse, auctionId);
        auction.setParticipantsNo(participants);
        auction.setMaxStepsNo(maxSteps);
    }

    private Auction findAuction(AuctionHouse auctionHouse, int auctionId) {
        for (Auction a : auctionHouse.getAuctionList()) {
            if(a.getId() == auctionId) {
                return a;
            }
        }
        return null;
    }
}
