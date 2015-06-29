package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Department;
import net.learntechnology.empmaint.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
In real life your service methods might do a bit more than just delegate to the mapper.
The service just shields we are using mybatis and we'd put other stuff in here.
Remember, nothing wrong if you feel like just using MyBatis mappers directly in your
ViewModels. In this case you can skip using the Service class approach
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService  {
	@Resource
	private DepartmentMapper departmentMapper;

	public List<Department> fetchAll() {
		return departmentMapper.fetchAll();
	}
}
