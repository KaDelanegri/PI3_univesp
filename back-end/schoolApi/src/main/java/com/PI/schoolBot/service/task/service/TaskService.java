package com.PI.schoolBot.service.task.service;

import com.PI.schoolBot.service.task.model.Task;
import com.PI.schoolBot.service.task.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskService {

    private static Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public ResponseEntity<Object> postTask(final List<Task> tasks) {
        LOGGER.info("Starting the task data ingestion");
        List<Task> taskList = new ArrayList<>();
        tasks.forEach(task -> taskList.add(taskRegistration(task)));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskList.size() + " tasks processed from " + tasks.size() + " tasks sent");
    }

    private Task taskRegistration(final Task task) {
        taskRepository.save(task);
        return task;
    }
}
