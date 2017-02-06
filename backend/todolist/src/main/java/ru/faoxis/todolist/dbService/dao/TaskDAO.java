package ru.faoxis.todolist.dbService.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.faoxis.todolist.dbService.dataSets.TaskDataSet;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TaskDAO {
    private Session session;

    public TaskDAO(Session session) {
        this.session = session;
    }

    public TaskDataSet get(long id) throws HibernateException {
        return session.get(TaskDataSet.class, id);
    }

    public List<TaskDataSet> getAll() throws HibernateException {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TaskDataSet> cq = builder.createQuery(TaskDataSet.class);
        Root<TaskDataSet> task = cq.from(TaskDataSet.class);
        cq.select(task);
        TypedQuery<TaskDataSet> q = session.createQuery(cq);
        List<TaskDataSet> allTask = q.getResultList();
        return allTask;
    }

    public long insert(String name, boolean done) {
        return (Long) session.save(new TaskDataSet(name, done));
    }

    public void delete(long id) throws HibernateException {
        session.delete(get(id));
    }

    public void update(long id, String name, boolean done) {
        TaskDataSet task = get(id);
        task.setName(name);
        task.setDone(done);
        session.update(task);
    }

    public void setDone(long id) {
        TaskDataSet task = get(id);
        if (!task.isDone()) {
            task.setDone(true);
            session.update(task);
        }
    }

    public void resetDone(long id) {
        TaskDataSet task = get(id);
        if (task.isDone()) {
            task.setDone(false);
            session.update(task);
        }
    }
}