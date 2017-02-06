package ru.faoxis.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.faoxis.todolist.dbService.DBException;
import ru.faoxis.todolist.dbService.DBService;
import ru.faoxis.todolist.dbService.dataSets.TaskDataSet;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TaskController {

    @Autowired
    private DBService dbService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public @ResponseBody List<TaskDataSet> getAllTasks() throws DBException {
        return dbService.getAllTasks();
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public void addTask(@RequestBody TaskDataSet task) {
        dbService.addTask(task.getName(), task.isDone());
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable(value = "id") final String id) throws DBException {
        dbService.deleteTask(Long.valueOf(id));
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public void editTask(@PathVariable(value = "id") final String id, @RequestBody TaskDataSet task) {
        dbService.updateTask(Long.valueOf(id), task.getName(), task.isDone());
    }
}
