package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Employee;

import java.util.List;

public interface EmployeeService {
	List<Employee> fetchAll();
    void update(Employee emp);
    void delete(Integer id);
    Employee fetch(Integer id);
    void insert(Employee emp);

}
