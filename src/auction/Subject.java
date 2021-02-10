package auction;

/**
 * a subject has a few parameters that determines it's state
 * if the state of the subject changes. the observers should be aware
 */
public interface Subject {
    /**
     * @param o observer
     */
    void registerObserver(Observer o);

    /**
     * @param o observer
     */
    void removeObserver(Observer o);

    /**
     * @throws InterruptedException thrown if a thread is interrupted
     */
    void notifyObservers() throws InterruptedException;
}
