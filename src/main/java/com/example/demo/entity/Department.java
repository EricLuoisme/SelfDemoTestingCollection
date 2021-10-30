package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Department")
// 使用lombok实现链式constructor
@Accessors(chain = true)
public class Department implements Serializable {

    @Id
    @Column(name = "dept_no")
    private Long dept_no;

    private String dept_name;

    private String db_source;
}