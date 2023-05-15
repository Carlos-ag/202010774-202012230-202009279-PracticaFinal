package com.stockify.stockifyapp.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockify.stockifyapp.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT * FROM USERS u WHERE u.EMAIL = :EMAIL AND u.PASSWORD = :PASSWORD")
    User getUser(@Param("EMAIL") String EMAIL, @Param("PASSWORD") String PASSWORD);

}

