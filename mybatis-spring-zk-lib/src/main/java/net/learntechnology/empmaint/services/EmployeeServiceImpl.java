package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Employee;
import net.learntechnology.empmaint.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
In real life your service methods might do a bit more than just delegate to the mapper.
The service just shields we are using mybatis and we'd put other stuff in here.
Remember, nothing wrong if you feel like just using MyBatis mappers directly in your
ViewModels. In this case you can skip using the Service class approach
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	public List<Employee> fetchAll() {
		return employeeMapper.fetchAll();
	}

	public void update(Employee emp) {
		employeeMapper.update(emp);
	}

	public void delete(Integer id) {
		employeeMapper.delete(id);
	}

	public Employee fetch(Integer id) {
		return employeeMapper.fetch(id);
	}

	public void insert(Employee emp) {
		employeeMapper.insert(emp);
	}
}
