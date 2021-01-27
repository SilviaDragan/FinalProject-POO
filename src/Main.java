import auction.AuctionHouse;
import client.Client;
import employee.Employee;


public class Main {
    public static void main(String[] args) {
        AuctionHouse auctionHouse = AuctionHouse.auctionHouseInstance();
        Reader reader = new Reader();
        String employeesFile = "employees.csv";
        String clientsFile = "clients.csv";
        auctionHouse.setEmployees(reader.readEmployeesCSV(employeesFile));
        auctionHouse.setClientList(reader.readClientsCSV(clientsFile));
        for(Employee e: auctionHouse.getEmployees()) {
            System.out.println(e);
        }
        for(Client c: auctionHouse.getClientList()) {
            System.out.println(c);
        }
    }
}
