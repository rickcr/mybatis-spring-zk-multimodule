package net.learntechnology.empmaint.mapper;

import net.learntechnology.empmaint.domain.Employee;

import java.util.List;

public interface EmployeeMapper {
	List<Employee> fetchAll();
	void update(Employee emp);
	void delete(Integer id);
	Employee fetch(Integer id);
	void insert(Employee emp);
}
