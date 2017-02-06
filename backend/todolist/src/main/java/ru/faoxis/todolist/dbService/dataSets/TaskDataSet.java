package ru.faoxis.todolist.dbService.dataSets;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="task")
@Component
public class TaskDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "done")
    private boolean done;

    public TaskDataSet() {}

    public TaskDataSet(String name) {
        this.name = name;
        done = false;
    }

    public TaskDataSet(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
