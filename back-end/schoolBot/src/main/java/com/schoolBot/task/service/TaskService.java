package com.schoolBot.task.service;

import com.schoolBot.task.model.Task;
import com.schoolBot.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public List<Task> getTasksByClassName(String className){
        Optional<List<Task>> taskList = taskRepository.findByClassName(className, LocalDate.now());
        return taskList.orElse(Collections.emptyList());
    }

    public Task getTaskDetails(final String id) {
        Optional<Task> task = taskRepository.findById(Long.valueOf(id));
        return task.orElse(new Task());
    }
}