package employee;

/**
 * Classes Broker and Administrator extend this class
 */
public abstract class Employee {
    private int employeeId;

    protected Employee(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
