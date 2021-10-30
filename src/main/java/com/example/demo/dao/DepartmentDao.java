package com.example.demo.dao;

import com.example.demo.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface DepartmentDao {

    boolean add(Department department);

    Department findById(Long departmentId);

    List<Department> findAll();
}
