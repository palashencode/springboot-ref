package com.java.springboot.repository;


import com.java.springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u ORDER BY exactDob")
    List<User> listByDOB();

    @Query(value = "select now() from dual", nativeQuery = true)
    String getDBTime();

    @Query(value = "SELECT u FROM User u where name = :name")
    List<User> listUserByName(@Param("name") String column);

}
