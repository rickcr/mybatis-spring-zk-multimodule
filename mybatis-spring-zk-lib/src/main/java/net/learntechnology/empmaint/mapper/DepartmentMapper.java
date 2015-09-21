package net.learntechnology.empmaint.mapper;

import net.learntechnology.empmaint.domain.Department;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@CacheNamespace(flushInterval = 86400000, size = 20, readWrite = false )
public interface DepartmentMapper {

	@Select("SELECT id, name FROM DEPARTMENT")
	List<Department> fetchAll();
}
