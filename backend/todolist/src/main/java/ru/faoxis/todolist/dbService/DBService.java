package ru.faoxis.todolist.dbService;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;
import ru.faoxis.todolist.dbService.dao.TaskDAO;
import ru.faoxis.todolist.dbService.dataSets.TaskDataSet;

import java.util.List;
import java.util.Properties;

@Component
public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private Properties properties = new Properties();
    private SessionFactory sessionFactory;


    public DBService() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(TaskDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }


    public TaskDataSet getTask(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            TaskDAO dao = new TaskDAO(session);
            TaskDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<TaskDataSet> getAllTasks() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            TaskDAO dao = new TaskDAO(session);
            List<TaskDataSet> tasks = dao.getAll();
            session.close();
            return tasks;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long addTask(String name, boolean done) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        TaskDAO dao = new TaskDAO(session);
        long id = dao.insert(name, done);

        tx.commit();
        session.close();

        return id;
    }

    public void deleteTask(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            TaskDAO dao = new TaskDAO(session);
            dao.delete(id);

            tx.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void setTaskDone(long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        new TaskDAO(session).setDone(id);

        tx.commit();
        session.close();
    }

    public void resetTaskDone(long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        new TaskDAO(session).resetDone(id);

        tx.commit();
        session.close();
    }

    public void updateTask(long id, String name, boolean done) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        new TaskDAO(session).update(id, name, done);

        tx.commit();
        session.close();
    }
}
