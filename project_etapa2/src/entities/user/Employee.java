package entities.user;

import java.util.Date;

public class Employee extends User {
    private int salary;
    private Date hireDate;

    public Employee(Integer id, String firstName, String lastName, String email, String phoneNumber, Date birthDate, int salary, Date hireDate) {
        super(id, firstName, lastName, email, phoneNumber, birthDate);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}
