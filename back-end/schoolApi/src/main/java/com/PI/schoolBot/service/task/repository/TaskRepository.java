package com.PI.schoolBot.service.task.repository;

import com.PI.schoolBot.service.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<Task, Long> {

}
