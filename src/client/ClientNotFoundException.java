package client;

/**
 * thrown when a client is not found
 */
public class ClientNotFoundException extends Exception{
    public ClientNotFoundException() {super("Client does not exist");}
}
