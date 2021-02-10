package auction;

import client.Client;

/**
 * interface to be implemented by objects that are affected by the subject's change of state
 */
public interface Observer {
    /**
     * @param winner the winner of the auction
     * @param sellPrice he price the product sold for
     * @throws InterruptedException can be thrown if a thread is interrupted
     */
    void update(Client winner, double sellPrice) throws InterruptedException;
}
