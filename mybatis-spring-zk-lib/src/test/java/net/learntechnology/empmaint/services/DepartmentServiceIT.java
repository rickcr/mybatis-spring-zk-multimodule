package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Department;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class DepartmentServiceIT extends BaseIT {
	private final static Logger logger = LoggerFactory.getLogger(DepartmentServiceIT.class);

	@Resource
	private DepartmentService departmentService;

	@Test
	public void should_fetch_all_departments() {
		List<Department> departments = departmentService.fetchAll();
		for(Department d: departments) {
			logger.debug("Dept: {}", d);
		}
		Assert.assertTrue(departments.size() > 1);
	}
}
