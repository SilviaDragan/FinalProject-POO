package auction;

import client.Client;

public interface Observer {
    public void update(Client winner, double sellPrice);
}
