package net.learntechnology.empmaint.viewmodel;

import net.learntechnology.empmaint.domain.Department;
import net.learntechnology.empmaint.domain.Employee;
import net.learntechnology.empmaint.services.DepartmentService;
import net.learntechnology.empmaint.services.EmployeeService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeesVM {

	@WireVariable
	private EmployeeService employeeService;
	@WireVariable
	private DepartmentService departmentService;

	private Employee employee;
	private boolean hideGrid;

	@NotifyChange({"employee", "hideGrid"})
	@Command
	public void createEmployee() {
		employee = new Employee();
		hideGrid = true;
	}

	@NotifyChange({"hideGrid"})
	@Command
	public void editEmployee() {
		if (employee != null) hideGrid = true;
	}

	@NotifyChange({"employees", "hideGrid"})
	@Command
	public void add() {
		employeeService.insert(employee);
		hideGrid = false;
	}

	@NotifyChange({"employees", "hideGrid"})
	@Command
	public void update() {
		employeeService.update(employee);
		hideGrid = false;
	}

	@NotifyChange({"employees", "hideGrid"})
	@Command
	public void delete() {
		employeeService.delete(employee.getId());
		hideGrid = false;
	}

	@NotifyChange({"employees", "hideGrid"})
	@Command
	public void cancel() {
		hideGrid = false;
	}

	public List<Employee> getEmployees() {
		return employeeService.fetchAll();
	}

	public boolean isHideGrid() {
		return hideGrid;
	}

	public List<Department> getDepartments() {
		return departmentService.fetchAll();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
