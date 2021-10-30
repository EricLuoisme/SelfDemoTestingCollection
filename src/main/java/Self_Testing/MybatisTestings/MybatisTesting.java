package Self_Testing.MybatisTestings;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.entity.Department;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTesting {


    public static void main(String[] args) {
        try {
            // 1. 获取配置文件
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            // 2. 加载解析配置文件, 并根据FactoryBuilder获取一个SqlSession的Factory实例
            // 需要注意, 这里应该是单例
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            // 3. 使用Factory实例, 创建一个SqlSession (每一次调用数据库会话, 都是需要新open一个Session的)
            SqlSession sqlSession = factory.openSession();
            // 4. 通过SqlSession中的Api与数据库交互
            DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
            List<Department> all = departmentDao.findAll();
            // 5. 执行完操作关闭连接
            sqlSession.close();

            all.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
