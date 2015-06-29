package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Department;

import java.util.List;

public interface DepartmentService {
	List<Department> fetchAll();
}
