package net.learntechnology.empmaint.mapper;

import net.learntechnology.empmaint.domain.Employee;

import java.util.List;

public interface EmployeeMapper {
	List<Employee> fetchAll();
	void update(Employee emp);
	void delete(Integer id);
	public Employee fetch(Integer id);
	public void insert(Employee emp);
}
