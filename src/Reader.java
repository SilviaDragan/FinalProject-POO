import client.Client;
import client.LegalPerson;
import client.NaturalPerson;
import employee.Administrator;
import employee.Broker;
import employee.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import product.Furniture;
import product.Jewelery;
import product.Painting;
import product.Product;

public class Reader {

    public List<Employee> readEmployeesCSV(String filename) {
        List<Employee> employees = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Employee employee;
                String employeeType = csvRecord.get(0);
                int id = Integer.parseInt(csvRecord.get(1));
                if(employeeType.equals("Administrator")) {
                    employee = new Administrator(id);
                }
                else {
                    employee = new Broker(id);
                }
                employees.add(employee);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    List<Client> readClientsCSV(String filename) {
        List<Client> clients = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Client client;
                String clientType = csvRecord.get(0);
                int id = Integer.parseInt(csvRecord.get(1));
                int tries = Integer.parseInt((csvRecord.get(4)));
                int wins = Integer.parseInt((csvRecord.get(5)));
                if(clientType.equals("Natural")) {
                    client = new NaturalPerson(id, csvRecord.get(2), csvRecord.get(3), tries, wins);
                    ((NaturalPerson) client).setBirthDate(csvRecord.get(6));
                }
                else {
                    client = new LegalPerson(id, csvRecord.get(2), csvRecord.get(3), tries, wins);
                    ((LegalPerson) client).setCompanyType(csvRecord.get(6));
                    ((LegalPerson) client).setSocialCapital(Double.parseDouble(csvRecord.get(7)));
                }
                clients.add(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    List<Product> readProductsCSV(String filename) {
        List<Product> products = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Product product;
                String productType = csvRecord.get(0);
                int id = Integer.parseInt(csvRecord.get(1));
                double sell = Double.parseDouble(csvRecord.get(3));
                double minimum = Double.parseDouble(csvRecord.get(4));
                if(productType.equals("Painting")) {
                    product = new Painting(id, csvRecord.get(2), sell, minimum);
                    ((Painting) product).setPainterName(csvRecord.get(5));
                    ((Painting) product).setColorType(csvRecord.get(6));
                }
                else if(productType.equals("Jewelery")){
                    product = new Jewelery(id, csvRecord.get(2), sell, minimum);
                    ((Jewelery) product).setMetal(csvRecord.get(5));
                    ((Jewelery) product).setIfPrecious(csvRecord.get(6));

                }
                else {
                    product = new Furniture(id, csvRecord.get(2), sell, minimum);
                    ((Furniture) product).setType(csvRecord.get(5));
                    ((Furniture) product).setMaterial(csvRecord.get(5));
                }
                products.add(product);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

}
