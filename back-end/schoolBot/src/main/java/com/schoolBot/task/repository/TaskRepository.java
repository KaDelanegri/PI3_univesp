package com.schoolBot.task.repository;

import com.schoolBot.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value ="SELECT * FROM db_schoolBot.tb_task where class_Name = :className and expiration_date > :today",nativeQuery = true)
    Optional<List<Task>> findByClassName(@Param("className") String className, @Param("today")LocalDate today);
}
