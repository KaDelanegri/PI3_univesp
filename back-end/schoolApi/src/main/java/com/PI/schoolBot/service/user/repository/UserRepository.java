package com.PI.schoolBot.service.user.repository;

import com.PI.schoolBot.service.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value ="select * from tb_user where username = :username",nativeQuery = true)
    public Optional<User> findByUsername(@Param("username") String username);

    @Query(value ="select username from tb_user where email = :paramEmail",nativeQuery = true)
    public String findByEmail(@Param("paramEmail") String email);

    @Query(value ="select * from tb_user where email = :paramEmail",nativeQuery = true)
    public Optional<User> findUserByEmail(@Param("paramEmail") String email);

}
