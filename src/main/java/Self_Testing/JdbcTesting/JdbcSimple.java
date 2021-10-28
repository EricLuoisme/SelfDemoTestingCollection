package Self_Testing.JdbcTesting;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSimple {

    public static void main(String[] args) {
        // 1. 原始硬编码
//        List<Department> select = JdbcSimple.select("select * from Department;");
//        select.forEach(System.out::println);

        // 2. 反射获取
        List<?> objects = JdbcSimple.selectImpByReflection(new Department());
        objects.forEach(System.out::println);
    }

    /**
     * 通过反射的版本
     */
    public static List<?> selectImpByReflection(Object condition) {
        // 初始化内容
        List<Object> result = new ArrayList<>();
        Class<?> entityClass = condition.getClass();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        // 执行jdbc通用流程
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 建立链接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db01", "root", "123456");

            // 3. 通过反射把列明和entity名称一一对应存入Map
            Map<String, String> getFieldNameByColumn = new HashMap<>();
            Map<String, String> getColumnByFieldName = new HashMap<>();
            Field[] fields = entityClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (field.isAnnotationPresent(Column.class)) {
                    // 如果有配置名称
                    Column annotation = field.getAnnotation(Column.class);
                    String colName = annotation.name();
                    // 同时存入两个双向Map
                    getFieldNameByColumn.put(colName, fieldName);
                    getColumnByFieldName.put(fieldName, colName);
                } else {
                    // 默认属性名就是列名
                    getFieldNameByColumn.put(fieldName, fieldName);
                    getColumnByFieldName.put(fieldName, fieldName);
                }
            }
            // 通过注解获取Table名称
            StringBuilder sql = new StringBuilder();
            Table table = entityClass.getAnnotation(Table.class);
            sql.append("select * from " + table.name() + " where 1=1");

            // 3. 创建语句集
            pstm = conn.prepareStatement(sql.toString());
            // 4. 执行语句集
            rs = pstm.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object instance = entityClass.getDeclaredConstructor().newInstance();
                // 数据库列与Java内的不同, 应该从1开始
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    String fieldName = getFieldNameByColumn.get(columnName);
                    // 通过反射进行属性赋值
                    Field field = entityClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(instance, rs.getObject(columnName));
                }
                result.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // 返回结果
        return result;
    }

    /**
     * 原始Jdbc链接数据库
     *
     * @param sql 输入的Sql
     * @return 查询得到List
     */
    public static List<Department> select(String sql) {
        // 初始化内容
        List<Department> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        // 执行jdbc通用流程
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2. 建立链接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db01", "root", "123456");
            // 3. 创建语句集
            pstm = conn.prepareStatement(sql);
            // 4. 执行语句集
            rs = pstm.executeQuery();
            while (rs.next()) {
                // 5. 一行一行获得结果集
                Department instance = mapperRow(rs, rs.getRow());
                result.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // 返回结果
        return result;
    }

    /**
     * @param rs resultSet
     * @param i  行索引
     */
    private static Department mapperRow(ResultSet rs, int i) throws Exception {
        Department instance = new Department();
        instance.setDept_no(rs.getLong("dept_no"));
        instance.setDept_name(rs.getString("dept_name"));
        instance.setDb_source(rs.getString("db_source"));
        return instance;
    }

    @Data
    @NoArgsConstructor
    @Entity
    @Table(name = "Department")
    public static class Department implements Serializable {

        @Id
        @Column(name = "dept_no")
        private Long dept_no;

        private String dept_name;

        private String db_source;
    }
}
