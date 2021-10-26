package Self_Testing.JdvcTesting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSimple {


    public static class Department {
        private Long dept_no;

        private String dept_name;

        private String db_source;

        public Department() {
        }

        public Long getDept_no() {
            return dept_no;
        }

        public void setDept_no(Long dept_no) {
            this.dept_no = dept_no;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getDb_source() {
            return db_source;
        }

        public void setDb_source(String db_source) {
            this.db_source = db_source;
        }
    }

    public static List<Department> select(String sql) {

        List<Department> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db01", "root", "123456");

            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {
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

        return result;
    }

    private static Department mapperRow(ResultSet rs, int i) throws Exception {
        Department instance = new Department();
        instance.setDept_no(rs.getLong("dept_no"));
        instance.setDept_name(rs.getString("dept_name"));
        instance.setDb_source(rs.getString("db_source"));
        return instance;
    }

}
