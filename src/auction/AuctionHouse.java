package auction;

import client.Client;
import employee.Employee;
import product.Product;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//singleton
public class AuctionHouse {
    private static AuctionHouse instance = null;
    private List<Product> productList;
    private List<Client> clientList;
    private List<Auction> auctionList;

    private List<Employee> employees;

    private int capacity; // capacity for productlist

    private AuctionHouse() {}

    public static AuctionHouse auctionHouseInstance() {
        // if a class instance exists, it is returned
        // only create a new instance if it does not exist already
        if (instance == null)
            instance = new AuctionHouse();
        return instance;
    }

    Lock lock = new ReentrantLock();
    Condition notVid = lock.newCondition();
    Condition notFull = lock.newCondition();

    // only administrators can do this
    public void addProduct(Product productNew) {
        lock.lock();
        try {
            while(productList.size() == capacity) {
                notFull.await();
            }
            System.out.println("add " + productNew.getName());
            productList.add(productNew);
            notVid.signal();
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // only brokers can do this
    public void removeProduct(Product soldProduct) {
        lock.lock();
        try {
            while (productList.isEmpty()) {
                notVid.await();
            }
            System.out.println("remove " + soldProduct.getName());
            productList.remove(soldProduct);
        } catch (InterruptedException e) {
            // Restore interrupted state
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }finally {
            lock.unlock();
        }


    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
