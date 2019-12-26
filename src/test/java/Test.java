import com.yyh.po.Student;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.Before;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

/**
 * @author YanYuHang
 * @create 2019-12-26-15:05
 */
public class Test {
    SessionFactory sessionFactory;
    Session session;
    Transaction tx;

    @Before
    public void before() {
        //1.加载配置文件
        //2.创建会话工厂
        sessionFactory = new Configuration().configure().buildSessionFactory();
        //3.生产会话
        session = sessionFactory.openSession();
        //4.开启事务
        tx = session.beginTransaction();
    }

    /**
     * 使用hibernate   查询单个   get
     */
    @org.junit.Test
    public void testGet() {
        //5.可以使用会话  session  进行 数据库操作
        //5.1  根据id查询   get(要查询的实体类型,参数)
        Student student = session.get(Student.class, 11);
        System.out.println("student = " + student);
    }

    /**
     * load
     */
    @org.junit.Test
    public void testLoad() {
        Student student = session.load(Student.class, 11);
        System.out.println("student = " + student);
    }

    @org.junit.Test
    public void testQuery() {
        // hql   hibernate  的语句
        //1.编写 hql 语句
        String hql = "from Student";
        //2.创建query对象
        Query query = session.createQuery(hql);
        //2.1  query给占位符赋值    jdbc中  ? 占位符  赋值时  1代表第一个
        //                        hibernate中       赋值时  0代表第一个
        //query.setInteger(0, 11);
        //   hql    第二种占位符   通过自定义名称    =:自定义名称
        //query.setInteger("id",12);
        //3.执行查询
        List<Student> list = query.list();
        list.forEach(System.out::println);
    }

    @org.junit.Test
    public void testSqlQuery() {
        //1.创建sql语句
        String sql = "select * from student";
        //2.创建Query
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        //2.1设置查询实体类型
        sqlQuery.addEntity(Student.class);
        //3.执行查询
        List<Student> list = sqlQuery.list();
        list.forEach(System.out::println);
    }

    @org.junit.Test
    public void testDelete() {
        Student student =new Student();
        student.setId(12);
        session.delete(student);
        tx.commit();
    }
    @org.junit.Test
    public void testInsert() {
        Student student = new Student();
        student.setName("hibernate测试添加方法数据");
        student.setPwd("123456");
        session.save(student);
        tx.commit();
    }
    @org.junit.Test
    public void testUpdate() {
        Student student = new Student();
        student.setId(25);
        student.setName("hibernate测试修改方法数据");
        student.setPwd("123456");
        session.update(student);
        tx.commit();
    }
    @org.junit.Test
    public void testSaveOrUpdate() {
        Student student = new Student();
        student.setName("saveorupdate数据");
        student.setPwd("456789");
        session.saveOrUpdate(student);
        tx.commit();
    }

}
